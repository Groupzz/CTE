package main;

import java.util.ArrayList;

public class SymTabRow {

    private static ArrayList<DynamicVal> memory = new ArrayList<>();
    static {
        memory.add(null); // null 0th entry for null ptr
    }
    private static int memIndex = 1;

    static DynamicVal getFromMem(DynamicVal memAddr) {
        return memory.get(memAddr.intVal);
    }

    static DynamicVal setMem(DynamicVal memAddr, DynamicVal val) {
        return memory.set(memAddr.intVal, val);
    }

    private String name;
    private String type;
    private int line;
    private int col;
    private PNode decl;
    private int memAddr;
    private boolean isPtr;

    SymTabRow(String name, String type, int line, int col, DynamicVal val, PNode decl, boolean isPtr) {
        this.memAddr = memIndex++;
        this.name = name;
        this.type = type;
        this.line = line;
        this.col = col;
        this.decl = decl;
        memory.add(val);
        this.isPtr = isPtr;
    }

    SymTabRow(String name, String type, int line, int col, PNode decl, int arrSize) {
        this.memAddr = memIndex++;
        this.name = name;
        this.type = type;
        this.line = line;
        this.col = col;
        this.decl = decl;
        memory.add(new DynamicVal(memIndex));
        this.isPtr = true;

        for(int i = 0; i < arrSize; i++) {
            memIndex++;
            switch (type) {
                case "INT":
                    memory.add(new DynamicVal(0));
                    break;
                case "FLOAT":
                    memory.add(new DynamicVal(0.0f));
                    break;
                case "STRING":
                    memory.add(new DynamicVal(""));
                    break;
                default:
                    throw new RuntimeException("Error: Attempted to create array of invalid type");
            }
        }
    }

    boolean isPtr() {
        return isPtr;
    }

    void setValue(DynamicVal value) {
        if(!isPtr) {
            if(value.type.equals(type))
                memory.set(memAddr, value);
            else{
                throw new RuntimeException("Error: Attempted to assign incorrect type");
            }
        }
        else {
            if(value.type.equals("INT"))
                memory.set(memAddr, value);
            else{
                throw new RuntimeException("Error: Attempted to assign incorrect type");
            }
        }
    }

    DynamicVal getValue() {
        return memory.get(memAddr);
    }

    DynamicVal getMemAddr() {
        return new DynamicVal(memAddr);
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return String.format("%-10s | %-9s | %-8d | %-4d | %-4d | %-40s", name, type + (isPtr ? "PTR" : ""), memAddr, line, col, getValue());
    }
}
