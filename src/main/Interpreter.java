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
    /* Represents the current scope we are executing in
     * While we are working in a scope, this variable should always be set to that scope
     * As such, it only changes when entering or exiting a brace block
     */
    private SymbolTable curScope = SymbolTable.root;

    private Interpreter() {
        AST = Parser.parseAndGenerateAST();
    }

    public static void main (String[] args) {
        Interpreter interpreter = new Interpreter();
        System.out.println(interpreter.AST);

        interpreter.beginExecution();
    }

    private void beginExecution() {
        doNode(AST.getRoot());
        // print out curScope's symtable and all of its children symtables
        // so we can see what's happening in them
        System.out.println(curScope);
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
            //adding other cases such as mult, div, power, ople, opge
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
                return doParens(node);
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
        curScope = curScope.addNewScope(node); // create new scope for this block and enter it
        DynamicVal result;
        doNode(node.kids[0]); // do vargroup if it exists
        result = doNode(node.kids[1]); // do block next and keep result in case of 'return' statement
        curScope = curScope.getParent(); // return to parent scope
        return result; // return result, may be null if no return statement
    }

    private DynamicVal doParens(PNode node) {
        return doNode(node.kids[0]); //  return result in case of 'return' statement, may be null if no return statement
    }

    /* Handles assignment and initialization of variables
     */
    private DynamicVal doEquals(PNode node) {

        DynamicVal val = doNode(node.kids[1]);

        Symbol lkid = node.kids[0].sym;
        if(lkid.getId() == Token.KINT || lkid.getId() == Token.KFLOAT || lkid.getId() == Token.KSTRING) {
            curScope.declareVar(val, lkid.getToken().getStr().toUpperCase(), node.kids[0].kids[0].sym.getToken());
        }
        else if(lkid.getId() == Token.ID) {
            curScope.updateVar(val, lkid.getToken().getStr());
        }
        else {
           throw new RuntimeException("Error: Unexpected token on left of equals in AST");
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
            //added mul case
            case Token.ASTER:
                return val1.mul(val2);
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
            return null; // missing implementation of dereference operator
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

}
