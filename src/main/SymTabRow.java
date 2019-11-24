package main;

public class SymTabRow {

    private String name;
    private String type;
    private int line;
    private int col;
    private DynamicVal value;

    SymTabRow(String name, String type, int line, int col, DynamicVal val) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.col = col;
        value = val;
    }

    public void setValue(DynamicVal value) {
        if(value.type.equals(this.value.type))
            this.value = value;
        else{
            System.out.println("Error: Attempted to assign incorrect type");
        }
    }

    public String toString() {
        return String.format("%-10s | %-8s | %-4d | %-4d | %-40s", name, type, line, col, value);
    }
}
