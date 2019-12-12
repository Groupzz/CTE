package main;

import java.util.ArrayList;

/* CECS 444 Compiler Construction
 * Project 3: Interpreter
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: Class that handles interpretation of the abstract syntax tree.
 * Calls the necessary methods to obtain an abstract syntax tree from raw A7 code.
 * First treewalks to build a scope tree and link identifier usages to their respective scope.
 * Then recursively iterates over the nodes of the AST doing different things based on what
 * token the node represents.
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
        interpreter.typeCheck();
        interpreter.optimizer(interpreter.AST.getRoot());
        System.out.println(interpreter.AST);
        interpreter.beginExecution();
    }

    private String getLinCol(PNode node) {
        return " (Lin: " + node.sym.getToken().getLin() + " Col: " + node.sym.getToken().getLinCol() + ") ";
    }

    // Helper method for recursive buildSCT
    private void buildSCT() {
        buildSCT(AST.getRoot(), SCT);
        System.out.println("---GENERATED SCOPE TREE---");
        System.out.println(SCT);
    }

    private void typeCheck() {
        typeCheck(AST.getRoot());
    }

    private void beginExecution() {
        DynamicVal exitCode = doNode(AST.getRoot());
        // print out curScope's symtable and all of its children symtables
        // so we can see what's happening in them
        System.out.println("---EXECUTION FINISHED---");
        System.out.println("Program finished with exit code: " + exitCode);
        System.out.println(SCT);
    }

    // ----------------- SCT BUILDER -----------------

    // Recursive buildSCT function
    private void buildSCT(PNode node, SymbolTable curScope) {
        if(null == node)
            return;
        switch(node.sym.getId()) {
            case Token.KFCN:
                declareFunction(node, curScope);
                break;
            case Token.BRACE1:
                curScope = curScope.addNewScope(node); // create new scope for this block and enter it
                buildSCT(node.kids[0], curScope); // do vargroup if it exists
                buildSCT(node.kids[1], curScope); // enter block to look for more blocks
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
                if(null == node.kids[0] || node.kids[0].sym.getId() == Token.BRACKET1) { // if id is not a function
                    curScope.linkID(node);
                }
                break;
            case Token.KINT:
            case Token.KFLOAT:
            case Token.KSTRING:
                PNode idNode;
                if(node.kids[0].sym.getId() == Token.ID) {
                    idNode = node.kids[0];
                    if(null == idNode.kids[0]) { // not an array
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

    private void declareFunction(PNode node, SymbolTable curScope) {
        buildSCT(node.kids[3], curScope); // handle function definition and all of its declarations
        buildSCT(node.kids[1], curScope.getKidByBlock(node.kids[3])); // link args to var decls inside block
        String funcID = node.kids[0].sym.getToken().getStr(); // get name of function
        curScope.declareFunc(funcID, node);
        buildSCT(node.kids[4], curScope); // declare next function
    }

    // ----------------- TYPECHECKER -----------------

    private String typeCheck(PNode node) {
        if(null == node) {
            return null;
        }
        switch(node.sym.getId()) {
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
                return typeCheckOP(node);
            case Token.EQUAL:
                String typeLHS = typeCheck(node.kids[0]);
                String typeRHS = typeCheck(node.kids[1]);
                if(typeLHS.equals(typeRHS))
                    return typeLHS;
                else
                    throw new RuntimeException("Wrong assignment at " + getLinCol(node) + "expected " + typeLHS + " got " + typeRHS + " instead.");
            case Token.INT:
            case Token.KINT: // if its a declaration, ptr or not we look for an integer
            case Token.AMPERSAND: // a reference to a variable's address is an integer
                return "INT";
            case Token.STRING:
                return "STRING";
            case Token.FLOAT:
                return "FLOAT";
            case Token.KFCN:
                typeCheck(node.kids[3]);
                typeCheck(node.kids[4]);
                typeCheck(node.kids[2]);
                return typeCheck(node.kids[1]);
            case Token.ID:
                if(null == node.kids[0]) {
                    if(node.symTabLink.isPtr()) {
                        return "INT";
                    }
                    return node.symTabLink.getType();
                }
                else if(node.kids[0].sym.getId() == Token.PARENS1) {
                    // Function call
                    return SCT.getFuncNode(node.sym.getToken().getStr()).kids[2].sym.getToken().getStr().toUpperCase();
                }
                else if(node.kids[0].sym.getId() == Token.BRACKET1) {
                    if(!typeCheck(node.kids[0]).equals("INT")) {
                        throw new RuntimeException("Attempt to access index that is not of type INT " + getLinCol(node));
                    }
                    return node.symTabLink.getType();
                }
                break;
            case Token.KFLOAT:
                if(node.kids[0].sym.getId() == Token.ASTER)
                    return "INT"; // if its a ptr declaration, we assign an integer
                else return "FLOAT";
            case Token.KSTRING:
                if(node.kids[0].sym.getId() == Token.ASTER)
                    return "INT"; // if its a ptr declaration, we assign an integer
                else return "STRING";
            case Token.BRACKET1:
            case Token.PARENS1:
                return typeCheck(node.kids[0]);
            case Token.ASTER:
                if(node.kids[1] != null) {
                    return typeCheckOP(node);
                }
                else {
                    return node.kids[0].symTabLink.getType();
                }
            default:
                for(PNode kid : node.kids) {
                    typeCheck(kid);
                }
                return "";
        }
        return null;
    }

    private DynamicVal dummyVal(String type) {
        if(null == type) {
            throw new RuntimeException("Type error");
        }
        switch (type) {
            case "INT":
                return new DynamicVal(1);
            case "FLOAT":
                return new DynamicVal(1.0f);
            case "STRING":
                return new DynamicVal("");
            default:
                throw new RuntimeException("Type error");
        }
    }

    private String typeCheckOP(PNode node) {
        String type1 = typeCheck(node.kids[0]);
        String type2 = typeCheck(node.kids[1]);
        DynamicVal val1 = dummyVal(type1);
        DynamicVal val2 = dummyVal(type2);
        try {
            switch (node.sym.getId()) {
                case Token.PLUS:
                    return val1.plus(val2).type;
                case Token.MINUS:
                    return val1.minus(val2).type;
                case Token.SLASH:
                    return val1.div(val2).type;
                case Token.CARET:
                    return val1.pow(val2).type;
                case Token.OPEQ:
                    return val1.equals(val2).type;
                case Token.ANGLE1:
                    return val1.lessThan(val2).type;
                case Token.OPLE:
                    return val1.lessThanOrEqual(val2).type;
                case Token.ANGLE2:
                    return val1.greaterThan(val2).type;
                case Token.OPGE:
                    return val1.greaterThanOrEqual(val2).type;
                case Token.OPNE:
                    return val1.notEqual(val2).type;
                case Token.ASTER:
                    return val1.mul(val2).type;
                default:
                    throw new RuntimeException("SIMPLE OPPPP");
            }
        }
        catch (Exception e) {
            System.out.print(getLinCol(node) + " : ");
            System.out.println(e.getMessage());
            throw e;
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
            case Token.KFCN: // ignore functions
                return null;
            case Token.KIF:
            case Token.KELSEIF:
                return doIf(node);
            case Token.KWHILE:
                return doWhile(node);
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
            case Token.KPRINT:
                return doPrint(node);
            case Token.KMAIN:
                return doNode(node.kids[0]);
            case Token.KPROG:
                return doProg(node);
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
    
    private DynamicVal doWhile(PNode node){
        DynamicVal result = null;
        while(doNode(node.kids[0]).isTrue()){
            result = doNode(node.kids[1]);
            if(result != null){
                return result;
            }
        }
        return result;
    }

    private DynamicVal doProg(PNode node) {
        doNode(node.kids[0]); // vargroup
        return doNode(node.kids[2]); // main
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
        if(null == result || node.kids[0].sym.getId() == Token.ID) { // if it has no return value or was a void function
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

    /* Handles assignment only
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
            if(null == idNode.kids[0]) { // if LHS is an ID by itself
                idNode.symTabLink.setValue(val); // assign calculated expr to existing symtabrow
            }
            else { // if LHS includes brackets for array assignment
                DynamicVal memAddr = idNode.symTabLink.getValue();
                DynamicVal offset = doNode(idNode.kids[0]);
                SymTabRow.setMem(memAddr.plus(offset), val);
            }
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
            case Token.SLASH:
                return val1.div(val2);
            case Token.CARET:
                return val1.pow(val2);
            case Token.OPEQ:
                return val1.equals(val2);
            case Token.ANGLE1:
                return val1.lessThan(val2);
            case Token.OPLE:
                return val1.lessThanOrEqual(val2);
            case Token.ANGLE2:
                return val1.greaterThan(val2);
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
        else if(node.kids[0].sym.getId() == Token.PARENS1) {
            // Function call logic
            String funcID = node.sym.getToken().getStr();
            PNode funcNode = SCT.getFuncNode(funcID);

            ArrayList<DynamicVal> args = collectArgs(node.kids[0].kids[0].kids[0]); // move down to first comma or literal
            fillParams(funcNode.kids[1].kids[0].kids[0], args);
            return doNode(funcNode.kids[3]);
        }
        else if(node.kids[0].sym.getId() == Token.BRACKET1) {
            // Array logic
            DynamicVal memAddr = node.symTabLink.getValue();
            DynamicVal offset = doNode(node.kids[0]);
            return SymTabRow.getFromMem(memAddr.plus(offset));
        }
        else {
            throw new RuntimeException("Identifier has weird kids");
        }
    }

    private DynamicVal doPrint(PNode node){
        String output = "";
        for(Object word : collectArgs(node.kids[0].kids[0].kids[0]))
            output += word + " ";
        System.out.println(output.trim());
        return null;
    }

    // Returns an ArrayList of all the resulting values of a function's arguments in the order they are listed
    private ArrayList<DynamicVal> collectArgs(PNode node) {
        ArrayList<DynamicVal> result = new ArrayList<>();
        if (node.sym.getId() == Token.COMMA) {
            result.addAll(collectArgs(node.kids[0]));
            result.addAll(collectArgs(node.kids[1]));
        }
        else {
            result.add(doNode(node));
        }
        return result;
    }

    // Takes an ArrayList and puts its values into a function's parameters in the order they are listed
    private void fillParams(PNode node, ArrayList<DynamicVal> params) {
        if(node.sym.getId() == Token.COMMA) {
            fillParams(node.kids[0], params);
            fillParams(node.kids[1], params);
        }
        else if(node.sym.getId() == Token.ASTER) {
            fillParams(node.kids[0], params);
        }
        else {
            if(node.symTabLink == null) {
                System.out.println(node.sym.getToken());
            }
            node.symTabLink.setValue(params.remove(0));
        }
    }

    // ----------------- OPTIMIZER -----------------

    // Propagates up the value by replacing the node operator with the value of the
    // arithmetic equations
    private void doPropagation(PNode node) {
//        if (node == null || node.sym.getId() == Token.ID) return;
        if(node.kids[0] == null || node.kids[1] == null) return;
        doPropagation(node.kids[0]);
        doPropagation(node.kids[1]);
        // If left child is a constant
        if(node.kids[0].sym.getId() == Token.PARENS1) {
            node.kids[0] = node.kids[0].kids[0];
        }
        if (node.kids[0].sym.getId() == Token.INT || node.kids[0].sym.getId() == Token.FLOAT || node.kids[0].sym.getId() == Token.STRING) {
            // If right child is a constant
            if (node.kids[1].sym.getId() == Token.INT || node.kids[1].sym.getId() == Token.FLOAT || node.kids[1].sym.getId() == Token.STRING) {
                // Get the value
                DynamicVal newVal;
                if(node.sym.getId() == Token.ASTER) {
                    if(node.kids[1] != null)
                        newVal = doAster(node); // multiplication operator
                    else
                        return; // dereference operator, not a constant so we return
                }
                else
                    newVal = doSimpleOP(node); // simple op can be done simply

                // Make a symbol of the dynamic val
                node.sym = makeSymbol(newVal, node.sym.getToken().getLin(), node.sym.getToken().getLinCol());

                node.kids[0] = null;
                node.kids[1] = null;
            } /*else if(node.kids[1].sym.getId() == Token.ID) {z
                Get value at the ID
            }*/
        } /*else if (node.kids[0].sym.getId() == Token.ID) {

        } */
    }
    // Makes a symbol based off of the Dynamic Value by creating the Token first
    private Symbol makeSymbol(DynamicVal dv, int lin, int lincol) {
        switch (dv.type) {
            case "INT":
                Token intToken = new Token(Token.INT, lin, lincol, String.valueOf(dv.intVal));
                return new Symbol(intToken);
            case "FLOAT":
                Token floatToken = new Token(Token.FLOAT, lin, lincol, String.valueOf(dv.floatVal));
                return  new Symbol(floatToken);
            case "STRING":
                Token strToken = new Token(Token.STRING, lin, lincol, dv.strVal);
                return  new Symbol(strToken);
            default:
                throw new RuntimeException("Not a valid type");
        }
    }
    // Recursive propagation tree function
    private void optimizer(PNode node) {
        if(null == node)
            return;
        switch(node.sym.getId()) {
            case Token.PLUS:
            case Token.MINUS:
            case Token.SLASH:
            case Token.CARET:
            case Token.OPEQ:
            case Token.ANGLE1:
            case Token.ANGLE2:
            case Token.OPLE:
            case Token.OPGE:
            case Token.OPNE:
                doPropagation(node);
            default:
                for(PNode kid : node.kids) {
                    if(null != kid)
                        optimizer(kid);
                }
        }
    }
}


