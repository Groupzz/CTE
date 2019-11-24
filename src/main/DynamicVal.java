package main;

public class DynamicVal {
    private int intVal;
    private float floatVal;
    private String strVal;
    String type;

    DynamicVal(String type, String val) {
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
                    System.out.println("Error: Attempted to put invalid type in symbol table");
                    break;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Exception: Attempted to put invalid type in symbol table");
        }
    }

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

    DynamicVal plus(DynamicVal other) {
        if(this.type.equals("INT") && other.type.equals("INT")) {
            return new DynamicVal(intVal + other.intVal);
        }
        else if(this.type.equals("FLOAT") && other.type.equals("FLOAT")) {
            return new DynamicVal(floatVal + other.floatVal);
        }
        else {
            return null;
        }
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
