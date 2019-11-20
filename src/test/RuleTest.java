package test;

import main.Rule;

import static main.Rule.firstSet;
import static main.Symbol.*;

import main.Symbol;
import main.Token;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class RuleTest {


    @Test
    public void testFirstSet() {
        HashSet<Symbol> pgmTest = new HashSet<>();
        pgmTest.add(KPROG);
        assertEquals("First set of PGM incorrect", pgmTest, firstSet(PGM));

        HashSet<Symbol> factTest = new HashSet<>();
        factTest.add(PARENS1);
        factTest.add(INT);
        factTest.add(FLOAT);
        factTest.add(STRING);
        factTest.add(AMPERSAND);
        factTest.add(ID);
        assertEquals("First set of FACT incorrect", factTest, firstSet(FACT));
        assertEquals("First set of TERM incorrect", factTest, firstSet(TERM));
        assertEquals("First set of EXPR incorrect", factTest, firstSet(EXPR));

        HashSet<Symbol> stmtTest = new HashSet<>();
        stmtTest.add(ID);
        stmtTest.add(KIF);
        stmtTest.add(KWHILE);
        stmtTest.add(KPRINT);
        stmtTest.add(KRETURN);
        assertEquals("First set of STMT incorrect", stmtTest, firstSet(STMT));
        assertEquals("First set of STMTS incorrect", stmtTest, firstSet(STMTS));

        HashSet<Symbol> dppexprsTest = new HashSet<>();
        dppexprsTest.add(PARENS1);
        dppexprsTest.add(INT);
        dppexprsTest.add(FLOAT);
        dppexprsTest.add(STRING);
        dppexprsTest.add(AMPERSAND);
        dppexprsTest.add(ID);
        assertEquals("First set of DPPEXPRS incorrect", dppexprsTest, firstSet(DPPEXPRS));
    }
}
