package main;

public class SymTabRow {

    private String name;
    private String type;
    private int line;
    private int col;
    private DynamicVal value;
    private PNode decl;

    SymTabRow(String name, String type, int line, int col, DynamicVal val, PNode decl) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.col = col;
        value = val;
        this.decl = decl;
    }

    void setValue(DynamicVal value) {
        if(value.type.equals(type))
            this.value = value;
        else{
            throw new RuntimeException("Error: Attempted to assign incorrect type");
        }
    }

    DynamicVal getValue() {
        return value;
    }

    public String toString() {
        return String.format("%-10s | %-8s | %-4d | %-4d | %-40s", name, type, line, col, value);
    }
}
