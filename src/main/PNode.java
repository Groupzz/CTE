package main;

public class PNode {

    private int ruleID;
    private PNode grandma;

    public PNode[] kids;
    public Symbol sym;

    PNode(Symbol sym, PNode gma) {
        this.ruleID = -1;
        this.sym = sym;
        this.grandma = gma;
        kids = new PNode[10];
    }

    @Override
    public String toString() {
        return sym.getId() + ":" + sym.isTerminal() + ":" + ruleID;
    }

    public void setRuleID(int ruleID) {
        this.ruleID = ruleID;
    }

    public String toString(int depth) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        for(int i = 0; i < depth; i++) {
            builder.append("  ");
        }
        if(sym.isTerminal()) {
            builder.append(sym.toString());
            builder.append(sym.getToken());
        }
        else {
            builder.append(sym.getId() + ":" + ruleID);
        }
        builder.append(" ( ");
        for(PNode p : kids) {
            if(p != null) {
                builder.append(p.toString(depth + 1));
            }
        }
        builder.append(" ) ");
//            return "( " + sym.getId() + " "
        return builder.toString();
    }

}