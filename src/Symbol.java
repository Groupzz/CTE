public class Symbol {

    private int id;
    private boolean terminal;
    private Token token;

    public Symbol(int id) {
        this.id = id;
        terminal = false;
    }

    public Symbol(Token tok) {
        this.id = tok.getID();
        terminal = true;
        token = tok;
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
}
