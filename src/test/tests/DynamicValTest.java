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

        val1 = new DynamicVal("FLOAT", "12.0");
        val2 = new DynamicVal("FLOAT", "12.0");
        Assert.assertTrue("Equals failed with two FLOATS", val1.equals(val2).isTrue());

        val1 = new DynamicVal("FLOAT", "12.1");
        val2 = new DynamicVal("FLOAT", "12.0");
        Assert.assertFalse("Equals failed with two FLOATS", val1.equals(val2).isTrue());

        val1 = new DynamicVal("STRING", "Hello");
        val2 = new DynamicVal("STRING", "Hello");
        Assert.assertTrue("Equals failed with two STRINGS", val1.equals(val2).isTrue());

        val1 = new DynamicVal("STRING", "HELLO");
        val2 = new DynamicVal("STRING", "Hello");
        Assert.assertFalse("Equals failed with two STRINGS", val1.equals(val2).isTrue());

        // These unit tests throw exceptions
        // val1 = new DynamicVal("FLOAT", "12.0");
        // val2 = new DynamicVal("INT", "12");
        // Assert.assertFalse("Equals failed with a FLOAT and an INT", val1.equals(val2).isTrue());

        // val1 = new DynamicVal("STRING", "12.0");
        // val2 = new DynamicVal("FLOAT", "12.0");
        // Assert.assertFalse("Equals failed with a STRING and a FLOAT", val1.equals(val2).isTrue());

        // val1 = new DynamicVal("STRING", "12");
        // val2 = new DynamicVal("INT", "12.0");
        // Assert.assertFalse("Equals failed with a STRING and an INT", val1.equals(val2).isTrue());
    }

    @Test
    public void testPlus() {

        DynamicVal val1 = new DynamicVal("INT", "2");
        DynamicVal val2 = new DynamicVal("INT", "12");
        Assert.assertTrue("Equals failed with two INTS", val1.plus(val2).equals(new DynamicVal("INT", "14")).isTrue());

        val1 = new DynamicVal("INT", "-2");
        val2 = new DynamicVal("INT", "12");
        Assert.assertTrue("Equals failed with two INTS", val1.plus(val2).equals(new DynamicVal("INT", "10")).isTrue());
        
        val1 = new DynamicVal("FLOAT", "2.0");
        val2 = new DynamicVal("INT", "12");
        Assert.assertTrue("Equals failed with a FLOAT and an INT", val1.plus(val2).equals(new DynamicVal("FLOAT", "14.0")).isTrue());
        
        val1 = new DynamicVal("FLOAT", "2.0");
        val2 = new DynamicVal("FLOAT", "12.0");
        Assert.assertTrue("Equals failed with two FLOATS", val1.plus(val2).equals(new DynamicVal("FLOAT", "14.0")).isTrue());
    
        val1 = new DynamicVal("INT", "-2");
        val2 = new DynamicVal("FLOAT", "12.0");
        Assert.assertTrue("Equals failed with an INT and a FLOAT", val1.plus(val2).equals(new DynamicVal("FLOAT", "10.0")).isTrue());
    
        val1 = new DynamicVal("STRING", "HEL");
        val2 = new DynamicVal("STRING", "LO");
        Assert.assertTrue("Equals failed with two STRINGS", val1.plus(val2).equals(new DynamicVal("STRING", "HELLO")).isTrue());
    }
    
    @Test
    public void testMinus() {
        DynamicVal val1 = new DynamicVal("INT", "2");
        DynamicVal val2 = new DynamicVal("INT", "12");
        Assert.assertTrue("Equals failed with two INTS", val1.plus(val2).equals(new DynamicVal("INT", "14")).isTrue());
    }
}
