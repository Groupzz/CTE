public class Symbol {

    public static final Symbol PGM = new Symbol(1, false);
    public static final Symbol MAIN = new Symbol(2, false);
    public static final Symbol BBLOCK = new Symbol(3, false);

    public static final Symbol VARGROUP = new Symbol(4, false);
    public static final Symbol PPVARLIST = new Symbol(5, false);
    public static final Symbol VARLIST = new Symbol(6, false);
    public static final Symbol VARITEM = new Symbol(7, false);
    public static final Symbol VARDECL = new Symbol(8, false);
    public static final Symbol SIMPLEKIND = new Symbol(9, false);
    public static final Symbol BASEKIND = new Symbol(10, false);
    public static final Symbol CLASSID = new Symbol(11, false);
    public static final Symbol VARSPEC = new Symbol(12, false);
    public static final Symbol VARID = new Symbol(13, false);
    public static final Symbol ARRSPEC = new Symbol(14, false);
    public static final Symbol KKINT = new Symbol(15, false);
    public static final Symbol DEREF_ID = new Symbol(16, false);
    public static final Symbol DEREF = new Symbol(17, false);

    public static final Symbol VARINIT = new Symbol(18, false);
    public static final Symbol BBEXPRS = new Symbol(19, false);
    public static final Symbol EXPRLIST = new Symbol(20, false);
    public static final Symbol MOREEXPRS = new Symbol(21, false);
    
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
