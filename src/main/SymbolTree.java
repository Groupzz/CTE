package main;

public class SymbolTree {

    private PNode root;
    private PNode curNode;

    public SymbolTree(PNode root) {
        this.root = root;
    }

    public String toString() {
        return root.toString(0);
    }

    public PNode getRoot() {
        return root;
    }

    // This is for non-terminals that have been expanded using an epsilon rule
    // They have no children and need to disappear
    // It goes into its parent and nulls out itself
    private static void pta_epsilon(PNode n) {
        PNode[] gmakids = n.getGrandma().kids;
        for(int i = 0; i < gmakids.length; i++) {
            // cant stop at null because another non-terminal may have nulled itself
            if (gmakids[i] == n) {
                gmakids[i] = null;
                break;
            }
        }
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

    private static void pta_bs1_k3(PNode n){
        PNode bigSis = n.kids[0];
        bigSis.kids[0] = n.kids[1];
        bigSis.kids[1] = n.kids[2];
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

    private static void pta_bs2_k2_assign(PNode n) {
        PNode bigSis = n.kids[1];
        bigSis.kids[1] = bigSis.kids[0];
        bigSis.kids[0] = n.kids[0];
        bigSis.hoist(n);
    }

    private static void pta_2k_recursion(PNode n) {
        if(n.kids[1] != null) {
            n.kids[1].hoist(n);
        }
        else {
            n.kids[0].hoist(n);
        }
    }

    private static void pta_lexpr(PNode n) {
        PNode bigSis = n.kids[0];
        PNode gma = n.getGrandma();
        if (gma.sym.equals(Symbol.LEXPR))
        {
            bigSis.kids[0] = gma.kids[1];
        }
        else if (gma.sym.equals(Symbol.EXPR)) {
            bigSis.kids[0] = gma.kids[0];
            gma.kids[0] = null;
        }
        else {
            System.out.println("Error converting LEXPR to AST");
            System.exit(1);
        }
        bigSis.kids[0].grandma = bigSis;
        if (n.kids[2] == null) { // if we are at the end of the chain
            bigSis.kids[1] = n.kids[1];
        }
        else {
            bigSis.kids[1] = n.kids[2];
        }
        bigSis.hoist(n);
    }

    private static void pta_lrterm(PNode n) {
        PNode bigSis = n.kids[0];
        PNode gma = n.getGrandma();
        if (gma.sym.equals(Symbol.LRTERM))
        {
            bigSis.kids[0] = gma.kids[1];
        }
        else if (gma.sym.equals(Symbol.RTERM)) {
            bigSis.kids[0] = gma.kids[0];
            gma.kids[0] = null;
        }
        else {
            System.out.println("Error converting LRTERM to AST");
            System.exit(1);
        }
        bigSis.kids[0].grandma = bigSis;
        if (n.kids[2] == null) { // if we are at the end of the chain
            bigSis.kids[1] = n.kids[1];
        }
        else {
            bigSis.kids[1] = n.kids[2];
        }
        bigSis.hoist(n);
    }

    private static void pta_lterm(PNode n) {
        PNode bigSis = n.kids[0];
        PNode gma = n.getGrandma();
        if (gma.sym.equals(Symbol.LTERM))
        {
            bigSis.kids[0] = gma.kids[1];
        }
        else if (gma.sym.equals(Symbol.TERM)) {
            bigSis.kids[0] = gma.kids[0];
            gma.kids[0] = null;
        }
        else {
            System.out.println("Error converting LTERM to AST");
            System.exit(1);
        }
        bigSis.kids[0].grandma = bigSis;
        if (n.kids[2] == null) { // if we are at the end of the chain
            bigSis.kids[1] = n.kids[1];
        }
        else {
            bigSis.kids[1] = n.kids[2];
        }
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
            case 49:
            case 57:
            case 58:
            case 69:
            case 70:
            case 71:
            case 72:
            case 76:
            case 97:
            case 99:
            case 101:
            case 102:
            case 103:
            case 104:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 121:
            case 124:
            case 127:
            case 130:
            case 133:
            case 150:
            case 154:
                pta_bs1_k1(n);
                break;
            case 2:
            case 4:
            case 12:
            case 31:
            case 32:
            case 53:
            case 55:
            case 61:
            case 62:
            case 87:
            case 122:
            case 125:
            case 128:
            case 131:
            case 132:
            case 134:
            case 139:
            case 149:
                pta_bs1_k2(n);
                break;
            case 6:
            case 86:
            case 90:
                pta_bs1_k3(n);
                break;
            case 1:
            case 3:
            case 56:
            case 82:
                pta_bs1_k4(n);
                break;
            case 129:
            case 135:
            case 137:
                pta_bs2_k2_assign(n);
                break;
            case 140:
                pta_lexpr(n);
                break;
            case 143:
                pta_lrterm(n);
                break;
            case 146:
                pta_lterm(n);
                break;
            case 141: 
            case 144:
            case 147:
                pta_2k_recursion(n);
                break;
            case 7:
            case 65:
                pta_bs2_k3(n);
                break;
            case 5:
            case 8:
            case 33:
            case 54:
            case 63:
            case 66:
            case 85:
            case 120:
            case 123:
            case 126:
            case 136:
            case 138:
            case 142:
            case 145:
            case 148:
            case 151:
                pta_epsilon(n);
                break;
            default:
             System.out.println(n.sym + ": no rule #" + rule + " found in AST conversion");
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
