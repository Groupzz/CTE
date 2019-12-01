package main;
/* CECS 444 Compiler Construction
 * Project 3: Interpreter
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: Class that handles interpretation of the abstract syntax tree.
 * Calls the necessary methods to obtain an abstract syntax tree from raw A7 code.
 * Recursively iterates over the nodes of the AST doing different things based on what
 * token the node represents.
 * Keeps track of the current scope as it is iterating over the AST.
 */
public class Interpreter {

    private SymbolTree AST;
    private SymbolTable SCT = SymbolTable.root; // Root node of scope tree

    private Interpreter() {
        AST = Parser.parseAndGenerateAST();
    }

    public static void main (String[] args) {
        Interpreter interpreter = new Interpreter();
        System.out.println(interpreter.AST);

        interpreter.buildSCT();
        interpreter.beginExecution();
    }

    // Helper method for recursive buildSCT
    private void buildSCT() {
        buildSCT(AST.getRoot(), SCT);
        System.out.println("---GENERATED SCOPE TREE---");
        System.out.println(SCT);
    }

    private void beginExecution() {
        doNode(AST.getRoot());
        // print out curScope's symtable and all of its children symtables
        // so we can see what's happening in them
        System.out.println("---EXECUTION FINISHED---");
        System.out.println(SCT);
    }

    // Recursive buildSCT function
    private void buildSCT(PNode node, SymbolTable curScope) {
        if(null == node)
            return;
        switch(node.sym.getId()) {
            case Token.KFCN:
                return; // ignore functions for now
            case Token.BRACE1:
                curScope = curScope.addNewScope(node); // create new scope for this block and enter it
                buildSCT(node.kids[0], curScope); // do vargroup if it exists
                buildSCT(node.kids[1], curScope); // enter block to look for more blocks
//                curScope = curScope.getParent(); // return to parent scope
                break;
            case Token.EQUAL:
                buildSCT(node.kids[1], curScope); // check rhs of equals for any identifier usage
                Symbol lkid = node.kids[0].sym; // check lhs of equals for type in case of initializer
                if(lkid.getId() == Token.KINT || lkid.getId() == Token.KFLOAT || lkid.getId() == Token.KSTRING) {
                    buildSCT(node.kids[0], curScope); // if its an initializer we let the type node handle it
                }
                else if(lkid.getId() == Token.ID) { // if not an initializer, we simply link it to its SymTabRow
                    curScope.linkID(node.kids[0]);
                }
                else if(lkid.getId() == Token.ASTER) {
                    curScope.linkID(node.kids[0].kids[0]); // skip the aster for pointers
                }
                else {
                    throw new RuntimeException("Error: Unexpected token on left of equals in AST");
                }
                break;
            case Token.ID:
                if(null == node.kids[0]) { // if id is a variable
                    curScope.linkID(node);
                }
                break;
            case Token.KINT:
            case Token.KFLOAT:
            case Token.KSTRING:
                PNode idNode;
                if(node.kids[0].sym.getId() == Token.ID) {
                    idNode = node.kids[0];
                    if(null == node.kids[0].kids[0]) { // not an array
                        curScope.declareVar(node.sym.getToken().getStr().toUpperCase(), idNode.sym.getToken(), node, false);
                        curScope.linkID(idNode);
                    }
                    else { // an array
                        DynamicVal arraySize = doNode(node.kids[0].kids[0]);
                        curScope.declareArr(node.sym.getToken().getStr().toUpperCase(), idNode.sym.getToken(), node, arraySize);
                        curScope.linkID(idNode);
                    }
                }
                else if (node.kids[0].sym.getId() == Token.ASTER) {
                    idNode = node.kids[0].kids[0];
                    // should be an asterisk
                    curScope.declareVar(node.sym.getToken().getStr().toUpperCase(), idNode.sym.getToken(), node, true);
                    curScope.linkID(idNode);
                }
                else {
                    throw new RuntimeException("Error: Unexpected token on left of initializer in AST");
                }
                break;
            default:
                for(PNode kid : node.kids) {
                    if(null != kid)
                        buildSCT(kid, curScope);
                }
        }
    }

    /*
     * Main recursive function that handles the treewalk of the AST
     * Has a return type of DynamicVal so when arithmetic operators are performed it can return the resulting value
     * This is also used so handle return values of functions or the return value of a boolean operator
     * The individual cases are separated into other functions to improve readability and modularity
     */
    private DynamicVal doNode(PNode node) {
        if(null == node)
        {
            return null;
        }
        switch(node.sym.getId()) {
            case Token.KINT:
            case Token.KFLOAT:
            case Token.KSTRING:
            case Token.KFCN: // ignore functions for now
                return null;
            case Token.KIF:
            case Token.KELSEIF:
                return doIf(node);
            case Token.KRETURN:
                return doReturn(node);
            case Token.SEMI:
                return doSemi(node);
            case Token.EQUAL:
                return doEquals(node);
            case Token.INT:
            case Token.FLOAT:
            case Token.STRING:
                return doLiteral(node);
            case Token.PLUS:
            case Token.MINUS:
            case Token.SLASH:
            case Token.CARET:
            case Token.OPLE:
            case Token.OPGE:
            case Token.OPEQ:
            case Token.ANGLE1:
            case Token.ANGLE2:
            case Token.OPNE:
                return doSimpleOP(node);
            case Token.ASTER:
                return doAster(node);
            case Token.BRACE1:
                return doBrace(node);
            case Token.PARENS1:
            case Token.BRACKET1:
                return doParens(node);
            case Token.ID:
                return doIdentifier(node);
            case Token.AMPERSAND:
                return doAmpersand(node);
                // by default we simply recurse to all children and return null
            default:
                for(PNode kid : node.kids) {
                    if(null != kid)
                        doNode(kid);
                }
                return null;
        }
    }

    private DynamicVal doIf(PNode node) {
        DynamicVal condition = doNode(node.kids[0]); // get result of condition
        if(condition.isTrue()) { // check if condition is true
            return doNode(node.kids[1]); // do block
        }
        else {
            return doNode(node.kids[2]); // do else part
        }
    }

    private DynamicVal doReturn(PNode node) {
        if(null != node.kids[0]) {
            // return the result of the expression after the 'return' statement
            // this will result in no more statements after the 'return' statement is executed
            return doNode(node.kids[0]);
        }
        else {
            return new DynamicVal("INT", "0"); // temporary solution for 'return;' with no value
        }
    }

    private DynamicVal doSemi(PNode node) {
        DynamicVal result = doNode(node.kids[0]); // check if the first statement results in a return value
        if(null == result) { // if it has no return value
            result = doNode(node.kids[1]); // do the next statement and return that
        }
        return result; // otherwise return the result of the first statement: it has led to an A7 'return' statement
    }

    private DynamicVal doBrace(PNode node) {
        DynamicVal result;
        doNode(node.kids[0]); // do vargroup if it exists
        result = doNode(node.kids[1]); // do block next and keep result in case of 'return' statement
        return result; // return result, may be null if no return statement
    }

    private DynamicVal doParens(PNode node) {
        return doNode(node.kids[0]); //  return result in case of 'return' statement, may be null if no return statement
    }

    /* Handles assignment of variables only
     */
    private DynamicVal doEquals(PNode node) {
        DynamicVal val = doNode(node.kids[1]); // calculate expr on RHS
        Symbol lkid = node.kids[0].sym;
        PNode idNode;
        if(lkid.getId() == Token.KINT || lkid.getId() == Token.KFLOAT || lkid.getId() == Token.KSTRING) {
            idNode = node.kids[0].kids[0]; // if its an initializer, the identifier is one node down
            if (idNode.sym.getId() == Token.ASTER) {
                idNode = idNode.kids[0]; // if its a ptr initializer go straight to the ID
            }
        }
//        else if(lkid.getId() == Token.ID) {
        else {
            idNode = node.kids[0]; // if its assignment the identifier is right on the left
        }
//        else {
//           throw new RuntimeException("Error: Unexpected token on left of equals in AST");
//        }
        if(idNode.sym.getId() == Token.ASTER) { // if there is a deref specifically in assignment
            idNode = idNode.kids[0]; // move to identifier
            if(idNode.symTabLink.isPtr()) { // check for correct type
                DynamicVal memAddr = idNode.symTabLink.getValue(); // get the memory address
                SymTabRow.setMem(memAddr, val); // set value at memory address
            }
            else {
                throw new RuntimeException("Error: Attempted to dereference an identifier that isn't a pointer");
            }
        }
        else {
            idNode.symTabLink.setValue(val); // assign calculated expr to existing symtabrow
        }
        return null;
    }

    /* This function is meant to cover all arithmetic and boolean operators that do nothing other than their
     * mathematical operation. Some operators such as ASTER need a designated function as they have other uses
     */
    private DynamicVal doSimpleOP(PNode node) {
        DynamicVal val1 = doNode(node.kids[0]);
        DynamicVal val2 = doNode(node.kids[1]);
        switch (node.sym.getId()) {
            case Token.PLUS:
                return val1.plus(val2);
            case Token.MINUS:
                return val1.minus(val2);
            //added div case
            case Token.SLASH:
                return val1.div(val2);
            //added exponent case
            case Token.CARET:
                return val1.pow(val2);
            case Token.OPEQ:
                return val1.equals(val2);
            case Token.ANGLE1:
                return val1.lessThan(val2);
            //added <= case
            case Token.OPLE:
                return val1.lessThanOrEqual(val2);
            case Token.ANGLE2:
                return val1.greaterThan(val2);
            //added >= case
            case Token.OPGE:
                return val1.greaterThanOrEqual(val2);
            case Token.OPNE:
                return val1.notEqual(val2);
        }
        throw new RuntimeException("ERROR: Tried to do simple OP on an OP that isn't simple");
    }

    private DynamicVal doAster(PNode node) {
        if(null != node.kids[1]) { // we are a multiplication operator
            DynamicVal val1 = doNode(node.kids[0]);
            DynamicVal val2 = doNode(node.kids[1]);
            return val1.mul(val2);
        }
        else {
            DynamicVal memAddr = node.kids[0].symTabLink.getValue();
            return SymTabRow.getFromMem(memAddr);
        }
    }

    // Simply returns the dynamic value version of the literal
    private DynamicVal doLiteral(PNode node) {
        switch(node.sym.getId()) {
            case Token.INT:
                return new DynamicVal("INT", node.sym.getToken().getStr());
            case Token.FLOAT:
                return new DynamicVal("FLOAT", node.sym.getToken().getStr());
            case Token.STRING:
                return new DynamicVal("STRING", node.sym.getToken().getStr());
        }
        throw new RuntimeException("ERROR: Tried to treat an AST node that wasn't a literal as a literal");
    }

    private DynamicVal doAmpersand(PNode node) {
        return node.kids[0].symTabLink.getMemAddr();
    }

    private DynamicVal doIdentifier(PNode node) {
        if(null == node.kids[0]) { // if we are a variable identifier
            DynamicVal val = node.symTabLink.getValue();
            if(null == val) {
                Token idTok = node.sym.getToken();
                throw new RuntimeException("ERROR: Variable '" + idTok.getStr() + "' referenced before assignment (Lin: "
                        + idTok.getLin() + " Col: " + idTok.getLinCol() + ")");
            }
            return val;
        }
        // Put function call logic here
        return null;
    }
}
