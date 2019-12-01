package main;
/* CECS 444 Compiler Construction
 * Project 2: Parser
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: Wrapper class for PST and AST representations.
 * Contains all the methods necessary for converting the PST to an AST.
 * Has one attribute, the root node of the tree.
 */
public class SymbolTree {

    private PNode root;

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

    private static void hoistTerm(PNode n, int termIndex, int numKids) {
        PNode bigSis = n.kids[termIndex];
        int posCount = 0;
        for(int i = 0; i < numKids; i++) {
            if (i == termIndex)
                continue;
            bigSis.kids[posCount] = n.kids[i];
            posCount++;
        }
        bigSis.hoist(n);
    }

    private static void hoistNonterm(PNode n, int symIndex, int numKids) {
        PNode bigSis = n.kids[symIndex];
        PNode[] newKids = new PNode[10];
        int posCount = 0;
        for(int i = 0; i < symIndex; i++) {
            if(null != n.kids[i]) {
                newKids[posCount] = n.kids[i];
                posCount++;
            }
        }
        for(PNode node : bigSis.kids) {
            if(null == node) {
                break;
            }
            newKids[posCount] = node;
            posCount++;
        }
        for(int i = symIndex + 1; i < numKids; i++) {
            newKids[posCount] = n.kids[i];
            posCount++;
        }
        bigSis.kids = newKids;
        bigSis.hoist(n);
    }

    private static void pta_bs2_k2_assign(PNode n) {
        if(null != n.kids[1]) {
            PNode bigSis = n.kids[1];
            bigSis.kids[1] = bigSis.kids[0];
            bigSis.kids[0] = n.kids[0];
            bigSis.hoist(n);
        }
        else {
            PNode bigSis = n.kids[0];
            bigSis.hoist(n);
        }
    }

    private static void pta_2k_recursion(PNode n) {
        if(n.kids[1] != null) {
            n.kids[1].hoist(n);
        }
        else {
            n.kids[0].hoist(n);
        }
    }

    //Jamil - rule 23
    private static void pta_arrspec(PNode n){
        PNode bigSis = n.kids[1];
        bigSis.kids[2] = bigSis.kids[1];
        bigSis.kids[1] = bigSis.kids[0];
        bigSis.kids[0] = n.kids[0];
        bigSis.hoist(n);
    }

    // Specific method for LEXPR expansions
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

    // Specific method for LRTERM expansions
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

    // Specific method for LTERM expansions
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

    // Specific method for MOREEXPR expansions
    private static void pta_commas(PNode n) {
        PNode bigSis = n.kids[0];
        PNode gma = n.getGrandma();
        if (gma.sym.equals(Symbol.EXPRLIST) || gma.sym.equals(Symbol.VARSPECS))
        {
            bigSis.kids[0] = gma.kids[0];
            gma.kids[0] = null;
        }
        else {
            System.out.println("Error converting COMMA EXPR to AST");
            System.exit(1);
        }
        bigSis.kids[0].grandma = bigSis;

        bigSis.kids[1] = n.kids[1];
        bigSis.hoist(n);
    }

    // Rule 131
    private static void pta_dstmt(PNode n) {
        if(n.kids[1].sym.getId() == Token.EQUAL) { // assignment
            hoistNonterm(n, 1, 2);
        }
        else { // void function call
            hoistTerm(n, 0, 2);
        }
    }

    /**
     * This method is called for each tree in post order. It uses the ruleid of the node
     * to determine how to handle its conversion to AST and calls the correct function
     * @param n - The node we are working from
     */
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
            case 155:
            case 156:
                hoistTerm(n, 0, 1);
                break;
            case 2:
            case 4:
            case 12: // questionable
            case 25:
            case 84:
            case 87:
            case 105:
            case 122:
            case 125: // questionable
            case 128:
            case 132:
            case 134:
            case 139:
            case 149:
                hoistTerm(n, 0, 2);
                break;
            case 6:
            case 24:
            case 86:
            case 90:
                hoistTerm(n, 0, 3);
                break;
            case 1:
            case 3:
            case 56:
            case 82:
            case 83:
                hoistTerm(n, 0, 4);
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
            case 31:
            case 61:
            case 141: 
            case 144:
            case 147:
                pta_2k_recursion(n);
                break;
            case 7:
            case 65:
                hoistTerm(n, 1, 3);
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
            case 138:
            case 142:
            case 145:
            case 148:
            case 151:
                pta_epsilon(n);
                break;
            case 53:
            case 55:
                hoistNonterm(n, 0, 2);
                break;
            case 157:
                hoistNonterm(n, 1, 2);
                break;
            case 32:
            case 62:
                pta_commas(n);
                break;
            case 131:
                pta_dstmt(n);
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
