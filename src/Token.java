/* CECS 444 Compiler Construction
 * Project 1: Lexer and Project 2: Parser
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu, 
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu 
 * Description: Token.java is a support file to the Lexer.java that creates a Token object to refer to in the Lexer.java
 * The same token object is used to store token information in Parser.java
 */

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token{

    // Initialize regex pattern for parsing a string as a token
    private static Pattern tokPattern = Pattern.compile("\\(Tok: +([0-9]+) +lin= *([0-9]+),([0-9]+) *str = \"(.*)\"(, (int|flo)=([+\\-]?[0-9.]+))?\\)");

    private int tokID;
    private int lin;
    private int linCol;
    private String str;
    private int iVal;
    private float fVal;

    // Simple constructor that the Lexer uses. Assumes lines need to be adjusted
    public Token(int tokID, int lin, int linCol, String str) {
        this(tokID, lin, linCol, str, true);
    }

    // Constructor to create a token object using the token id, the line number where the token is found,
    // the column number of the start of the token and a string of actual token as it appears in code
    // Has optional boolean value to determine whether we need to adjust the line number for the lexer
    public Token(int tokID, int lin, int linCol, String str, boolean adjustLines) {
        this.tokID = tokID; // Assign an appropriate token ID
        this.lin = lin; // Keep track of line number the token is located on in the code
        this.linCol = linCol; // Keep track of column number the token starts at in the code
        this.str = str; // A string value of the whole token as read from stdin

        // If we are the lexer, we want to adjust the line numbers (Backwards compatibility)
        if(adjustLines) {
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

    // Builder that parses a token's output string and converts it into a token object
    // \(Tok: +([0-9]+) +lin= *([0-9]+),([0-9]+) *str = "(.*)"(, (int|flo)=([+\-]?[0-9.]+))?\)
    public static Token TokenBuilder(String tokString) {
        Matcher m = tokPattern.matcher(tokString);
        if(!m.matches()) {
            throw new InputMismatchException("Token input formatted improperly: \"" + tokString + "\"");
        }
        return new Token(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), m.group(4), false);
    }

    public int getID() {
        return tokID;
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

    // Constants for Token and Terminal IDs
    public static final int ID = 2;
    public static final int INT = 3;
    public static final int FLOAT = 4;
    public static final int STRING = 5;
    public static final int COMMA = 6;
    public static final int SEMI = 7;

    // Keywords
    public static final int KPROG = 10;
    public static final int KMAIN = 11;
    public static final int KFCN = 12;
    public static final int KCLASS = 13;
    public static final int KFLOAT = 15;
    public static final int KINT = 16;
    public static final int KSTRING = 17;
    public static final int KIF = 18;
    public static final int KELSEIF = 19;
    public static final int KELSE = 20;
    public static final int KWHILE = 21;
    public static final int KINPUT = 22;
    public static final int KPRINT = 23;
    public static final int KNEW = 24;
    public static final int KRETURN = 25;
    public static final int KVAR = 26;

    // Paired delimeters
    public static final int ANGLE1 = 31;
    public static final int ANGLE2 = 32;
    public static final int BRACE1 = 33;
    public static final int BRACE2 = 34;
    public static final int BRACKET1 = 35;
    public static final int BRACKET2 = 36;
    public static final int PARENS1 = 37;
    public static final int PARENS2 = 38;

    // Other punctuation tokens
    public static final int ASTER = 41;
    public static final int CARET = 42;
    public static final int COLON = 43;
    public static final int DOT = 44;
    public static final int EQUAL = 45;
    public static final int MINUS = 46;
    public static final int PLUS = 47;
    public static final int SLASH =  48;
    public static final int AMPERSAND = 49;

    // Multi-char operators
    public static final int OPARROW = 51;
    public static final int OPEQ = 52;
    public static final int OPNE = 53;
    public static final int OPLE = 54;
    public static final int OPGE = 55;
    public static final int OPSHL = 56;
    public static final int OPSHR = 57;

    // Misc
    public static final int EOF = 0;
}