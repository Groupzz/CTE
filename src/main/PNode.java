package main;
/* CECS 444 Compiler Construction
 * Project 2: Parser
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: PNode class that is used to represent both the PST and AST
 * Each node has a reference to the symbol stored within it, what rule was used to expand it (if any) a link to grandma
 * and an array of kids
 */
public class PNode {

    static int IDGenerator = 0;

    int ruleID;
    int nodeID;
    PNode grandma;

    PNode[] kids;
    Symbol sym;

    PNode(Symbol sym, PNode gma) {
        this.ruleID = -1;
        this.nodeID = IDGenerator++;
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
        builder.append(" ( ");
        builder.append("Node #" + nodeID + " ");
        if(sym.isTerminal()) {
            builder.append(sym.toString() + " " + sym.getToken().toString());
        }
        else {
            builder.append(sym.toString() + " Rule: " + ruleID);
        }

        for(PNode p : kids) {
            if(p != null) {
                builder.append(p.toString(depth + 1));
            }
        }
        builder.append(" ) ");
//            return "( " + sym.getId() + " "
        return builder.toString();
    }

    PNode getGrandma(){
        return grandma;
    }

    public void hoist(PNode mom){
        mom.sym = this.sym;
        mom.kids = this.kids;
        mom.ruleID = this.ruleID; // we only do this for debugging purposes
        // (hopefully it doesnt break something..)
    }

}