
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
        if(tokID == 3) {
            return String.format("(Tok: %2d  lin= %2d,%-2d  str = \"%s\", int=%d)", tokID, lin, linCol, str, iVal);
        }
        else if (tokID == 4) {
            return String.format("(Tok: %2d  lin= %2d,%-2d  str = \"%s\", flo=%f)", tokID, lin, linCol, str, fVal);
        }
        else{
                return String.format("(Tok: %2d  lin= %2d,%-2d  str = \"%s\")", tokID, lin, linCol, str);
        }
    }
}