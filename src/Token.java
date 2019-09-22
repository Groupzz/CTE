
public class Token{
    private int tokID;
    private int lin;
    private int linCol;
    private String str;
    private int iVal;
    private float fVal;

    public Token(int tokID, int lin, int linCol, String str) {
        this.tokID = tokID;
        this.lin = lin;
        this.linCol = linCol;
        this.str = str;

        // If token is an integer
        if (tokID == 3) {
            this.iVal = Integer.parseInt(str);
        }
        // If token is a float
        else if (tokID == 4) {
            this.fVal = Float.parseFloat(str);
        }
    }

    public String toString() {
        return String.format("(Tok: %d, lin= %d,%d str = \"%s\")", tokID, lin, linCol, str);
    }

}