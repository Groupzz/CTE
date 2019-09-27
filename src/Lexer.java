/* CECS 444 Compiler Construction
 * Project 1: Lexer
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 * Authors' emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu, 
 *                                 jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu 
 * Description: Lexer.java is a driver file for the Lexer of the A5 language compiler
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.exit;

public class Lexer {

	static HashMap<String, Integer> keywords;
	private static HashMap<Character, Integer> tokenLookup;
	private static ArrayList<Token> tokens = new ArrayList<>();

	private static int state = 1; // Represents our current state in the DFA
	private static StringBuilder tokenText = new StringBuilder();
	private static int line = 1; // Tracks line and column number for each token
	private static int col = 1;

	public static void main(String[] args) throws IOException {

		//Calling methods to initialize keywords and tokens
		//that should be recognized by the lexer
		keywordInitialize();
		tokenInitialize();

		//Initialize int variable to store next character from system input
		int ci;

		// Read one character at a time from stdin until EOF
		while ((ci = System.in.read()) != -1) { // -1 represents EOF
		    char c = (char) ci; // read() returns an integer so we cast it to a character

			nextState(c);

			// Increment position counter for each character
			col++;
			// If we hit a newline increment line counter and reset position counter
			if (c == '\n') {
			    col = 1;
			    line++;
            }
		}

		// Send a space after the file has been read to make sure tokens in the middle of being read
		// are accepted and added to the list of tokens
		nextState(' ');

		// Add EOF token
		tokens.add(new Token(0, line, col, ""));

		// Output all the tokens
		for(Token tok : tokens) {
			System.out.println(tok);
		}
	}

	//Method to check the state by peeking ahead to next character and advancing if allowed
	private static void nextState(char c) {
	    if (c == '\t' || c == '\r') {
	        return; // ignore indentation
        }
		if (c == '\n') {
			if (state == 3) {
				resetState(); // Throw the comment away
			}
			return; // ignore newline
		}
		if (c == ' ') {
			// Ignore spaces when we are in the start state
			if (state == 1) {
				return; // skip this character
			}
		}
		switch(state) {
			case 1: // Start state
				if(Character.isAlphabetic(c) || c == '_') {
					tokenText.append(c);
					state = 13;
				}
				else if(Character.isDigit(c)) {
					tokenText.append(c);
					state = 15;
				}
				else {
				    // If tokenLookup finds a result, this character exists in the language only as a single token
                    // As a result we can immediately accept it and move back to start state
					if (tokenLookup.get(c) != null) {
					    tokenText.append(c);
						acceptToken(tokenLookup.get(c));
						break;
					}
					else {
					    // If we have reached this condition, the character is non-numeral/alphabetic
                        // and is the beginning of one or more tokens
						switch(c) {
							case '/':
								tokenText.append(c);
								state = 2;
								break;
							case '=':
								tokenText.append(c);
								state = 5;
								break;
							case '+':
								tokenText.append(c);
								state = 14;
								break;
							case '-':
								tokenText.append(c);
								state = 19;
								break;
                            case '"':
                                // For string literals don't append the quote to tokenText
                                state = 21;
								break;
							case '>':
								tokenText.append(c);
								state = 7;
								break;
							case '<':
								tokenText.append(c);
								state = 10;
								break;
							case '!':
								tokenText.append(c);
								state = 24;
								break;
							default:
								tokenError();
								return;
						}
					}
				}
				break;
            case 2: // State after seeing a '/': Division or line comments
				if(c == '/'){
					state = 3;
					tokenText.append(c);
				}
				else {
					acceptToken(48);
					nextState(c);
				}
				break;
			case 5: // State after seeing a '='
				if(c == '=') {
					tokenText.append(c);
                    acceptToken(52);
				}
				else {
					acceptToken(45);
					nextState(c);
				}
				break;
			case 7: //State after seeing a '>': Greater than or equal to or only greater than
				if(c == '>')
				{
					tokenText.append(c);
					acceptToken(57);
				}
				else if (c == '=')
				{
					tokenText.append(c);
					acceptToken(55);
				}
				else
				{
					acceptToken(32);
					nextState(c);
				}
				break;
			case 10: //State after seeing '<': Less than or equal to or only less than
				if(c == '<')
				{
					tokenText.append(c);
					acceptToken(56);
				}
				else if(c == '=')
				{
					tokenText.append(c);
					acceptToken(54);
				}
				else
				{
					acceptToken(31);
					nextState(c);
				}
				break;
			case 13: // Identifier state
				if(Character.isDigit(c) || Character.isAlphabetic(c) || c == '_') {
					tokenText.append(c);
				}
				else {
					acceptIdentifier();
					nextState(c);
				}
				break;
			case 14: // State after seeing '+'
				if(Character.isDigit(c)) {
					tokenText.append(c);
					state = 15;
				}
				else {
					acceptToken(47);
					nextState(c);
				}
				break;
			case 15: // Integer accepting state / Possible float state
				if(Character.isDigit(c)) {
					tokenText.append(c);
				}
				else if(c == '.') {
					tokenText.append(c);
					state = 16;
				}
				else {
					acceptToken(3);
					nextState(c);
				}
				break;
			case 16: // State after seeing '.' following integer
				if(Character.isDigit(c)) {
					tokenText.append(c);
					state = 17;
				}
				else {
					tokenError();
				}
				break;
			case 17: // Float accepting state
				if(Character.isDigit(c)) {
					tokenText.append(c);
				}
				else {
					acceptToken(4);
					nextState(c);
				}
				break;
			case 19: // State after seeing a '-'
				if(Character.isDigit(c)) {
					// Integer/Float preceded by minus
					tokenText.append(c);
					state = 15;
				}
				else if(c == '>') {
					// -> Arrow operator
					tokenText.append(c);
					acceptToken(51);
				}
				else {
					// Minus operator
					acceptToken(46);
					nextState(c);
				}
				break;
            case 21: // String literal state
                if(c == '"') {
                    // Don't append quote to tokenText
                    acceptToken(5);
                }
                else {
                    tokenText.append(c);
                }
				break;
			case 24: //State after seeing "="
				if(c == '=')
				{
					tokenText.append(c);
					acceptToken(53);
				}
				else
				{
					tokenError();
				}
				break;
		}
	}

	// Method for accepting identifiers and keywords specifically
    // checks if the identifier is a keyword and accepts the appropriate token
	private static void acceptIdentifier() {
		Integer keywordID = keywords.get(tokenText.toString());
		if (keywordID != null) {
			// its a keyword, use the right tokenID
			acceptToken(keywordID);
		}
		else {
			// its an identifier
			acceptToken(2);
		}
	}

	// Adds a new token to the output and resets the state
	private static void acceptToken(int ID) {
		Token newToken = new Token(ID, line, col, tokenText.toString());
		tokens.add(newToken);
		resetState();
	}

	//Method to reset start state of lexer.
	private static void resetState() {
		tokenText = new StringBuilder();
		state = 1;
	}

	//Method to throw syntax errors for any tokens that are syntactically incorrect.
	private static void tokenError() {
		System.out.println("Token syntax error: " + "lin: " + line + " col: " + col);
		exit(0);
	}

	//Method to place keywords that should be recognized by the lexer in a new HashMap.
	static void keywordInitialize()
	{
		keywords = new HashMap<>();
		keywords.put("prog", 10);
		keywords.put("main", 11);
		keywords.put("fcn", 12);
		keywords.put("class", 13);
		keywords.put("float", 15);
		keywords.put("int", 16);
		keywords.put("string", 17);
		keywords.put("if", 18);
		keywords.put("elseif", 19);
		keywords.put("else", 20);
		keywords.put("while", 21);
		keywords.put("input", 22);
		keywords.put("print", 23);
		keywords.put("new", 24);
		keywords.put("return", 25);
		keywords.put("var", 26);
	}

	//Method to place tokens that should be recognized by the lexer in a new HashMap.
	private static void tokenInitialize() {
		tokenLookup = new HashMap<>();
		tokenLookup.put(',', 6);
		tokenLookup.put(';', 7);
        	tokenLookup.put('{', 33);
        	tokenLookup.put('}', 34);
		tokenLookup.put('[', 35);
		tokenLookup.put(']', 36);
        	tokenLookup.put('(', 37);
        	tokenLookup.put(')', 38);
        	tokenLookup.put('*', 41);
        	tokenLookup.put('^', 42);
        	tokenLookup.put(':', 43);
        	tokenLookup.put('.', 44);
        	tokenLookup.put('&', 49);
	}
}
