package main;

public class SymbolTree {

    private PNode root;
    private PNode curNode;

    SymbolTree(PNode root) {
        this.root = root;
    }

    public String toString() {
        return root.toString(0);
    }

    PNode getRoot() {
        return root;
    }

    // This is for non-terminals that have been expanded using an epsilon rule
    // They have no children and need to disappear
    // It goes into the parent of the node and nulls out the index pos
    private static void pta_disappear(PNode n, int pos) {
        n.getGrandma().kids[pos] = null;
    }

    // For rules that have only one symbol on their RHS
    // Naming scheme is bs1: big sister is first symbol on RHS AKA index 0
    // k1: There is one symbol on the RHS AKA one kid in PST
    private static void pta_bs1_k1(PNode n){
        PNode bigSis = n.kids[0];
        bigSis.hoist(n);
    }

    private static void pta_bs1_k2(PNode n){
        PNode bigSis = n.kids[0];
        bigSis.kids[0] = n.kids[1];
        bigSis.hoist(n);
    }

    private static void pta_bs1_k4(PNode n){
        PNode bigSis = n.kids[0];
        bigSis.kids[0] = n.kids[1];
        bigSis.kids[1] = n.kids[2];
        bigSis.kids[2] = n.kids[3];
        bigSis.hoist(n);
    }

    private static void pta_bs2_k3(PNode n) {
        PNode bigSis = n.kids[1];
        bigSis.kids[0] = n.kids[0];
        bigSis.kids[1] = n.kids[2];
        bigSis.hoist(n);
    }


// kept this for reference only but its working from the wrong node
//    private static void pta_varlist(PNode n){
//        if(n.getGrandma().kids[1] != null)
//        {
//            n.kids[0] = n.getGrandma().kids[0];
//            n.kids[1] = n.getGrandma().kids[2];
//            n.hoist(n.getGrandma());
//        }
//        else
//        System.out.println("varlist");
//    }

    private static void toAST(PNode n){
        if (n.sym.isTerminal()) {
            return;
        }
        int rule = n.ruleID;
        switch(rule)
        {
            // These all fall into eachother on purpose
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 26:
            case 27:
            case 28:
                pta_bs1_k1(n);
                break;
            case 1:
            case 2:
            case 4:
            case 6:
            case 12:
            case 125:
            case 137:
            case 139:
                pta_bs1_k2(n);
                break;
            case 3:
                pta_bs1_k4(n);
                break;
            case 7:
                pta_bs2_k3(n);
                break;
            case 5:
            case 126:
                pta_disappear(n, 1);
                break;
            case 8:
            case 66:
                pta_disappear(n, 2);
                break;
            default:
                System.out.println(n.sym + ": no rule found in AST conversion");
        }
    }

    private static void PST_to_AST(PNode pn){
        if(pn == null) return;
        for(int x = 0; x < pn.kids.length; x++)
        {
            PNode pkid = pn.kids[x];
            if(pkid == null) break;
            PST_to_AST(pkid);
        }
        toAST(pn);
    }

    void convertToAST() {
        PST_to_AST(root);
    }
}

