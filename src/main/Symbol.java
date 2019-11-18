package main;
/* CECS 444 Compiler Construction
 * Project 2: Parser
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: Symbol class for use with the parser
 * Symbols are both terminals and non-terminals
 * This file mostly consists of every symbol given an ID and a constant variable to make programming
 * the parser easier.
 */
public class Symbol {

    // Non terminals
    public static final Symbol PGM = new Symbol(1, false);
    public static final Symbol MAIN = new Symbol(2, false);
    public static final Symbol BBLOCK = new Symbol(3, false);

    public static final Symbol VARGROUP = new Symbol(4, false);
    public static final Symbol PPVARLIST = new Symbol(5, false);
    public static final Symbol VARLIST = new Symbol(6, false);

    public static final Symbol VARDECL = new Symbol(7, false);
    public static final Symbol SIMPLEKIND = new Symbol(8, false);
    public static final Symbol BASEKIND = new Symbol(9, false);
    public static final Symbol CLASSID = new Symbol(10, false);
    public static final Symbol VARSPEC = new Symbol(11, false);
    public static final Symbol VARID = new Symbol(12, false);
    public static final Symbol ARRSPEC = new Symbol(13, false);
    public static final Symbol KKINT = new Symbol(14, false);
    public static final Symbol DEREF_ID = new Symbol(15, false);
    public static final Symbol DEREF = new Symbol(16, false);

    public static final Symbol VARINIT = new Symbol(17, false);
    public static final Symbol BBEXPRS = new Symbol(18, false);
    public static final Symbol EXPRLIST = new Symbol(19, false);
    public static final Symbol MOREEXPRS = new Symbol(20, false);

    public static final Symbol CLASSMOM = new Symbol(21, false);
    public static final Symbol CLASSITEMS = new Symbol(22, false);
    public static final Symbol CLASSGROUP = new Symbol(23, false);
    public static final Symbol CLASS_CTR = new Symbol(24, false);
    public static final Symbol MDDECLS = new Symbol(25, false);
    public static final Symbol MDHEADER = new Symbol(26, false);
    public static final Symbol MD_ID = new Symbol(27, false);

    public static final Symbol FCNDEFS = new Symbol(28, false);
    public static final Symbol FCNDEF = new Symbol(29, false);
    public static final Symbol FCNHEADER = new Symbol(30, false);
    public static final Symbol FCNID = new Symbol(31, false);
    public static final Symbol RETKIND = new Symbol(32, false);
    public static final Symbol VARSPECS = new Symbol(33, false);
    public static final Symbol MORE_VARSPECS = new Symbol(34, false);
    public static final Symbol PPONLY = new Symbol(35, false);
    public static final Symbol STMTS = new Symbol(36, false);
    public static final Symbol PPARAMLIST = new Symbol(37, false);
    public static final Symbol STASGN = new Symbol(38, false);
    public static final Symbol LVAL = new Symbol(39, false);
    public static final Symbol AREF = new Symbol(40, false);
    public static final Symbol KKEXPR = new Symbol(41, false); 
    public static final Symbol STIF = new Symbol(42, false);
    public static final Symbol ELSEPART = new Symbol(43, false);
    public static final Symbol STWHILE = new Symbol(44, false);
    public static final Symbol STPRINT = new Symbol(45, false);
    public static final Symbol STRTN = new Symbol(46, false);
    public static final Symbol FCALL = new Symbol(47, false);

    public static final Symbol PPEXPR = new Symbol(48, false);
    public static final Symbol FACT = new Symbol(49, false);
    public static final Symbol BASELITERAL = new Symbol(50, false);
    public static final Symbol ADDROF_ID = new Symbol(51, false);
    public static final Symbol OPREL = new Symbol(52, false);
    public static final Symbol LTHAN = new Symbol(53, false);
    public static final Symbol GTHAN = new Symbol(54, false);
    public static final Symbol OPADD = new Symbol(55, false);
    public static final Symbol OPMUL = new Symbol(56, false);

    public static final Symbol DFACT = new Symbol(57, false);

    public static final Symbol DVARSPEC = new Symbol(58, false);

    public static final Symbol PPARMLIST = new Symbol(59, false);
    public static final Symbol DPPARMLIST = new Symbol(60, false);

    public static final Symbol STMT = new Symbol(61, false);
    public static final Symbol DSTMT = new Symbol(62, false);

    public static final Symbol PPEXPRS = new Symbol(63, false);
    public static final Symbol DPPEXPRS = new Symbol(64, false);

    public static final Symbol VARITEM = new Symbol(65, false);
    public static final Symbol DVARITEM = new Symbol(66, false);

    public static final Symbol LEXPR = new Symbol(67, false);
    public static final Symbol EXPR = new Symbol(68, false);

    public static final Symbol LRTERM = new Symbol(69, false);
    public static final Symbol RTERM = new Symbol(70, false);
    public static final Symbol LTERM = new Symbol(71, false);
    public static final Symbol TERM = new Symbol(72, false);
    public static final Symbol DSTRTN = new Symbol(73, false);

    public static final Symbol DBBEXPRS = new Symbol(75, false);

    public static final Symbol DLVAL = new Symbol(74, false);
    
    public static final Symbol CLASSDECL = new Symbol(113, false);

    // Terminals

    public static final Symbol ID = new Symbol(Token.ID, true);
    public static final Symbol INT = new Symbol(Token.INT, true);
    public static final Symbol FLOAT = new Symbol(Token.FLOAT, true);
    public static final Symbol STRING = new Symbol(Token.STRING, true);
    public static final Symbol COMMA = new Symbol(Token.COMMA, true);
    public static final Symbol SEMI = new Symbol(Token.SEMI, true);

    // Keywords
    public static final Symbol KPROG = new Symbol(Token.KPROG, true);
    public static final Symbol KMAIN = new Symbol(Token.KMAIN, true);
    public static final Symbol KFCN = new Symbol(Token.KFCN, true);
    public static final Symbol KCLASS = new Symbol(Token.KCLASS, true);
    public static final Symbol KFLOAT = new Symbol(Token.KFLOAT, true);
    public static final Symbol KINT = new Symbol(Token.KINT, true);
    public static final Symbol KSTRING = new Symbol(Token.KSTRING, true);
    public static final Symbol KIF = new Symbol(Token.KIF, true);
    public static final Symbol KELSEIF = new Symbol(Token.KELSEIF, true);
    public static final Symbol KELSE = new Symbol(Token.KELSE, true);
    public static final Symbol KWHILE = new Symbol(Token.KWHILE, true);
    public static final Symbol KINPUT = new Symbol(Token.KINPUT, true);
    public static final Symbol KPRINT = new Symbol(Token.KPRINT, true);
    public static final Symbol KNEW = new Symbol(Token.KNEW, true);
    public static final Symbol KRETURN = new Symbol(Token.KRETURN, true);
    public static final Symbol KVAR = new Symbol(Token.KVAR, true);

    // Paired delimeters
    public static final Symbol ANGLE1 = new Symbol(Token.ANGLE1, true);
    public static final Symbol ANGLE2 = new Symbol(Token.ANGLE2, true);
    public static final Symbol BRACE1 = new Symbol(Token.BRACE1, true);
    public static final Symbol BRACE2 = new Symbol(Token.BRACE2, true);
    public static final Symbol BRACKET1 = new Symbol(Token.BRACKET1, true);
    public static final Symbol BRACKET2 = new Symbol(Token.BRACKET2, true);
    public static final Symbol PARENS1 = new Symbol(Token.PARENS1, true);
    public static final Symbol PARENS2 = new Symbol(Token.PARENS2, true);

    // Other punctuation tokens
    public static final Symbol ASTER = new Symbol(Token.ASTER, true);
    public static final Symbol CARET = new Symbol(Token.CARET, true);
    public static final Symbol COLON = new Symbol(Token.COLON, true);
    public static final Symbol DOT = new Symbol(Token.DOT, true);
    public static final Symbol EQUAL = new Symbol(Token.EQUAL, true);
    public static final Symbol MINUS = new Symbol(Token.MINUS, true);
    public static final Symbol PLUS = new Symbol(Token.PLUS, true);
    public static final Symbol SLASH =  new Symbol(Token.SLASH, true);
    public static final Symbol AMPERSAND = new Symbol(Token.AMPERSAND, true);

    // Multi-char operators
    public static final Symbol OPARROW = new Symbol(Token.OPARROW, true);
    public static final Symbol OPEQ = new Symbol(Token.OPEQ, true);
    public static final Symbol OPNE = new Symbol(Token.OPNE, true);
    public static final Symbol OPLE = new Symbol(Token.OPLE, true);
    public static final Symbol OPGE = new Symbol(Token.OPGE, true);
    public static final Symbol OPSHL = new Symbol(Token.OPSHL, true);
    public static final Symbol OPSHR = new Symbol(Token.OPSHR, true);


    private int id; // Ids are separate for terminals and non-terminals
    private boolean terminal; // True if this symbol is terminal, false if not
    private Token token;

    public Symbol(int id, boolean term) {
        this.id = id;
        terminal = term;
        token = null;
    }

    public Symbol(Token tok) {
        this.id = tok.getID();
        terminal = true;
        token = tok;
    }

    public int getId() {
        return id;
    }

    public Token getToken() {
        return token;
    }

    public boolean isTerminal() {
        return terminal;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof  Symbol) {
            Symbol o = (Symbol) other;
            // checks that both the id matches and they are both either terminal or nonterminal
            return this.id == o.id && this.terminal == o.terminal;
        }
        else {
            return false;
        }
    }

    public String toString() {
        return String.format("{%s: %d}", terminal ? "Terminal" : "Non-terminal", id);
    }
}
