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
        
        rules.put(12, new Rule(Symbol.VARDECL,Symbol.SIMPLEKIND, Symbol.VARSPEC));
        rules.put(13, new Rule(Symbol.SIMPLEKIND, Symbol.BASEKIND));
        rules.put(14, new Rule(Symbol.SIMPLEKIND, Symbol.CLASSID));
        rules.put(15, new Rule(Symbol.BASEKIND, new Symbol(Token.KINT, true)));
        rules.put(16, new Rule(Symbol.BASEKIND, new Symbol(Token.KFLOAT, true)));
        rules.put(17, new Rule(Symbol.BASEKIND, new Symbol(Token.KSTRING, true)));
        rules.put(18, new Rule(Symbol.CLASSID, new Symbol(Token.ID, true)));
        rules.put(21, new Rule(Symbol.VARSPEC, Symbol.DEREF_ID));
        rules.put(22, new Rule(Symbol.VARID, new Symbol(Token.ID, true)));
        rules.put(23, new Rule(Symbol.ARRSPEC, Symbol.VARID, Symbol.KKINT));
        rules.put(24, new Rule(Symbol.KKINT, new Symbol(Token.BRACKET1, true), new Symbol(Token.INT, true), new Symbol(Token.BRACKET2, true)));
        rules.put(25, new Rule(Symbol.DEREF_ID, Symbol.DEREF, new Symbol(Token.ID, true)));
        rules.put(26, new Rule(Symbol.DEREF, new Symbol(Token.ASTER, true)));

        rules.put(27, new Rule(Symbol.VARINIT, Symbol.EXPR));
        rules.put(28, new Rule(Symbol.VARINIT, Symbol.BBEXPRS));
        rules.put(31, new Rule(Symbol.EXPRLIST, Symbol.EXPR, Symbol.MOREEXPRS));
        rules.put(32, new Rule(Symbol.MOREEXPRS, new Symbol(Token.COMMA, true), Symbol.EXPRLIST));
        rules.put(34, new Rule(Symbol.CLASSDECL, new Symbol(Token.KCLASS, true), Symbol.CLASSID));

        rules.put(79, new Rule(Symbol.FCALL, Symbol.FCNID, Symbol.PPEXRS));


        rules.put(27, new Rule(Symbol.VARINIT, Symbol.EXPR));
        rules.put(28, new Rule(Symbol.VARINIT, Symbol.BBEXPRS));
        rules.put(31, new Rule(Symbol.EXPRLIST, Symbol.EXPR, Symbol.MOREEXPRS));
        rules.put(32, new Rule(Symbol.MOREEXPRS, new Symbol(Token.COMMA, true), Symbol.EXPRLIST));
        rules.put(33, new Rule(Symbol.MOREEXPRS));

        rules.put(53, new Rule(Symbol.FCNDEFS, Symbol.FCNDEF, Symbol.FCNDEFS));

        rules.put(90, new Rule(Symbol.PPEXPR, new Symbol(Token.PARENS1, true), Symbol.EXPR, new Symbol(Token.PARENS2, true)));

        rules.put(97, new Rule(Symbol.FACT, Symbol.BASELITERAL));
        rules.put(99, new Rule(Symbol.FACT, Symbol.ADDROF_ID));
        rules.put(101, new Rule(Symbol.FACT, Symbol.PPEXPR));
        rules.put(102, new Rule(Symbol.BASELITERAL, new Symbol(Token.KINT, true)));
        rules.put(103, new Rule(Symbol.BASELITERAL, new Symbol(Token.KFLOAT, true)));
        rules.put(104, new Rule(Symbol.BASELITERAL, new Symbol(Token.KSTRING, true)));
        rules.put(105, new Rule(Symbol.ADDROF_ID, new Symbol(Token.AMPERSAND, true), new Symbol(Token.ID, true)));
        rules.put(106, new Rule(Symbol.OPREL, new Symbol(Token.OPEQ, true)));
        rules.put(107, new Rule(Symbol.OPREL, new Symbol(Token.OPNE, true)));
        rules.put(108, new Rule(Symbol.OPREL, Symbol.LTHAN));
        rules.put(109, new Rule(Symbol.OPREL, new Symbol(Token.OPLE, true)));
        rules.put(110, new Rule(Symbol.OPREL, new Symbol(Token.OPGE, true)));
        rules.put(111, new Rule(Symbol.OPREL, Symbol.GTHAN));
        rules.put(112, new Rule(Symbol.LTHAN, new Symbol(Token.ANGLE1, true)));
        rules.put(113, new Rule(Symbol.GTHAN, new Symbol(Token.ANGLE2, true)));
        rules.put(114, new Rule(Symbol.OPADD, new Symbol(Token.PLUS, true)));
        rules.put(115, new Rule(Symbol.OPADD, new Symbol(Token.MINUS, true)));
        rules.put(116, new Rule(Symbol.OPMUL, new Symbol(Token.ASTER, true)));
        rules.put(117, new Rule(Symbol.OPMUL, new Symbol(Token.SLASH, true)));
        rules.put(118, new Rule(Symbol.OPMUL, new Symbol(Token.CARET, true)));

        rules.put(120, new Rule(Symbol.DLVAL));

        rules.put(123, new Rule(Symbol.DFACT));
        rules.put(124, new Rule(Symbol.DFACT, Symbol.PPEXPRS));

        rules.put(126, new Rule(Symbol.DVARSPEC));
        rules.put(127, new Rule(Symbol.DVARSPEC, Symbol.KKINT));

        rules.put(128, new Rule(Symbol.PPARMLIST, new Symbol(Token.PARENS1, true), Symbol.DPPARMLIST));
        //rule 129 goes here
        rules.put(130, new Rule(Symbol.DPPARMLIST, new Symbol(Token.PARENS2, true)));

        rules.put(131, new Rule(Symbol.STMT, new Symbol(Token.ID, true), Symbol.DSTMT));
        rules.put(132, new Rule(Symbol.DSTMT, new Symbol(Token.EQUAL, true), Symbol.EXPR));
        rules.put(133, new Rule(Symbol.DSTMT, Symbol.PPEXPRS));

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
