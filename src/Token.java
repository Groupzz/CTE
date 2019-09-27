/* CECS 444 Compiler Construction
 * Project 1: Lexer
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu, jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu 
 * Description: Token.java is a support file to the Lexer.java that creates a Token object to refer to in the Lexer.java
 */

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

        if(str.length() > 1)
        {
            this.linCol -= str.length();
        }
        if(this.tokID == 5)
        {
            this.linCol--;
        }

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