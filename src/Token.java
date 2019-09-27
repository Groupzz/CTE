/* CECS 444 Compiler Construction
 * Project 1: Lexer
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu, 
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu 
 * Description: Token.java is a support file to the Lexer.java that creates a Token object to refer to in the Lexer.java
 */

public class Token{
    private int tokID;
    private int lin;
    private int linCol;
    private String str;
    private int iVal;
    private float fVal;

    // Constructor to create a token object using the token id, the line number where the token is found,
    // the column number of the start of the token and a string of actual token as it appears in code
    public Token(int tokID, int lin, int linCol, String str) {
        this.tokID = tokID; // Assign an appropriate token ID
        this.lin = lin; // Keep track of line number the token is located on in the code
        this.linCol = linCol; // Keep track of column number the token starts at in the code
        this.str = str; // A string value of the whole token as read from stdin

        // Make sure to get the column number of the beginning of token
        if(str.length() > 1)
        {
            this.linCol -= str.length();
        }
        // If the token is a string subtract one from the column number to accommodate "
        if(this.tokID == 5)
        {
            this.linCol--;
        }

        // If token is an integer parse it to an integer value
        if (tokID == 3) {
            this.iVal = Integer.parseInt(str);
        }
        // If token is a float parse it to a float value
        else if (tokID == 4) {
            this.fVal = Float.parseFloat(str);
        }
    }

    // Method to format the final output of the program 
    public String toString() {
        // If the token is an integer add the actual parsed value
        if(tokID == 3) {
            return String.format("(Tok: %2d  lin= %2d,%-2d  str = \"%s\", int=%d)", tokID, lin, linCol, str, iVal);
        }
        // If the token is a float add the parsed float value
        else if (tokID == 4) {
            return String.format("(Tok: %2d  lin= %2d,%-2d  str = \"%s\", flo=%f)", tokID, lin, linCol, str, fVal);
        }
        // If a token is neither a float nor an integer do not include a value
        else{
                return String.format("(Tok: %2d  lin= %2d,%-2d  str = \"%s\")", tokID, lin, linCol, str);
        }
    }
}