package main;
/* CECS 444 Compiler Construction
 * Project 3: Interpreter
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: Dynamic value class that allows for dynamic typing to be used in A7.
 * The three types: int, float and string are able to be represented by an object of this type.
 * Arithmetic, relational, and boolean operators are supported by this class.
 * The interpreter uses this class when evaluating operators, assigning variables and reading from variables
 */
public class DynamicVal {
    int intVal;
    float floatVal;
    String strVal;
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
    DynamicVal(int val) {
        type = "INT";
        intVal = val;
    }

    DynamicVal(float val) {
        type = "FLOAT";
        floatVal = val;
    }

    DynamicVal(String val) {
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
        else if(this.type.equals("INT") && other.type.equals("FLOAT")) {
            return new DynamicVal(intVal + other.floatVal);
        }
        else if(this.type.equals("INT") && other.type.equals("STRING")) {
            return new DynamicVal(intVal + other.strVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            return new DynamicVal(floatVal + other.floatVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("INT")) {
            return new DynamicVal(floatVal + other.intVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("STRING")) {
            return new DynamicVal(floatVal + other.strVal);
        }
        else if(this.type.equals("STRING") && other.type.equals("STRING")) {
            return new DynamicVal(strVal + other.strVal);
        }
        else if(this.type.equals("STRING") && other.type.equals("INT")) {
            return new DynamicVal(strVal + other.intVal);
        }
        else if(this.type.equals("STRING") && other.type.equals("FLOAT")) {
            return new DynamicVal(strVal + other.floatVal);
        }
        else {
            throw new RuntimeException("Error: Cannot perform addition between " + this.type + " and " + other.type);
        }
    }

    public DynamicVal minus(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            return new DynamicVal(intVal - other.intVal);
        }
        else if(this.type.equals("INT") && other.type.equals("FLOAT")) {
            return new DynamicVal(intVal - other.floatVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            return new DynamicVal(floatVal - other.floatVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("INT")) {
            return new DynamicVal(floatVal - other.intVal);
        }
        else {
            throw new RuntimeException("Error: Cannot perform subtraction between " + this.type + " and " + other.type);
        }
    }

    public DynamicVal mul(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            return new DynamicVal(intVal * other.intVal);
        }
        else if(this.type.equals("INT") && other.type.equals("FLOAT")) {
            return new DynamicVal(intVal * other.floatVal);
        }
        else if(this.type.equals("INT") && other.type.equals("STRING")) {
            String tempStr = "";
            for(int i = 0; i < this.intVal; i++){
                tempStr += other.strVal;
            }
            return new DynamicVal(tempStr);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            return new DynamicVal(floatVal * other.floatVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("INT")) {
            return new DynamicVal(floatVal * other.intVal);
        }
        //cannot do float*string
        //cannot do string*string
        else if(this.type.equals("STRING") && other.type.equals("INT")) {
            String tempStr = "";
            for(int i = 0; i < other.intVal; i++){
                tempStr += this.strVal;
            }
            return new DynamicVal(tempStr);
        }
        //cannot do string*float
        else {
            throw new RuntimeException("Error: Cannot perform multiplication between " + this.type + " and " + other.type);
        }
    }

    public DynamicVal div(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            return new DynamicVal(intVal / other.intVal);
        }
        else if(this.type.equals("INT") && other.type.equals("FLOAT")) {
            return new DynamicVal(intVal / other.floatVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            return new DynamicVal(floatVal / other.floatVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("INT")) {
            return new DynamicVal(floatVal / other.intVal);
        }
        else {
            throw new RuntimeException("Error: Cannot perform division between " + this.type + " and " + other.type);
        }
    }

    public DynamicVal pow(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            return new DynamicVal((int) Math.pow(intVal, other.intVal));
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            return new DynamicVal((float) Math.pow(floatVal, other.floatVal));
        }
        else if(this.type.equals("FLOAT") && other.type.equals("INT")) {
            return new DynamicVal((float) Math.pow(floatVal, other.intVal));
        }
        else if(this.type.equals("INT") && other.type.equals("FLOAT")) {
            return new DynamicVal((float) Math.pow(intVal, other.floatVal));
        }
        else {
            throw new RuntimeException("Error: Cannot perform exponentiation between " + this.type + " and " + other.type);
        }
    }

    public DynamicVal equals(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            if(this.intVal == other.intVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("INT") && other.type.equals("FLOAT")) {
            if(this.intVal == other.floatVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            if(this.floatVal == other.floatVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("FLOAT") && other.type.equals("INT")) {
            if(this.floatVal == other.intVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("STRING") && other.type.equals("STRING")) {
            if(this.strVal.equals(other.strVal)){
                return new DynamicVal(1);
            }
        }
        else {
            throw new RuntimeException("Error: Cannot determine == equality between " + this.type + " and " + other.type);
        }
        return new DynamicVal(0);
    }

    public DynamicVal lessThan(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            if(this.intVal < other.intVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("INT") && other.type.equals("FLOAT")) {
            if(this.intVal < other.floatVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            if(this.floatVal < other.floatVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("FLOAT") && other.type.equals("INT")) {
            if(this.floatVal < other.intVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("STRING") && other.type.equals("STRING")) {
            if(this.strVal.compareTo(other.strVal) < 0){
                return new DynamicVal(1);
            }
        }
        else {
            throw new RuntimeException("Error: Cannot determine < inequality between " + this.type + " and " + other.type);
        }
        return new DynamicVal(0);
    }

    public DynamicVal lessThanOrEqual(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            if(this.intVal <= other.intVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("INT") && other.type.equals("FLOAT")) {
            if(this.intVal <= other.floatVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            if(this.floatVal <= other.floatVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("FLOAT") && other.type.equals("INT")) {
            if(this.floatVal <= other.intVal){
                return new DynamicVal(1);
            }
        }
        else if(this.type.equals("STRING") && other.type.equals("STRING")) {
            if(this.strVal.compareTo(other.strVal) <= 0){
                return new DynamicVal(1);
            }
        }
        else {
            throw new RuntimeException("Error: Cannot determine <= inequality between " + this.type + " and " + other.type);
        }
        return new DynamicVal(0);
    }

    public DynamicVal greaterThan(DynamicVal other) {
        DynamicVal lessOrEqual = this.lessThanOrEqual(other);
        if(!lessOrEqual.isTrue()) {
            return new DynamicVal(1);
        }
        return new DynamicVal(0);
    }

    public DynamicVal greaterThanOrEqual(DynamicVal other) {
        DynamicVal less = this.lessThan(other);
        if(!less.isTrue()) {
            return new DynamicVal(1);
        }
        return new DynamicVal(0);
    }

    public DynamicVal notEqual(DynamicVal other) {
        DynamicVal equal = this.equals(other);
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
