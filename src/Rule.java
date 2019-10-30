import java.util.HashMap;

public class Rule {

    private static HashMap<Integer, Rule> rules = new HashMap<>();

    static {
        rules.put(1, new Rule(Symbol.PGM, new Symbol(Token.KPROG, true), Symbol.MAIN));
        rules.put(2, new Rule(Symbol.MAIN, new Symbol(Token.KMAIN, true), Symbol.BBLOCK));
        rules.put(3, new Rule(Symbol.BBLOCK, new Symbol(Token.BRACE1, true), new Symbol(Token.BRACE2, true)));
    }

    private int ruleID;
    private Symbol LHS;
    private Symbol[] RHS;

    public Rule(Symbol leftHandSymbol, Symbol ... rightHandSymbols) {
        LHS = leftHandSymbol;
        RHS = new Symbol[10];
        int count = 0;
        for(Symbol r : rightHandSymbols) {
            RHS[count] = r;
            count++;
        }
    }

    public static Rule getRule(int id) {
        return rules.get(id);
    }

    public int getRuleID() {
        return ruleID;
    }

    public Symbol getLHS() {
        return LHS;
    }

    public Symbol getRHS(int i) {
        return RHS[i];
    }

    public int getRHSLength() {
        return RHS.length;
    }

}
