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

    SymbolTable addNewScope(PNode bblock) {
        kids.add(new SymbolTable(this, bblock));
        return kids.get(kids.size() - 1);
    }

    void declareVar(String type, Token id, PNode declNode, boolean isPtr) {

        if(!rows.containsKey(id.getStr())) {
            SymTabRow newRow = new SymTabRow(id.getStr(), type, id.getLin(), id.getLinCol(), null, declNode, isPtr);
            rows.put(id.getStr(), newRow);
        }
        else {
            throw new RuntimeException("Error: Identifier '" + id.getStr() +  "' already exists in symbol table for this scope: lin: " + id.getLin() + " col: " + id.getLinCol());
        }

    }

    void declareArr(String type, Token id, PNode declNode, DynamicVal size) {
        if(!rows.containsKey(id.getStr())) {
            SymTabRow newRow = new SymTabRow(id.getStr(), type, id.getLin(), id.getLinCol(), declNode, size.intVal);
            rows.put(id.getStr(), newRow);
        }
        else {
            throw new RuntimeException("Error: Identifier '" + id.getStr() +  "' already exists in symbol table for this scope: lin: " + id.getLin() + " col: " + id.getLinCol());
        }
    }

    /* Automatically searches through this scope and all its parents for the first occurrence of an identifier
     * then updates the AST node to link to the correct SymTabRow for easy access
     * If it can't find one, prints an error saying it doesn't exist.
     */
    void linkID(PNode idNode) {
        String id = idNode.sym.getToken().getStr();
        if(rows.containsKey(id)) {
            idNode.symTabLink = rows.get(id);
        }
        else {
            if(null != parent) {
                parent.linkID(idNode);
            }
            else {
                throw new RuntimeException("Error: Identifier '" + id +  "' doesn't exist in the current scope. Lin: "
                        + idNode.sym.getToken().getLin() + " Col: " + idNode.sym.getToken().getLinCol());
            }
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        if(bblock != null) { // global scope doesn't have a block
            str.append("\nSymTab for BBLOCK at lin: " + bblock.sym.getToken().getLin() + " col: " + bblock.sym.getToken().getLinCol() + "\n");
        }
        str.append(String.format("%-10s | %-9s | %-8s | %-4s | %-4s | %-40s\n", "ID", "TYPE", "ADDR", "LINE", "COL", "VALUE"));
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
