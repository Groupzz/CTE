/* CECS 444 Compiler Construction
 * Project 1: Lexer
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 */

import java.io.IOException;

public class Lexer {

	public static void main(String[] args) throws IOException {

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


}
