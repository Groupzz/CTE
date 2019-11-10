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

    public static final Symbol CLASSMOM = new Symbol(39, false);
    public static final Symbol CLASSITEMS = new Symbol(40, false);
    public static final Symbol CLASSGROUP = new Symbol(41, false);
    public static final Symbol CLASS_CTR = new Symbol(42, false);
    public static final Symbol MDDECLS = new Symbol(43, false);
    public static final Symbol MDHEADER = new Symbol(44, false);
    public static final Symbol MD_ID = new Symbol(45, false);

    public static final Symbol FCNDEFS = new Symbol(53, false);
    public static final Symbol FCNDEF = new Symbol(54, false);
    public static final Symbol FCNHEADER = new Symbol(55, false);
    public static final Symbol FCNID = new Symbol(56, false);
    public static final Symbol RETKIND = new Symbol(57, false);
    public static final Symbol VARSPECS = new Symbol(58, false);
    public static final Symbol MORE_VARSPECS = new Symbol(59, false);
    public static final Symbol PPONLY = new Symbol(60, false);
    public static final Symbol STMTS = new Symbol(61, false);
    public static final Symbol PPARAMLIST = new Symbol(62, false);
    public static final Symbol STASGN = new Symbol(63, false);
    public static final Symbol LVAL = new Symbol(64, false);
    public static final Symbol AREF = new Symbol(65, false);
    public static final Symbol KKEXPR = new Symbol(66, false); 
    public static final Symbol STIF = new Symbol(67, false);
    public static final Symbol ELSEPART = new Symbol(68, false);
    public static final Symbol STWHILE = new Symbol(69, false);
    public static final Symbol STPRINT = new Symbol(70, false);
    public static final Symbol STRTN = new Symbol(71, false);
    public static final Symbol FCALL = new Symbol(72, false);

    public static final Symbol PPEXPR = new Symbol(90, false);
    public static final Symbol FACT = new Symbol(91, false);
    public static final Symbol BASELITERAL = new Symbol(92, false);
    public static final Symbol ADDROF_ID = new Symbol(93, false);
    public static final Symbol OPREL = new Symbol(94, false);
    public static final Symbol LTHAN = new Symbol(95, false);
    public static final Symbol GTHAN = new Symbol(96, false);
    public static final Symbol OPADD = new Symbol(97, false);
    public static final Symbol OPMUL = new Symbol(98, false);

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

    public static final Symbol LRTERM = new Symbol(113, false);
    public static final Symbol RTERM = new Symbol(114, false);
    public static final Symbol LTERM = new Symbol(115, false);
    public static final Symbol TERM = new Symbol(116, false);
    public static final Symbol DSTRTN = new Symbol(117, false);
    
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
