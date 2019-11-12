public class Symbol {

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

    public static final Symbol PPEXPR = new Symbol(90, false);
    public static final Symbol FACT = new Symbol(91, false);
    public static final Symbol BASELITERAL = new Symbol(92, false);
    public static final Symbol ADDROF_ID = new Symbol(93, false);
    public static final Symbol OPREL = new Symbol(94, false);
    public static final Symbol LTHAN = new Symbol(95, false);
    public static final Symbol GTHAN = new Symbol(96, false);
    public static final Symbol OPADD = new Symbol(97, false);
    public static final Symbol OPMUL = new Symbol(98, false);

    public static final Symbol DLVAL = new Symbol(99, false);

    public static final Symbol DFACT = new Symbol(100, false);

    public static final Symbol DVARSPEC = new Symbol(102, false);

    public static final Symbol PPARMLIST = new Symbol(103, false);
    public static final Symbol DPPARMLIST = new Symbol(104, false);

    public static final Symbol STMT = new Symbol(105, false);
    public static final Symbol DSTMT = new Symbol(106, false);

    public static final Symbol PPEXPRS = new Symbol(107, false);
    public static final Symbol DPPEXPRS = new Symbol(108, false);

    public static final Symbol VARITEM = new Symbol(109, false);
    public static final Symbol DVARITEM = new Symbol(110, false);

    public static final Symbol LEXPR = new Symbol(111, false);
    public static final Symbol EXPR = new Symbol(112, false);

    public static final Symbol LRTERM = new Symbol(143, false);
    public static final Symbol RTERM = new Symbol(144, false);

    public static final Symbol LTERM = new Symbol(146, false);
    public static final Symbol TERM = new Symbol(147, false);

    private int id;
    private boolean terminal;
    private Token token;

//    public Symbol(int id) {
//        this.id = id;
//        terminal = false;
//        token = null;
//    }

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
