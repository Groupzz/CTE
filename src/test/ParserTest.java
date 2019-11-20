package test;



import main.Parser;
import main.Rule;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ParserTest {

    // This test determines if the LLTable has any entries that don't lead to an existing rule in Rule.java
    @Test
    public void testRulesExist() {
        for(int i = 0; i < Parser.llTable.length; i++) {
            for (int j = 0; j < Parser.llTable[0].length; j++) {
                int ruleID = Parser.llTable[i][j];
                if (ruleID == 0)
                    continue;
                Rule rule = Rule.getRule(ruleID);
                assertNotEquals("LLTable entry (" + i + ", " + j + ") must lead to an existing rule", rule, null);
            }
        }

    }

    // This test determines if the LLTable has any entries that lead to rules that don't make sense
    @Test
    public void testRulesValid() {
        for(int i = 0; i < Parser.llTable.length; i++) {
            for (int j = 0; j < Parser.llTable[0].length; j++) {
                int ruleID = Parser.llTable[i][j];
                if (ruleID == 0)
                    continue;
                Rule rule = Rule.getRule(ruleID);
                if(rule != null)
                    assertEquals("LLTable entry (" + i + ", " + j + ") must expand row header using rule it exists on the LHS of", rule.getLHS().getId(), i);
            }
        }

    }
}