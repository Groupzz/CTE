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

    public static final Symbol DLVAL = new Symbol(74, false);
    
    public static final Symbol CLASSDECL = new Symbol(113, false);
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
