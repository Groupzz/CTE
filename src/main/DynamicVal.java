package main;
/* CECS 444 Compiler Construction
 * Project 3: Interpreter
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: Dynamic value class that allows for dynamic typing to be used in A7.
 * The three types: int, float and string are able to be represented by an object of this type.
 * Arithmetic and boolean operators are supported by this class.
 * The interpreter uses this class when evaluating operators, assigning variables and reading from variables
 */
public class DynamicVal {
    private int intVal;
    private float floatVal;
    private String strVal;
    String type;

    // Attempts to parse the string input we get from the parser as whatever type we are expecting
    // This is called when declaring a variable
    public DynamicVal(String type, String val) {
        this.type = type;
        try {
            switch (type) {
                case "INT":
                    this.intVal = Integer.parseInt(val);
                    break;
                case "FLOAT":
                    this.floatVal = Float.parseFloat(val);
                    break;
                case "STRING":
                    this.strVal = val;
                    break;
                default:
                    throw new RuntimeException("Error: Attempted to put invalid type in a dynamic val");
            }
        }
        catch (NumberFormatException e) {
            throw new RuntimeException("Exception: Attempted to put invalid type in symbol table");
        }
    }

    /* These three constructors allow creating a dynamic val more easily
     * when we already know its type
     */
    private DynamicVal(int val) {
        type = "INT";
        intVal = val;
    }

    private DynamicVal(float val) {
        type = "FLOAT";
        floatVal = val;
    }

    private DynamicVal(String val) {
        type = "STRING";
        strVal = val;
    }

    /* Since there are no boolean types in A7, we use integers to represent them
     * An integer value of anything greater than 0 is considered 'true'
     * anything else is considered false.
     * This method returns true if this dynamicVal represents a true value for conditional statements
     */
    public boolean isTrue() {
        return intVal > 0;
    }

    public DynamicVal plus(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            return new DynamicVal(intVal + other.intVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            return new DynamicVal(floatVal + other.floatVal);
        }
        //Jamil additional condition
        else if(this.type.equals("FLOAT") && other.type.equals("INT")) {
            return new DynamicVal(floatVal + other.intVal);
        }
        //Jamil additional condition
        else if(this.type.equals("INT") && other.type.equals("FLOAT")) {
            return new DynamicVal(intVal + other.floatVal);
        }
        //Jamil additional condition
        else if(this.type.equals("STRING") && other.type.equals("STRING")) {
            return new DynamicVal(strVal + other.strVal);
        }
        else {
            return null;
        }
    }

    public DynamicVal minus(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            return new DynamicVal(intVal - other.intVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            return new DynamicVal(floatVal - other.floatVal);
        }
        else {
            return null;
        }
    }

    public DynamicVal mul(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            return new DynamicVal(intVal * other.intVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            return new DynamicVal(floatVal * other.floatVal);
        }
        else {
            return null;
        }
    }

    public DynamicVal equals(DynamicVal other) {
        //Jamil third condition separated and put into the inner if block of all
        if(this.type.equals("INT") && other.type.equals("INT")) {
            if(this.intVal == other.intVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            if(this.floatVal == other.floatVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("STRING") && other.type.equals("STRING")) {
            if(this.strVal.equals(other.strVal)){
                return new DynamicVal(1);
            }
        }
        //Jamil Exception
        else {
            throw new RuntimeException("Error: Cannot compare between " + this.type + " and " + other.type);
        }
        return new DynamicVal(0);
    }

    public DynamicVal lessThan(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            if(this.intVal < other.intVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            if(this.floatVal < other.floatVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("STRING") && other.type.equals("STRING")) {
            if(this.strVal.compareTo(other.strVal) < 0){
                return new DynamicVal(1);
            }
        }
        else {
            throw new RuntimeException("Error: Cannot compare between " + this.type + " and " + other.type);
        }
        return new DynamicVal(0);
    }

    //Jamil lessThanOrEqual function added
    public DynamicVal lessThanOrEqual(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            if(this.intVal <= other.intVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            if(this.floatVal <= other.floatVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("STRING") && other.type.equals("STRING")) {
            if(this.strVal.compareTo(other.strVal) <= 0){
                return new DynamicVal(1);
            }
        }
        else {
            throw new RuntimeException("Error: Cannot compare between " + this.type + " and " + other.type);
        }
        return new DynamicVal(0);
    }

    public DynamicVal greaterThan(DynamicVal other) {
        DynamicVal less = this.lessThan(other);
        DynamicVal equals = this.equals(other);
        //Jamil condition modified
        if(!less.isTrue() && !equals.isTrue()) {
            return new DynamicVal(1);
        }
        return new DynamicVal(0);
    }

    //Jamil greaterThanOrEqual function added
    public DynamicVal greaterThanOrEqual(DynamicVal other) {
        DynamicVal less = this.lessThan(other);
        DynamicVal equals = this.equals(other);
        //Jamil condition modified
        if(!less.isTrue()) {
            return new DynamicVal(1);
        }
        return new DynamicVal(0);
    }

    public DynamicVal notEqual(DynamicVal other) {
        DynamicVal equal = this.equals(other);
        //Jamil condition modified
        if(equal.isTrue()) {
            return new DynamicVal(0);
        }
        return new DynamicVal(1);
    }

    public String toString() {
        switch (type) {
            case "INT":
                return String.valueOf(intVal);
            case "FLOAT":
                return String.valueOf(floatVal);
            case "STRING":
                return strVal;
            default:
                return "NULL";
        }
    }
}
