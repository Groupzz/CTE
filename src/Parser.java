import java.util.Scanner;
import java.util.Stack;

public class Parser {

    private static int[][] llTable = new int[100][100];

    static {
        // llTable[ROW/NON-T][COLUMN/TERMINAL]
        llTable[Symbol.PGM.getId()][Token.KPROG] = 1;
        llTable[Symbol.MAIN.getId()][Token.KMAIN] = 2;
        llTable[Symbol.BBLOCK.getId()][Token.BRACE1] = 3;
        llTable[Symbol.VARGROUP.getId()][Token.KVAR] = 4;

        llTable[Symbol.VARGROUP.getId()][Token.ID] = 5;
        llTable[Symbol.VARGROUP.getId()][Token.KFCN] = 5;
        llTable[Symbol.VARGROUP.getId()][Token.KIF] = 5;
        llTable[Symbol.VARGROUP.getId()][Token.KWHILE] = 5;
        llTable[Symbol.VARGROUP.getId()][Token.KPRINT] = 5;
        llTable[Symbol.VARGROUP.getId()][Token.KRETURN] = 5;

        llTable[Symbol.PPVARLIST.getId()][Token.PARENS1] = 6;

        llTable[Symbol.VARLIST.getId()][Token.KINT] = 7;
        llTable[Symbol.VARLIST.getId()][Token.KSTRING] = 7;
        llTable[Symbol.VARLIST.getId()][Token.KFLOAT] = 7;
        llTable[Symbol.VARLIST.getId()][Token.ID] = 7;
        llTable[Symbol.VARLIST.getId()][Token.KCLASS] = 7;

        llTable[Symbol.VARLIST.getId()][Token.PARENS2] = 8;

        llTable[Symbol.PPEXPR.getId()][Token.PARENS1] = 90;

        llTable[Symbol.EXPR.getId()][Token.PARENS1] = 141;
        llTable[Symbol.EXPR.getId()][Token.ID] = 141;
        llTable[Symbol.EXPR.getId()][Token.INT] = 141;
        llTable[Symbol.EXPR.getId()][Token.FLOAT] = 141;
        llTable[Symbol.EXPR.getId()][Token.STRING] = 141;
        llTable[Symbol.EXPR.getId()][Token.AMPERSAND] = 141;

        llTable[Symbol.RTERM.getId()][Token.PARENS1] = 144;
        llTable[Symbol.RTERM.getId()][Token.ID] = 144;
        llTable[Symbol.RTERM.getId()][Token.INT] = 144;
        llTable[Symbol.RTERM.getId()][Token.FLOAT] = 144;
        llTable[Symbol.RTERM.getId()][Token.STRING] = 144;
        llTable[Symbol.RTERM.getId()][Token.AMPERSAND] = 144;

        llTable[Symbol.TERM.getId()][Token.PARENS1] = 147;
        llTable[Symbol.TERM.getId()][Token.ID] = 147;
        llTable[Symbol.TERM.getId()][Token.INT] = 147;
        llTable[Symbol.TERM.getId()][Token.FLOAT] = 147;
        llTable[Symbol.TERM.getId()][Token.STRING] = 147;
        llTable[Symbol.TERM.getId()][Token.AMPERSAND] = 147;

        llTable[Symbol.FACT.getId()][Token.INT] = 97;
        llTable[Symbol.FACT.getId()][Token.FLOAT] = 97;
        llTable[Symbol.FACT.getId()][Token.STRING] = 97;
        llTable[Symbol.FACT.getId()][Token.AMPERSAND] = 99;
        llTable[Symbol.FACT.getId()][Token.PARENS1] = 101;
        llTable[Symbol.FACT.getId()][Token.ID] = 122;

        llTable[Symbol.BASELITERAL.getId()][Token.KINT] = 102;
        llTable[Symbol.BASELITERAL.getId()][Token.KFLOAT] = 103;
        llTable[Symbol.BASELITERAL.getId()][Token.KSTRING] = 104;

        llTable[Symbol.ADDROF_ID.getId()][Token.AMPERSAND] = 105;

        llTable[Symbol.OPREL.getId()][Token.OPEQ] = 106;
        llTable[Symbol.OPREL.getId()][Token.OPNE] = 107;
        llTable[Symbol.OPREL.getId()][Token.OPLE] = 109;
        llTable[Symbol.OPREL.getId()][Token.OPGE] = 110;
        llTable[Symbol.OPREL.getId()][Token.ANGLE1] = 108;
        llTable[Symbol.OPREL.getId()][Token.ANGLE2] = 111;

        llTable[Symbol.LTHAN.getId()][Token.ANGLE1] = 112;

        llTable[Symbol.GTHAN.getId()][Token.ANGLE2] = 113;

        llTable[Symbol.OPADD.getId()][Token.PLUS] = 114;
        llTable[Symbol.OPADD.getId()][Token.MINUS] = 115;

        llTable[Symbol.OPMUL.getId()][Token.ASTER] = 116;
        llTable[Symbol.OPMUL.getId()][Token.SLASH] = 117;
        llTable[Symbol.OPMUL.getId()][Token.CARET] = 118;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Stack<PNode> stack = new Stack<>();
        // Initialize a parse tree with the start symbol as the root node
        SymbolTree parseTree = new SymbolTree(new PNode(new Symbol(1, false), null));
        // This node is only used to check for valid end of input and does not end up in the parse tree
        stack.push(new PNode(new Symbol(Token.EOF, true), null));
        // Push the start symbol that is also the root of the parse tree onto the stack
        stack.push(parseTree.getRoot());

        Symbol curSymbol = new Symbol(Token.TokenBuilder(scanner.nextLine())); // Get first token of input

        while(!stack.empty()) {

            // m1: if top is the same as front, pop stack and advance input
            if(stack.peek().sym.equals(curSymbol)) {
                PNode curNode = stack.pop(); // save current node (a terminal symbol) so we can give it proper token info
                System.out.println("Top is same as front. Stack: " + stack);
                if(stack.isEmpty()) {
                    break; // if the latest pop was the end of input we can exit the loop
                    // we need this because otherwise the loop would hang waiting for more tokens to be input
                }
                // Since we matched this terminal symbol with the input
                // give the node in the parse tree the token information we got from the input
                curNode.sym = curSymbol;
                curSymbol = new Symbol(Token.TokenBuilder(scanner.nextLine())); // advance input
                continue;
            }
            // m2E: top is a terminal and we didn't match the input
            else if(stack.peek().sym.isTerminal()) {
//                throw new RuntimeException(String.format("Terminal not predicted by parser. Top of stack: %s ", stack.peek(), curSymbol));
                System.out.println("ERROR: Terminal " + curSymbol.getId() + " not predicted by parser");
                System.exit(1);
                break;
            }


            // Non-terminal on the stack, use correct rule from table to expand
            int cell = llTable[stack.peek().sym.getId()][curSymbol.getId()]; // id representing rule number
            // m3E: cell is empty
            if (cell == 0) {
                // if there isn't a rule in the table the input is grammatically incorrect. That's an error
                System.out.println("ERROR: No rule exists for given input");
                System.exit(1);
                break;
            }
            // m4: pop and push reverse of RHS
            PNode curNode = stack.pop(); // save top of stack so we can push RHS of rule to its kids in parse tree
            curNode.setRuleID(cell); // set the top of stack's rule to the one we matched for future AST conversion
            pushReverse(stack, Rule.getRule(cell), curNode); // method to push RHS to stack and kids at the same time
            System.out.println(stack + " : " + curSymbol); // debug print
        }
        // if there is still input but the stack is empty
        if(scanner.hasNext()) {
            System.out.println("ERROR: Expected EOF at " + curSymbol + " got " + scanner.nextLine() + " instead.");
            System.exit(1);
        }
        System.out.println("Passed grammar check");
        scanner.close();

        // Print out the parse tree
        System.out.println(parseTree);

    }


    private static void pushReverse(Stack<PNode> stack, Rule rule, PNode mom) {
        for(int i = rule.getRHSLength() - 1; i >= 0; i--) {
            // for each symbol in RHS of rule
            if(rule.getRHS(i) != null) {
                // create a node for that symbol pointing up to mom. Rule used is 0 for now
                PNode newNode = new PNode(rule.getRHS(i), mom);
                stack.push(newNode); // put the node into the stack
                mom.kids[i] = newNode; // make that node a kid of mom
            }
        }
    }
}
