/* CECS 444 Compiler Construction
 * Project 1: Lexer
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 */

import com.sun.org.apache.xpath.internal.compiler.Keywords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.exit;

public class Lexer {

	static HashMap<String, Integer> keywords;
	private static HashMap<Character, Integer> tokenLookup;
	private static ArrayList<Token> tokens = new ArrayList<>();

	private static int state = 1;
	private static StringBuilder tokenText = new StringBuilder();
	private static int line = 1;
	private static int col = 1;

	public static void main(String[] args) throws IOException {

		keywordInitialize();
		tokenInitialize();

		int ci;

		// Read one character at a time from stdin until EOF
		while ((ci = System.in.read()) != -1) {
		    char c = (char) ci;
			//System.out.println(c);

			nextState(c);

			// Increment position counter for each character
			col++;
			// If we hit a newline increment line counter and reset position counter
			if (c == '\n') {
			    col = 1;
			    line++;
            }
		}

		for(Token tok : tokens) {
			System.out.println(tok);
		}
	}

	private static void nextState(char c) {
		if (c == '\n') {
			if (state == 3) {
				resetState(); // Throw the comment away
			}
			return; // ignore newline
		}
		if (c == ' ') {
			// if we are not reading a string or an identifier
			//if (state != 21 && state != 13) {
			if (state == 1) {
				return; // skip this character
			}
		}
		switch(state) {
			case 1:
				if(Character.isAlphabetic(c) || c == '_') {
					tokenText.append(c);
					state = 13;
				}
				else {
					if (tokenLookup.get(c) != null) {
						acceptToken(tokenLookup.get(c), "" + c);
						resetState();
						break;
					}
					else {
						switch(c) {
							case '/':
								tokenText.append(c);
								state = 2;
								break;
							case '=':
								tokenText.append(c);
								state = 5;
								break;
							default:
								tokenError();
								return;
						}
					}
				}
				break;
			case 2: // Division / line comments
				if(c == '/'){
					state = 3;
					tokenText.append(c);
				}
				else {
					acceptToken(48, tokenText.toString());
					resetState();
					nextState(c);
				}
				break;
			case 5:
				if(c == '=') {
					tokenText.append(c);
					state = 6;
				}
				else {
					acceptToken(45, tokenText.toString());
					resetState();
					nextState(c);
				}
				break;
			case 13:
				if(Character.isDigit(c) || Character.isAlphabetic(c) || c == '_') {
					tokenText.append(c);
				}
				else {
					acceptIdentifier(tokenText.toString());
					resetState();
					nextState(c);
				}
				break;

		}
	}

	private static void acceptIdentifier(String text) {
		Integer keywordID = keywords.get(text);
		if (keywordID != null) {
			// its a keyword, use the right tokenID
			acceptToken(keywordID, text);
		}
		else {
			// its an identifier
			acceptToken(2, text);
		}
	}

	private static void acceptToken(int ID, String text) {
		Token newToken = new Token(ID, line, col, text);
		tokens.add(newToken);
	}

	private static void resetState() {
		tokenText = new StringBuilder();
		state = 1;
	}

	private static void tokenError() {
		System.out.println("Token syntax error: " + "lin: " + line + " col: " + col);
		exit(0);
	}

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

	private static void tokenInitialize() {
		tokenLookup = new HashMap<>();
		tokenLookup.put(',', 6);
		tokenLookup.put(';', 7);
	}
}
