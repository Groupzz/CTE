package main;
/* CECS 444 Compiler Construction
 * Project 2: Parser
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: Rule class that facilitates the LL parse mechanism
 * A rule object is made up of symbols, one is the RHS and the rest are the LHS of a grammar rule
 * Each rule is assigned an ID for easy access. The LL parse mechanism uses that ID to know what to push onto the stack
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static main.Symbol.*;


// Most of A7's rules are listed here. Rules related to classes/methods have been excluded or modified
public class Rule {

    private static HashMap<Integer, Rule> rules = new HashMap<>();

    static {
        rules.put(1, new Rule(PGM, KPROG, VARGROUP, FCNDEFS, MAIN));
        rules.put(2, new Rule(MAIN, KMAIN, BBLOCK));

        rules.put(3, new Rule(BBLOCK, BRACE1, VARGROUP, STMTS, BRACE2));
        
       	rules.put(4, new Rule(VARGROUP, KVAR, PPVARLIST));
        rules.put(5, new Rule(VARGROUP));
        rules.put(6, new Rule(PPVARLIST, PARENS1, VARLIST, PARENS2));
        rules.put(7, new Rule(VARLIST, VARITEM, SEMI, VARLIST));
        rules.put(8, new Rule(VARLIST));

        rules.put(11, new Rule(VARITEM, CLASSDECL));
        // the REAL rule 12 is not included

        rules.put(12, new Rule(VARDECL, SIMPLEKIND, VARSPEC));

        rules.put(13, new Rule(SIMPLEKIND, BASEKIND));
        rules.put(14, new Rule(SIMPLEKIND, CLASSID));
        rules.put(15, new Rule(BASEKIND, KINT));
        rules.put(16, new Rule(BASEKIND, KFLOAT));
        rules.put(17, new Rule(BASEKIND, KSTRING));
        rules.put(18, new Rule(CLASSID, ID));
        rules.put(21, new Rule(VARSPEC, DEREF_ID));
        rules.put(22, new Rule(VARID, ID));
        rules.put(23, new Rule(ARRSPEC, VARID, KKINT));
        rules.put(24, new Rule(KKINT, BRACKET1, INT, BRACKET2));
        rules.put(25, new Rule(DEREF_ID, DEREF, ID));
        rules.put(26, new Rule(DEREF, ASTER));

        rules.put(27, new Rule(VARINIT, EXPR));
        rules.put(28, new Rule(VARINIT, BBEXPRS));
        rules.put(31, new Rule(EXPRLIST, EXPR, MOREEXPRS));
        rules.put(32, new Rule(MOREEXPRS, COMMA, EXPRLIST));
        rules.put(33, new Rule(MOREEXPRS));
        rules.put(34, new Rule(CLASSDECL, KCLASS, CLASSID));

        rules.put(53, new Rule(FCNDEFS, FCNDEF, FCNDEFS));
        rules.put(54, new Rule(FCNDEFS));
        rules.put(55, new Rule(FCNDEF, FCNHEADER, BBLOCK));
        rules.put(56, new Rule(FCNHEADER, KFCN, FCNID, PPARMLIST, RETKIND));
        rules.put(57, new Rule(FCNID, ID));
        rules.put(58, new Rule(RETKIND, BASEKIND));

        rules.put(61, new Rule(VARSPECS, VARSPEC, MORE_VARSPECS));
        rules.put(62, new Rule(MORE_VARSPECS, COMMA, VARSPECS));
        rules.put(63, new Rule(MORE_VARSPECS));
        rules.put(64, new Rule(PPONLY, PARENS1, PARENS2));
        rules.put(65, new Rule(STMTS, STMT, SEMI, STMTS));
        rules.put(66, new Rule(STMTS));

        rules.put(69, new Rule(STMT, STIF));
        rules.put(70, new Rule(STMT, STWHILE));
        rules.put(71, new Rule(STMT, STPRINT));
        rules.put(72, new Rule(STMT, STRTN));
        rules.put(73, new Rule(STASGN, LVAL, EQUAL, EXPR));

        rules.put(76, new Rule(LVAL, DEREF_ID));
        rules.put(77, new Rule(AREF, VARID, KKEXPR));
        rules.put(78, new Rule(KKEXPR, BRACKET1, EXPR, BRACKET2));
        rules.put(79, new Rule(FCALL, FCNID, PPEXPRS));

        rules.put(82, new Rule(STIF, KIF, PPEXPR, BBLOCK, ELSEPART));

        rules.put(83, new Rule(ELSEPART, KELSEIF, PPEXPR, BBLOCK, ELSEPART));
        rules.put(84, new Rule(ELSEPART, KELSE, BBLOCK));
        rules.put(85, new Rule(ELSEPART));

        rules.put(86, new Rule(STWHILE, KWHILE, PPEXPR, BBLOCK));
        rules.put(87, new Rule(STPRINT, KPRINT, PPEXPRS));

        rules.put(90, new Rule(PPEXPR, PARENS1, EXPR, PARENS2));

        rules.put(97, new Rule(FACT, BASELITERAL));
        rules.put(99, new Rule(FACT, ADDROF_ID));
        rules.put(101, new Rule(FACT, PPEXPR));
        rules.put(102, new Rule(BASELITERAL, INT));
        rules.put(103, new Rule(BASELITERAL, FLOAT));
        rules.put(104, new Rule(BASELITERAL, STRING));
        rules.put(105, new Rule(ADDROF_ID, AMPERSAND, ID));
        rules.put(106, new Rule(OPREL, OPEQ));
        rules.put(107, new Rule(OPREL, OPNE));
        rules.put(108, new Rule(OPREL, LTHAN));
        rules.put(109, new Rule(OPREL, OPLE));
        rules.put(110, new Rule(OPREL, OPGE));
        rules.put(111, new Rule(OPREL, GTHAN));
        rules.put(112, new Rule(LTHAN, ANGLE1));
        rules.put(113, new Rule(GTHAN, ANGLE2));
        rules.put(114, new Rule(OPADD, PLUS));
        rules.put(115, new Rule(OPADD, MINUS));
        rules.put(116, new Rule(OPMUL, ASTER));
        rules.put(117, new Rule(OPMUL, SLASH));
        rules.put(118, new Rule(OPMUL, CARET));
        rules.put(119, new Rule(LVAL, VARID, DLVAL));
        rules.put(120, new Rule(DLVAL));
        rules.put(121, new Rule(DLVAL, KKEXPR));
        rules.put(122, new Rule(FACT, new Symbol(Token.ID, true), DFACT));

        rules.put(123, new Rule(DFACT));
        rules.put(124, new Rule(DFACT, PPEXPRS));

        rules.put(125, new Rule(VARSPEC, ID, DVARSPEC));
        rules.put(126, new Rule(DVARSPEC));
        rules.put(127, new Rule(DVARSPEC, KKINT));

        rules.put(128, new Rule(PPARMLIST, PARENS1, DPPARMLIST));
        rules.put(129, new Rule(DPPARMLIST, VARSPECS, PARENS2));
        rules.put(130, new Rule(DPPARMLIST, PARENS2));

        rules.put(131, new Rule(STMT, ID, DSTMT));
        rules.put(132, new Rule(DSTMT, EQUAL, EXPR));
        rules.put(133, new Rule(DSTMT, PPEXPRS));

        rules.put(134, new Rule(PPEXPRS, PARENS1, DPPEXPRS));
        rules.put(135, new Rule(DPPEXPRS, EXPRLIST, PARENS2));

        rules.put(136, new Rule(DPPEXPRS, PARENS2));
        // it used to be the below statement
//        rules.put(136, new Rule(DPPEXPRS));

        rules.put(137, new Rule(VARITEM, VARDECL, DVARITEM));
        rules.put(138, new Rule(DVARITEM));
        rules.put(139, new Rule(DVARITEM, EQUAL, VARINIT));

        rules.put(140, new Rule(LEXPR, OPREL, RTERM, LEXPR));
        rules.put(141, new Rule(EXPR, RTERM, LEXPR));
        rules.put(142, new Rule(LEXPR));

        rules.put(143, new Rule(LRTERM, OPADD, TERM, LRTERM));
        rules.put(144, new Rule(RTERM, TERM, LRTERM));
        rules.put(145, new Rule(LRTERM));

        rules.put(146, new Rule(LTERM, OPMUL, FACT, LTERM));
        rules.put(147, new Rule(TERM, FACT, LTERM));
        rules.put(148, new Rule(LTERM));

        rules.put(149, new Rule(STRTN, KRETURN, DSTRTN));
        rules.put(150, new Rule(DSTRTN, EXPR));
        rules.put(151, new Rule(DSTRTN));

        rules.put(152, new Rule(BBEXPRS, BRACE1, DBBEXPRS));
        rules.put(153, new Rule(DBBEXPRS, EXPRLIST, BRACE2));
        rules.put(154, new Rule(DBBEXPRS, BRACE2));

        // Missing LVAL rules
        rules.put(155, new Rule(DFACT, KKEXPR));
        rules.put(156, new Rule(FACT, DEREF_ID));

        // More missing LVAL rules
        rules.put(157, new Rule(STMT, DEREF_ID, DSTMT));
        rules.put(158, new Rule(DSTMT, KKEXPR, EQUAL, EXPR)); // adds support for LHS arrays
    }

    private static HashSet<Symbol> disappearing = new HashSet<>();

    private Symbol LHS;
    private Symbol[] RHS;

    // Creates a rule, the first symbol being the LHS of the grammar rule
    // and all arguments after that making up the RHS
    // Adding more than 10 symbols to the RHS will throw an exception
    private Rule(Symbol leftHandSymbol, Symbol ... rightHandSymbols) {
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

    public static void main(String[] args) {
//        findDisappearing();
        System.out.println(firstSet(DVARITEM));
    }

    public static void findDisappearing() {
        Set<Integer> ruleIDS = rules.keySet();
        boolean foundNew = true;
        while(foundNew) {
            foundNew = false;
            for (Integer id : ruleIDS) {
                Rule rule = getRule(id);
                if (disappearing.contains(rule.getLHS()))
                    continue;
                boolean allGone = true;
                for (Symbol sym : rule.RHS) {
                    if (sym == null)
                        continue;
                    if (!disappearing.contains(sym)) {
                        allGone = false;
                        break;
                    }
                }
                if (allGone) {
                    disappearing.add(rule.getLHS());
                    foundNew = true;
                }
            }
        }
    }

    public static void fillLLTable(int[][] llTable) {
        findDisappearing();
        for(Integer id : rules.keySet()) {
            Rule rule = rules.get(id);
            if(null != rule.RHS[0]) { // if its not an epsilon rule
                HashSet<Symbol> firstSet = firstSet(rule);
                addColumns(llTable, rule.LHS.getId(), id, firstSet);
            }
            else { // if it is an epsilon rule
                HashSet<Symbol> followSet = followSet(rule.LHS);
                addColumns(llTable, rule.LHS.getId(), id, followSet);
            }
        }
    }

    private static void addColumns(int[][] llTable, int symID, int ruleID, HashSet<Symbol> columns) {
        for(Symbol term : columns) {
            if (llTable[symID][term.getId()] == 0) {
                llTable[symID][term.getId()] = ruleID;
            }
            else {
                System.out.println("DOUBLE STUFF: " + symID + " " + term + "RULEID: " + ruleID + " PREVRULE: " + llTable[symID][term.getId()] + "<--------------");
            }
        }
    }

    public static HashSet<Symbol> firstSet(Rule rule) {
        if(null != rule.RHS[0]) {
            return firstSet(rule.RHS[0]);
        }
        return new HashSet<>();
    }

    public static HashSet<Symbol> firstSet(Symbol sym) {
        Set<Integer> ruleIDS = rules.keySet();
        HashSet<Symbol> firstSet = new HashSet<>();
        if (sym.isTerminal()) {
            firstSet.add(sym);
            return firstSet;
        }
        for(Integer ruleID : ruleIDS) {
            Rule rule = getRule(ruleID);
            if(rule.LHS.equals(sym)) { // If this is one of our rules
                for(Symbol rhsym : rule.RHS) { // we loop through all of them in case there's a disappearing non-t
                    if(null == rhsym) {
                        break;
                    }
//                    if(rhsym.isTerminal()) {
//                        firstSet.add(rhsym);
//                        break; // break if its a terminal, terminals don't disappear
//                    }
                    else {
                        firstSet.addAll(firstSet(rhsym));
                        if (!disappearing.contains(rhsym)) {
                            break; // if its not disappearing, we don't need to loop again
                        }
                    }
                }

            }
        }
        return firstSet;
    }

    public static HashSet<Symbol> followSet(Symbol sym) {
        return followSet(sym, new HashSet<>());
    }

    private static HashSet<Symbol> followSet(Symbol sym, HashSet<Symbol> repeats) {
        repeats.add(sym); // remember that we already tried to find the follow set of this symbol
        HashSet<Symbol> followSet = new HashSet<>();
        for(Rule rule : rules.values()) {
            for(int i = 0; i < rule.RHS.length; i++) {
                if(null == rule.RHS[i]) {
                    break; // we reached the end of the rule
                }
                if(rule.RHS[i].equals(sym)) { // if we are computing this symbol's follow set
                    for(int j = i + 1; j < rule.RHS.length; j++) {
                        // loop to the end in case of disappearing non-terminals
                        if(null == rule.RHS[j]) { // if we are at the last symbol of the rule
                            if (!repeats.contains(rule.LHS)) {
                                // check if we already tried to find the follow set of this symbol
                                // this prevents infinite recursion
                                followSet.addAll(followSet(rule.LHS, repeats));
                            }
                            break;
                        }
                        if(rule.RHS[j].isTerminal()) {
                            followSet.add(rule.RHS[j]);
                            break;
                        }
                        else { // if the next symbol is non-terminal
                            followSet.addAll(firstSet(rule.RHS[j]));
                            if(!disappearing.contains(rule.RHS[j])) {
                                break; // if this isn't a disappearing non-terminal we can stop iterating
                            }
                        }
                    }
                    break;
                }
            }
        }
        return followSet;
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
