package main;

public class Interpreter {

    private SymbolTree AST;
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
        System.out.println(curScope);
    }

    private DynamicVal doNode(PNode node) {
        if(null == node)
        {
            return null;
        }
        switch(node.sym.getId()) {

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
            case Token.OPEQ:
            case Token.ANGLE1:
            case Token.ANGLE2:
            case Token.OPNE:
                return doSimpleOP(node);
            case Token.ASTER:
                return doAster(node);
            case Token.BRACE1:
                return doBrace(node);
            default:
                for(PNode kid : node.kids) {
                    if(null != kid)
                        doNode(kid);
                }
                return null;
        }
    }

    private DynamicVal doReturn(PNode node) {
        if(null != node.kids[0]) {
            return doNode(node.kids[0]);
        }
        else {
            return new DynamicVal("INT", "0"); // temporary solution for returning nothing
        }
    }

    private DynamicVal doSemi(PNode node) {
        DynamicVal result = doNode(node.kids[0]);
        if(null == result) {
            result = doNode(node.kids[1]);
        }
        return result;
    }

    private DynamicVal doBrace(PNode node) {
        curScope = curScope.addNewScope(node);
        DynamicVal result;
        doNode(node.kids[0]); // do vargroup if it exists;
        result = doNode(node.kids[1]); // do block next and return result in case of return statement
//        System.out.println("OUTPUTTING SCOPE BEFORE LEAVING: ");
//        System.out.println(curScope);
        curScope = curScope.getParent();
        return result;
    }

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
            System.out.println("Error: Unexpected token on left of equals in AST");
        }
        return null;
    }

    private DynamicVal doSimpleOP(PNode node) {
        DynamicVal val1 = doNode(node.kids[0]);
        DynamicVal val2 = doNode(node.kids[1]);
        switch (node.sym.getId()) {
            case Token.PLUS:
                return val1.plus(val2);
            case Token.MINUS:
                return val1.minus(val2);
            case Token.OPEQ:
                return val1.equals(val2);
            case Token.ANGLE1:
                return val1.lessThan(val2);
            case Token.ANGLE2:
                return val1.greaterThan(val2);
            case Token.OPNE:
                return val1.notEqual(val2);
        }
        System.out.println("ERROR: Tried to do simple OP on an OP that isn't simple");
        return null;
    }

    private DynamicVal doAster(PNode node) {
        if(null != node.kids[1]) { // multiplication operator
            DynamicVal val1 = doNode(node.kids[0]);
            DynamicVal val2 = doNode(node.kids[1]);
            return val1.mul(val2);
        }
        else {
            return null; // missing implementation of dereference operator
        }
    }

    private DynamicVal doLiteral(PNode node) {
        switch(node.sym.getId()) {
            case Token.INT:
                return new DynamicVal("INT", node.sym.getToken().getStr());
            case Token.FLOAT:
                return new DynamicVal("FLOAT", node.sym.getToken().getStr());
            case Token.STRING:
                return new DynamicVal("STRING", node.sym.getToken().getStr());
        }
        return null;
    }

}
