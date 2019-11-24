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

            case Token.EQUAL:
                return doEquals(node);
            case Token.INT:
            case Token.FLOAT:
            case Token.STRING:
                return doLiteral(node);
            case Token.PLUS:
                return doPlus(node);
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

    private DynamicVal doPlus(PNode node) {
        DynamicVal val1 = doNode(node.kids[0]);
        DynamicVal val2 = doNode(node.kids[1]);
        return val1.plus(val2);
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
