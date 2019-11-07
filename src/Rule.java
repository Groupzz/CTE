import java.util.HashMap;

public class Rule {

    private static HashMap<Integer, Rule> rules = new HashMap<>();

    static {
        rules.put(1, new Rule(Symbol.PGM, new Symbol(Token.KPROG, true), Symbol.MAIN));
        rules.put(2, new Rule(Symbol.MAIN, new Symbol(Token.KMAIN, true), Symbol.BBLOCK));
        rules.put(3, new Rule(Symbol.BBLOCK, new Symbol(Token.BRACE1, true), new Symbol(Token.BRACE2, true)));
        
       	rules.put(4, new Rule(Symbol.VARGROUP, new Symbol(Token.KVAR, true), Symbol.PPVARLIST));
        rules.put(5, new Rule(Symbol.VARGROUP));
        rules.put(6, new Rule(Symbol.PPVARLIST, new Symbol(Token.PARENS1, true), Symbol.VARLIST, new Symbol(Token.PARENS2, true)));
        rules.put(7, new Rule(Symbol.VARLIST, Symbol.VARITEM, new Symbol(Token.SEMI, true), Symbol.VARLIST));
        rules.put(8, new Rule(Symbol.VARLIST));

        rules.put(90, new Rule(Symbol.PPEXPR, new Symbol(Token.PARENS1, true), Symbol.EXPR, new Symbol(Token.PARENS2, true)));

        rules.put(97, new Rule(Symbol.FACT, Symbol.BASELITERAL));
        rules.put(99, new Rule(Symbol.FACT, Symbol.ADDROF_ID));
        rules.put(101, new Rule(Symbol.FACT, Symbol.PPEXPR));
        rules.put(102, new Rule(Symbol.BASELITERAL, new Symbol(Token.KINT, true)));
        rules.put(103, new Rule(Symbol.BASELITERAL, new Symbol(Token.KFLOAT, true)));
        rules.put(104, new Rule(Symbol.BASELITERAL, new Symbol(Token.KSTRING, true)));
    }

//    private int ruleID;
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

//    public int getRuleID() {
//        return ruleID;
//    }

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
