package main;

import java.util.ArrayList;

public class SymbolTable {

    static SymbolTable root = new SymbolTable(null, null); // scope tree root

    SymbolTable parent = null; // scope node parent
    ArrayList<SymbolTable> kids = new ArrayList<SymbolTable>(); // scope node kids
    PNode bblock = null;

    private SymbolTable(SymbolTable par, PNode bblock) {
        parent = par;
    }

    public SymbolTable addNewScope(PNode bblock) {
        kids.add(new SymbolTable(this, bblock));
        return kids.get(kids.size() - 1);
    }



}
