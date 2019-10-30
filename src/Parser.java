import java.util.Scanner;
import java.util.Stack;

public class Parser {

    private static int[][] llTable = new int[100][100];

    static {
        // llTable[ROW/NON-T][COLUMN/TERMINAL]
        llTable[Symbol.PGM.getId()][Token.KPROG] = 1;
        llTable[Symbol.MAIN.getId()][Token.KMAIN] = 2;
        llTable[Symbol.BBLOCK.getId()][Token.BRACE1] = 3;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Stack<PNode> stack = new Stack<>();
        // Initialize a parse tree with the start symbol as the root node
        SymbolTree parseTree = new SymbolTree(new PNode(new Symbol(1, false), null));
        // This node is only used to check for valid end of input and does not end up in the parse tree
        stack.push(new PNode(new Symbol(0, true), null));
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
