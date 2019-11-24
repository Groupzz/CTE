package main;

public class Interpreter {

    private SymbolTree AST;

    private Interpreter() {
        AST = Parser.parseAndGenerateAST();
    }

    

    public static void main (String[] args) {
        Interpreter interpreter = new Interpreter();
        System.out.println(interpreter.AST);
    }


}
