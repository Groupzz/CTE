/* CECS 444 Compiler Construction
 * Project 1: Lexer
 * Authors: Aleks Dziewulska, Jamil Khan, Jessica Hilario, Josh Lorenzen
 */

import java.io.IOException;

public class Lexer {

	public static void main(String[] args) throws IOException {

		int c;
		// Read one character at a time from stdin until EOF
		while ((c = System.in.read()) != -1) {
			System.out.print((char) c);
		}
	}


}
