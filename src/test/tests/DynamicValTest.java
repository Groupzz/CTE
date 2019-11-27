package tests;

import main.DynamicVal;
import org.junit.Test;
import org.junit.Assert;

public class DynamicValTest {

    @Test
    public void testEqual() {

        DynamicVal val1 = new DynamicVal("INT", "12");
        DynamicVal val2 = new DynamicVal("INT", "12");
        Assert.assertTrue("Equals failed with two INTS", val1.equals(val2).isTrue());

        val1 = new DynamicVal("INT", "7");
        val2 = new DynamicVal("INT", "16");
        Assert.assertFalse("Equals failed with two INTS", val1.equals(val2).isTrue());
    }

    @Test
    public void testPlus() {

        DynamicVal val1 = new DynamicVal("INT", "2");
        DynamicVal val2 = new DynamicVal("INT", "12");
        Assert.assertTrue("Equals failed with two INTS", val1.plus(val2).equals(new DynamicVal("INT", "14")).isTrue());
    }
}
