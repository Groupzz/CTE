import java.util.Scanner;
import java.util.Stack;

public class Parser {

    static int llTable[][] = new int[100][100];

    static {
        // llTable[ROW/NON-T][COLUMN/TERMINAL]
        llTable[Symbol.PGM.getId()][Token.KPROG] = 1;
        llTable[Symbol.MAIN.getId()][Token.KMAIN] = 2;
        llTable[Symbol.BBLOCK.getId()][Token.BRACE1] = 3;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Stack<Symbol> stack = new Stack<>();
        stack.push(new Symbol(0, true)); // push EOF token
        stack.push(new Symbol(1, false)); // push start non-t

        Symbol curSymbol = new Symbol(Token.TokenBuilder(scanner.nextLine()));

        while(!stack.empty()) {

            // m1: if top is the same as front, pop stack and advance input
            if(stack.peek().equals(curSymbol)) {
                stack.pop();
                System.out.println("Top is same as front. Stack: " + stack);
                if(stack.isEmpty()) {
                    break;
                }
                curSymbol = new Symbol(Token.TokenBuilder(scanner.nextLine())); // advance input

                continue;
            }
            // m2E: top is a terminal and we didn't match the input
            else if(stack.peek().isTerminal()) {
//                throw new RuntimeException(String.format("Terminal not predicted by parser. Top of stack: %s ", stack.peek(), curSymbol));
                System.out.println("ERROR: Terminal " + curSymbol.getId() + " not predicted by parser");
                System.exit(1);
                break;
            }
            int cell = llTable[stack.peek().getId()][curSymbol.getId()];
            // m3E: cell is empty
            if (cell == 0) {
                System.out.println("ERROR: No rule exists for given input");
                System.exit(1);
                break;
            }
            // m4: pop and push reverse of RHS
            stack.pop();
            Rule.getRule(cell).pushReverse(stack);
            System.out.println(stack + " : " + curSymbol);
        }
        // if there is still input but the stack is empty
        if(scanner.hasNext()) {
            System.out.println("ERROR: Expected EOF at " + curSymbol + " got " + scanner.nextLine() + " instead.");
            System.exit(1);
        }
        System.out.println("Passed grammar check");
        scanner.close();
    }


}
