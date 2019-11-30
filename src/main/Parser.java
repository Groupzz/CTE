package main;
/* CECS 444 Compiler Construction
 * Project 2: Parser
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: Parser class that contains the LL table and LL parse mechanism code
 * The LLtable is hard coded and fills when the program is ran
 * Also contains the main function that takes each token line from stdin, converts into a token and uses
 * that to parse
 *
 */

import java.util.Scanner;
import java.util.Stack;
import java.util.Iterator;
import java.util.Arrays;

public class Parser {

    public static int[][] llTable = new int[300][60];

    static {
        // llTable[ROW/NON-T][COLUMN/TERMINAL]
        llTable[Symbol.PGM.getId()][Token.KPROG] = 1;
        llTable[Symbol.MAIN.getId()][Token.KMAIN] = 2;
        llTable[Symbol.BBLOCK.getId()][Token.BRACE1] = 3;
        llTable[Symbol.VARGROUP.getId()][Token.KVAR] = 4;
        addRule(Symbol.VARGROUP, 5, Token.ID, Token.KFCN, Token.KIF, Token.KWHILE, Token.KMAIN,
                Token.KPRINT, Token.KRETURN, Token.BRACE2);
        llTable[Symbol.PPVARLIST.getId()][Token.PARENS1] = 6;
        addRule(Symbol.VARLIST, 7, Token.KINT, Token.KSTRING, Token.KFLOAT, Token.ID, Token.KCLASS);
        llTable[Symbol.VARLIST.getId()][Token.PARENS2] = 8;

        addRule(Symbol.VARDECL, 12, Token.KINT, Token.KFLOAT, Token.KSTRING, Token.ID);

        addRule(Symbol.BASEKIND, 15, Token.KINT);
        addRule(Symbol.BASEKIND, 16, Token.KFLOAT);
        addRule(Symbol.BASEKIND, 17, Token.KSTRING);

        addRule(Symbol.VARSPEC, 21, Token.ASTER);
        llTable[Symbol.VARID.getId()][Token.ID] = 22;
        llTable[Symbol.ARRSPEC.getId()][Token.ID] = 23;
        addRule(Symbol.KKINT, 24, Token.BRACKET1);
        addRule(Symbol.DEREF_ID, 25, Token.ASTER);
        addRule(Symbol.DEREF, 26, Token.ASTER);
        addRule(Symbol.VARINIT, 27, Token.INT, Token.FLOAT, Token.STRING, Token.AMPERSAND, Token.ID, Token.PARENS1);
        addRule(Symbol.VARINIT, 28, Token.BRACE1);
        llTable[Symbol.BBEXPRS.getId()][Token.BRACE1] = 152;

        addRule(Symbol.EXPRLIST, 31, Token.PARENS1, Token.ID, Token.INT, Token.FLOAT, Token.STRING, Token.AMPERSAND);

        addRule(Symbol.MOREEXPRS, 33, Token.BRACE2, Token.PARENS2);
        llTable[Symbol.MOREEXPRS.getId()][Token.COMMA] = 32;

        llTable[Symbol.FCNDEFS.getId()][Token.KMAIN] = 54;
        llTable[Symbol.FCNDEFS.getId()][Token.KFCN] = 53;
        llTable[Symbol.FCNDEFS.getId()][Token.KMAIN] = 54;

        llTable[Symbol.FCNDEF.getId()][Token.KFCN] = 55;
        llTable[Symbol.FCNHEADER.getId()][Token.KFCN] = 56;
        llTable[Symbol.FCNID.getId()][Token.ID] = 57;
        addRule(Symbol.RETKIND, 58, Token.KINT, Token.KFLOAT, Token.KSTRING);

        addRule(Symbol.VARSPECS, 61, Token.ID, Token.ASTER);
        llTable[Symbol.MORE_VARSPECS.getId()][Token.COMMA] = 62;
        llTable[Symbol.MORE_VARSPECS.getId()][Token.PARENS2] = 63;
        llTable[Symbol.PPONLY.getId()][Token.PARENS1] = 64;
        addRule(Symbol.STMTS, 65, Token.ID, Token.KIF, Token.KWHILE, Token.KPRINT, Token.KRETURN);
        llTable[Symbol.STMTS.getId()][Token.BRACE2] = 66;

        llTable[Symbol.STMT.getId()][Token.KIF] = 69;
        llTable[Symbol.STMT.getId()][Token.KWHILE] = 70;
        llTable[Symbol.STMT.getId()][Token.KPRINT] = 71;
        llTable[Symbol.STMT.getId()][Token.KRETURN] = 72;
        addRule(Symbol.STASGN, 73, Token.ID, Token.ASTER);

        llTable[Symbol.LVAL.getId()][Token.ASTER] = 76;
        llTable[Symbol.AREF.getId()][Token.ID] = 77;
        llTable[Symbol.KKEXPR.getId()][Token.BRACKET1] = 78;
        llTable[Symbol.FCALL.getId()][Token.ID] = 79;
        llTable[Symbol.PPEXPRS.getId()][Token.PARENS1] = 134;
        llTable[Symbol.STIF.getId()][Token.KIF] = 82;
        llTable[Symbol.ELSEPART.getId()][Token.KELSEIF] = 83;
        llTable[Symbol.ELSEPART.getId()][Token.KELSE] = 84;
        llTable[Symbol.ELSEPART.getId()][Token.SEMI] = 85;
        llTable[Symbol.STWHILE.getId()][Token.KWHILE] = 86;
        llTable[Symbol.STPRINT.getId()][Token.KPRINT] = 87;

        llTable[Symbol.PPEXPR.getId()][Token.PARENS1] = 90;

        addRule(Symbol.FACT, 97, Token.INT, Token.FLOAT, Token.STRING);

        llTable[Symbol.FACT.getId()][Token.AMPERSAND] = 99;

        llTable[Symbol.FACT.getId()][Token.PARENS1] = 101;
        llTable[Symbol.BASELITERAL.getId()][Token.INT] = 102;
        llTable[Symbol.BASELITERAL.getId()][Token.FLOAT] = 103;
        llTable[Symbol.BASELITERAL.getId()][Token.STRING] = 104;
        llTable[Symbol.ADDROF_ID.getId()][Token.AMPERSAND] = 105;
        llTable[Symbol.OPREL.getId()][Token.OPEQ] = 106;
        llTable[Symbol.OPREL.getId()][Token.OPNE] = 107;
        llTable[Symbol.OPREL.getId()][Token.ANGLE1] = 108;
        llTable[Symbol.OPREL.getId()][Token.OPLE] = 109;
        llTable[Symbol.OPREL.getId()][Token.OPGE] = 110;
        llTable[Symbol.OPREL.getId()][Token.ANGLE2] = 111;
        llTable[Symbol.LTHAN.getId()][Token.ANGLE1] = 112;
        llTable[Symbol.GTHAN.getId()][Token.ANGLE2] = 113;
        llTable[Symbol.OPADD.getId()][Token.PLUS] = 114;
        llTable[Symbol.OPADD.getId()][Token.MINUS] = 115;
        llTable[Symbol.OPMUL.getId()][Token.ASTER] = 116;
        llTable[Symbol.OPMUL.getId()][Token.SLASH] = 117;
        llTable[Symbol.OPMUL.getId()][Token.CARET] = 118;
        llTable[Symbol.LVAL.getId()][Token.ID] = 119;

        llTable[Symbol.DLVAL.getId()][Token.EQUAL] = 120;
        llTable[Symbol.DLVAL.getId()][Token.BRACKET1] = 121;

        addRule(Symbol.PPARMLIST, 128, Token.PARENS1);
        addRule(Symbol.DPPARMLIST, 129, Token.ID, Token.ASTER);
        llTable[Symbol.DPPARMLIST.getId()][Token.PARENS2] = 130;

        llTable[Symbol.DSTMT.getId()][Token.PARENS1] = 133;

        addRule(Symbol.DPPEXPRS, 135, Token.PARENS1, Token.ID, Token.INT, Token.FLOAT, Token.STRING, Token.AMPERSAND);
        addRule(Symbol.DPPEXPRS, 136, Token.PARENS2);

        addRule(Symbol.DVARITEM, 138, Token.SEMI);

        //DBBEXPRS
        addRule(Symbol.DBBEXPRS, 153, Token.PARENS1, Token.ID, Token.INT, Token.FLOAT, Token.STRING, Token.AMPERSAND);
        addRule(Symbol.DBBEXPRS, 154, Token.BRACE2);

        llTable[Symbol.DLVAL.getId()][Token.BRACKET1] = 121;
        llTable[Symbol.FACT.getId()][Token.ID] = 122;
        addRule(Symbol.DFACT, 123, Token.PLUS, Token.MINUS, Token.OPEQ, Token.OPNE, Token.OPLE, Token.OPGE,
                Token.ANGLE1, Token.ANGLE2, Token.SLASH, Token.ASTER, Token.CARET, Token.BRACKET2, Token.PARENS2,
                Token.BRACE2, Token.COMMA, Token.SEMI);

        addRule(Symbol.DFACT, 124, Token.PARENS1);
        addRule(Symbol.VARSPEC, 125, Token.ID);
        addRule(Symbol.DVARSPEC, 126, Token.PARENS2, Token.SEMI, Token.EQUAL, Token.COMMA);
        addRule(Symbol.DVARSPEC, 127, Token.BRACKET1);

        llTable[Symbol.STMT.getId()][Token.ID] = 131;
        addRule(Symbol.DSTMT, 132, Token.EQUAL);
        llTable[Symbol.PPEXPRS.getId()][Token.PARENS1] = 134;

        addRule(Symbol.VARITEM, 137, Token.KINT, Token.KFLOAT, Token.KSTRING, Token.ID);

        addRule(Symbol.DVARITEM, 139, Token.EQUAL);
        addRule(Symbol.LEXPR, 140, Token.OPEQ, Token.OPNE, Token.OPLE, Token.OPGE, Token.ANGLE1, Token.ANGLE2);
        addRule(Symbol.EXPR, 141, Token.PARENS1, Token.ID, Token.INT, Token.FLOAT, Token.STRING, Token.AMPERSAND);
        addRule(Symbol.LEXPR, 142, Token.SEMI, Token.PARENS2, Token.BRACKET2, Token.COMMA, Token.BRACE2);
        addRule(Symbol.LRTERM, 143, Token.PLUS, Token.MINUS);
        addRule(Symbol.RTERM, 144, Token.PARENS1, Token.ID, Token.INT, Token.FLOAT, Token.STRING, Token.AMPERSAND);
        addRule(Symbol.LRTERM, 145, Token.SEMI, Token.PARENS2, Token.BRACKET2,
                Token.OPEQ, Token.OPNE, Token.OPLE, Token.OPGE, Token.ANGLE1, Token.ANGLE2, Token.BRACE2, Token.COMMA);
        addRule(Symbol.LTERM, 146, Token.ASTER, Token.SLASH, Token.CARET);
        addRule(Symbol.TERM, 147, Token.PARENS1, Token.ID, Token.INT, Token.FLOAT, Token.STRING, Token.AMPERSAND);
        addRule(Symbol.LTERM, 148, Token.PLUS, Token.MINUS, Token.SEMI, Token.PARENS2, Token.BRACKET2,
                Token.OPEQ, Token.OPNE, Token.OPLE, Token.OPGE, Token.ANGLE1, Token.ANGLE2, Token.BRACE2, Token.COMMA);
        llTable[Symbol.STRTN.getId()][Token.KRETURN] = 149;
        addRule(Symbol.DSTRTN, 150, Token.ID, Token.INT, Token.FLOAT, Token.STRING, Token.PARENS1, Token.AMPERSAND);
        addRule(Symbol.DSTRTN, 151, Token.SEMI);

        llTable[Symbol.DFACT.getId()][Token.PARENS1] = 124;

        // Use generated llTable instead
        llTable = new int[300][60];
        Rule.fillLLTable(llTable);
    }

    private static void addRule(Symbol rowHeader, int ruleNumber, int ... columns) {
        for (int column : columns) {
            if (llTable[rowHeader.getId()][column] != 0) {
                System.out.println("Attempted to double stuff! Row: " + rowHeader.toString() + " Col: " + column);
                System.exit(2);
            }
            llTable[rowHeader.getId()][column] = ruleNumber;
        }
    }

    private static Scanner scanner;
    private static Iterator<Token> tokIterator;

    public static boolean hasNextToken() {
        if (scanner != null) {
            return scanner.hasNext();
        }
        else {
            return tokIterator.hasNext();
        }
    }

    public static Symbol getNextToken() {
        if(scanner != null) {
            return new Symbol(Token.TokenBuilder(scanner.nextLine()));
        }
        else {
            if (tokIterator != null) {
                return new Symbol(tokIterator.next());
            }
            else {
                try {
                    Lexer.lexinput();
                }
                catch (Exception e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                    System.out.println("Faled to lex input");
                }
                tokIterator = Lexer.getTokens().iterator();
                return new Symbol(tokIterator.next());
            }
        }

    }

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
//        int[][] llTable2 = new int[300][60];
//        Rule.fillLLTable(llTable2);
//        llTable = llTable2;
//
//        for(int i = 0; i < llTable2.length; i++) {
//            for(int j = 0; j < llTable2[0].length; j++) {
//                if(llTable[i][j] != llTable2[i][j]) {
//                    System.out.println("LLTable Mismatch: COL: " + i + " ROW: " + j + " ORIG: " + llTable[i][j] + " GEN: " + llTable2[i][j]);
//                }
//            }
//        }

        SymbolTree parseTree = parseAndGenerateAST();
        scanner.close();
        // Print out the parse tree
        System.out.println("\nAbstract Syntax Tree:\n" + parseTree);
    }

    public static SymbolTree parseAndGenerateAST() {
        Stack<PNode> stack = new Stack<>();
        // Initialize a parse tree with the start symbol as the root node
        SymbolTree parseTree = new SymbolTree(new PNode(new Symbol(1, false), null));
        // This node is only used to check for valid end of input and does not end up in the parse tree
        stack.push(new PNode(new Symbol(Token.EOF, true), null));
        // Push the start symbol that is also the root of the parse tree onto the stack
        stack.push(parseTree.getRoot());

        Symbol curSymbol = getNextToken(); // Get first token of input


        while(!stack.empty()) {
            // m1: if top is the same as front, pop stack and advance input
            if(stack.peek().sym.equals(curSymbol)) {
                PNode curNode = stack.pop(); // save current node (a terminal symbol) so we can give it proper token info
//                System.out.println("Top is same as front. Stack: " + stack + " : " + curSymbol); // debug print
                if(stack.isEmpty()) {
                    break; // if the latest pop was the end of input we can exit the loop
                    // we need this because otherwise the loop would hang waiting for more tokens to be input
                }
                // Since we matched this terminal symbol with the input
                // give the node in the parse tree the token information we got from the input
                curNode.sym = curSymbol;
                curSymbol = getNextToken(); // advance input
                continue;
            }
            // m2E: top is a terminal and we didn't match the input
            else if(stack.peek().sym.isTerminal()) {
//                throw new RuntimeException(String.format("Terminal not predicted by parser. Top of stack: %s ", stack.peek(), curSymbol));
                System.out.println("ERROR: Terminal " + curSymbol.getId() + " at lin: " + curSymbol.getToken().getLin() + " Expected: " + stack.peek().sym);
                System.exit(1);
                break;
            }


            // Non-terminal on the stack, use correct rule from table to expand
            int cell = llTable[stack.peek().sym.getId()][curSymbol.getId()]; // id representing rule number
            // m3E: cell is empty
            if (cell == 0) {
                // if there isn't a rule in the table the input is grammatically incorrect. That's an error
                System.out.println("ERROR: No rule exists for given input: Stack: " + stack.peek() + " Input: " + curSymbol.toString()); // debug print
                Token errTok = curSymbol.getToken();
                System.out.println("Unexpected symbol '" + errTok.getStr() + "' at lin:" + errTok.getLin() + " col:" + errTok.getLinCol());
                System.exit(1);
                break;
            }
            // m4: pop and push reverse of RHS
            PNode curNode = stack.pop(); // save top of stack so we can push RHS of rule to its kids in parse tree
            curNode.setRuleID(cell); // set the top of stack's rule to the one we matched for future AST conversion
            pushReverse(stack, cell, curNode); // method to push RHS to stack and kids at the same time
//            System.out.println("Used rule: " + cell + " : " + stack + " : " + curSymbol); // debug print
        }
        // if there is still input but the stack is empty
        if(hasNextToken()) {
            System.out.println("ERROR: Expected EOF at " + curSymbol + " got " + getNextToken() + " instead.");
            System.exit(1);
        }
        System.out.println("\nParse Tree:\n" + parseTree);
        parseTree.convertToAST();
        return parseTree;
    }

    // Little function that takes a rule and expands it into both the parse tree and pushes it in reverse order
    // onto the stack
    private static void pushReverse(Stack<PNode> stack, int ruleNum, PNode mom) {
        Rule rule = Rule.getRule(ruleNum);
        if (rule == null) { // if the rule isnt there we print an error and crash so we can figure out what's wrong
            System.out.println("ERROR: Rule specified by LLTable does not exist in Rule.java. Rule #: " + ruleNum);
            System.exit(1);
        }
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
