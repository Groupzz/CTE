package main;
/* CECS 444 Compiler Construction
 * Project 3: Interpreter
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
 * Description: SymTabRow that represents an entry in a symbol table
 * Contains name of the identifier, its type, line and column it was declared, a link to the AST declaration node,
 * memory address of stored value and a boolean of whether or not it is a pointer type
 * Contains methods to set and get the value in memory
 * Class contains static 'memory' array that provides addresses for use with dereference and ampersand operators
 */
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
                throw new RuntimeException("Error: Attempted to assign incorrect type. GOT " + value.type + " EXPECTED " + type);
            }
        }
        else {
            if(value.type.equals("INT"))
                memory.set(memAddr, value);
            else{
                throw new RuntimeException("Error: Attempted to assign incorrect type. GOT " + value.type + " EXPECTED INT");
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
