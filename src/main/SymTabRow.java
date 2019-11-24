package main;

public class SymTabRow {

    String name;
    String type;
    int line;
    int col;
    String value;

    SymTabRow(String name, String type, int line, int col, String val) {
        this.name = name;
        this.type = type;
        this.line = line;
        this.col = col;
        this.value = val;
    }


}
