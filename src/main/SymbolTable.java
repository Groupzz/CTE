package main;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

    static SymbolTable root = new SymbolTable(null, null); // scope tree root

    private SymbolTable parent = null; // scope node parent
    private ArrayList<SymbolTable> kids = new ArrayList<>(); // scope node kids
    private HashMap<String, SymTabRow> rows = new HashMap<>();
    PNode bblock = null;

    private SymbolTable(SymbolTable par, PNode bblock) {
        parent = par;
        this.bblock = bblock;
    }

    public SymbolTable addNewScope(PNode bblock) {
        kids.add(new SymbolTable(this, bblock));
        return kids.get(kids.size() - 1);
    }

    void declareVar(DynamicVal val, String type, Token id) {

        if(!rows.containsKey(id.getStr())) {
            SymTabRow newRow = new SymTabRow(id.getStr(), type, id.getLin(), id.getLinCol(), val);
            rows.put(id.getStr(), newRow);
        }
        else {
            System.out.println("Runtime error: Identifier '" + id.getStr() +  "' already exists in symbol table for this scope: lin: " + id.getLin() + " col: " + id.getLinCol());
        }

    }

    /* Automatically searches through this scope and all its parents for the first occurrence of an identifier
     * then updates its value to the new value passed
     * If it can't find one, prints an error saying it doesn't exist.
     */
    void updateVar(DynamicVal val, String id) {
        if(rows.containsKey(id)) {
            SymTabRow row = rows.get(id);
            row.setValue(val);
        }
        else {
            if(null != parent) {
                parent.updateVar(val, id);
            }
            else {
                System.out.println("Runtime error: Identifier '" + id +  "' doesn't exist in the current scope");
            }
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        if(bblock != null) { // global scope doesn't have a block
            str.append("SymTab for BBLOCK at lin: " + bblock.sym.getToken().getLin() + " col: " + bblock.sym.getToken().getLinCol() + "\n");
        }
        str.append(String.format("%-10s | %-8s | %-4s | %-4s | %-40s\n", "ID", "TYPE", "LINE", "COL", "VALUE"));
        for(SymTabRow row : rows.values()) {
            str.append(row.toString());
            str.append('\n');
        }
        for(SymbolTable symTab : kids) {
            str.append(symTab.toString());
        }
        return str.toString();
    }

    SymbolTable getParent() {
        return parent;
    }
}
