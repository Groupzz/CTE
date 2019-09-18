/* CECS 444 Compiler Construction
 * Project 1: Lexer
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 */

import java.io.IOException;
import java.util.HashMap;

public class Lexer {

	static HashMap<String, Integer> keywords;
	public static void main(String[] args) throws IOException {

		keywordInitialize();
		int ci;
		int line = 1;
		int pos = 0;
		// Read one character at a time from stdin until EOF
		while ((ci = System.in.read()) != -1) {
		    char c = (char) ci;
			System.out.println(c);

			// Increment position counter for each character
			pos++;
			// If we hit a newline increment line counter and reset position counter
			if (c == '\n') {
			    pos = 0;
			    line++;
            }
		}
	}

	static void keywordInitialize()
	{
		keywords = new HashMap();
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
}

public class Token{
	int tok;
	int lin;
	int linCol;
	String str;
	int var;

}