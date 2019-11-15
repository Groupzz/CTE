package main;

public class SymbolTree {

    private PNode root;
    private PNode curNode;

    public SymbolTree(PNode root) {
        this.root = root;
    }

    public String toString() {
        return root.toString(0);
    }

    public PNode getRoot() {
        return root;
    }
}

