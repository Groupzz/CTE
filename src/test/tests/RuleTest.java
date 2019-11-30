package tests;

import static main.Rule.firstSet;
import static main.Rule.followSet;
import static main.Symbol.*;

import main.Rule;
import main.Symbol;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class RuleTest {

    @BeforeClass
    public static void setupDisappearing() {
        Rule.findDisappearing();
    }

    @Test
    public void testFirstSet() {
        HashSet<Symbol> pgmTest = new HashSet<>();
        pgmTest.add(KPROG);
        Assert.assertEquals("First set of PGM incorrect", pgmTest, firstSet(PGM));

        HashSet<Symbol> factTest = new HashSet<>(Arrays.asList(PARENS1, INT, FLOAT, STRING, AMPERSAND, ID, ASTER));
        Assert.assertEquals("First set of FACT incorrect", factTest, firstSet(FACT));
        Assert.assertEquals("First set of TERM incorrect", factTest, firstSet(TERM));
        Assert.assertEquals("First set of EXPR incorrect", factTest, firstSet(EXPR));

        HashSet<Symbol> stmtTest = new HashSet<>(Arrays.asList(ID, KIF, KWHILE, KPRINT, KRETURN, ASTER));
        Assert.assertEquals("First set of STMT incorrect", stmtTest, firstSet(STMT));
        Assert.assertEquals("First set of STMTS incorrect", stmtTest, firstSet(STMTS));

        HashSet<Symbol> dppexprsTest = new HashSet<>(Arrays.asList(PARENS1, INT, FLOAT, STRING, AMPERSAND, ID, PARENS2, ASTER));
        Assert.assertEquals("First set of DPPEXPRS incorrect", dppexprsTest, firstSet(DPPEXPRS));
    }

    @Test
    public void testFollowSet() {
        HashSet<Symbol> dstmtTest = new HashSet<>();
        dstmtTest.add(SEMI);
        Assert.assertEquals("Follow set of DSTMT incorrect", dstmtTest, followSet(DSTMT));

        HashSet<Symbol> dvarspecTest = new HashSet<>(Arrays.asList(PARENS2, COMMA, SEMI, EQUAL));
        Assert.assertEquals("Follow set of DVARSPEC incorrect", dvarspecTest, followSet(DVARSPEC));

        HashSet<Symbol> lexprTest = new HashSet<>(Arrays.asList(SEMI, BRACKET2, PARENS2, COMMA, BRACE2));
        Assert.assertEquals("Follow set of LEXPR incorrect", lexprTest, followSet(LEXPR));

        HashSet<Symbol> dfactTest = new HashSet<>(Arrays.asList(PLUS, MINUS, OPEQ, OPNE, OPLE, OPGE, ANGLE1, ANGLE2,
                ASTER, CARET, SLASH, SEMI, BRACKET2, PARENS2, COMMA, BRACE2));
        Assert.assertEquals("Follow set of DFACT incorrect", dfactTest, followSet(DFACT));

        HashSet<Symbol> vargroupTest = new HashSet<>(Arrays.asList(KFCN, ID, KIF, KWHILE, KPRINT, KRETURN, KMAIN, BRACE2, ASTER));
        Assert.assertEquals("Follow set of VARGROUP incorrect", vargroupTest, followSet(VARGROUP));
    }
}
