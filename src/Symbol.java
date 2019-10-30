public class Symbol {

    public static final Symbol PGM = new Symbol(1, false);
    public static final Symbol MAIN = new Symbol(2, false);
    public static final Symbol BBLOCK = new Symbol(3, false);

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
