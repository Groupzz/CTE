# CTE
Class Number: CECS 444 - Section 01

Project Name: Project #2 - Parser

Team Name: CTE

Team Members: Aleks Dziewulska, Jessica Hilario, Jamil Khan, Joshua Lorenzen

Intro: 
The objective of this assignment is to write a parser for the A7 language lexicon. The parser implements the LL Parse based on an LL Parse table, token input and a prediction stack to construct a Parse Tree of a user's program. In case of a user error an error message is displayed.

Contents: 
README.txt, Parser.java, Lexer.java, Token.java, PNode.java, Rule.java, Symbol.java, SymbolTree.java

External Requirements: 
No special requirements necessary

Setup and Installation: 
To run the parser program all the java files need to be in the same directory. 
To compile the parser we can use Linux terminal or Windows Powershell by navigating to the directory and using the command: 
	javac *.java
To run the program we need to go up a directory which can be done using the command:
	cd ..
Once we are in the "parent" directory we can run the parser using the command below where <folderName> should be replaced by the exact name of the sub-directory that contains all of the compiled java files:
	java <folderName>.Lexer | java <folderName>.Parser

Sample invocation and results to see: 
    1. Open the Linux terminal or Windows Powershell.
    2. Navigate to the directory of the program.
    3. Once the directory is open, compile the code using the instructions above.
    4. Go up a directory and run the program using the instructions above.
    5. When the program is running enter input.
    6. To close the input stream send the EOF using Ctrl + d in Linux terminal or Ctrl + z in the Windows Powershell. 

    Sample input: 
        prog main { print ("Sample input", "S= ", 27, "I= ", 9.19);}

    Sample output:
	Parse Tree:
	1:1 (
	  {Terminal: 10}(Tok: 10  lin=  1,1   str = "prog") (  )
	  4:5 (  )
	  28:54 (  )
	  2:2 (
	    {Terminal: 11}(Tok: 11  lin=  1,6   str = "main") (  )
	    3:3 (
	      {Terminal: 33}(Tok: 33  lin=  1,11  str = "{") (  )
	      4:5 (  )
	      36:65 (
	        61:71 (
	          45:87 (
	            {Terminal: 23}(Tok: 23  lin=  1,13  str = "print") (  )
	            63:134 (
	              {Terminal: 37}(Tok: 37  lin=  1,18  str = "(") (  )
	              64:135 (
	                19:31 (
	                  68:141 (
	                    70:144 (
	                      72:147 (
	                        49:97 (
	                          50:104 (
	                            {Terminal: 5}(Tok:  5  lin=  1,20  str = "ASCII:") (  )  )  )
	                        71:148 (  )  )
	                      69:145 (  )  )
	                    67:142 (  )  )
	                  20:32 (
	                    {Terminal: 6}(Tok:  6  lin=  1,28  str = ",") (  )
	                    19:31 (
                      68:141 (
	                        70:144 (
	                          72:147 (
	                            49:97 (
	                              50:104 (
	                                {Terminal: 5}(Tok:  5  lin=  1,30  str = " A= ") (  )  )  )
	                            71:148 (  )  )
	                          69:145 (  )  )
	                        67:142 (  )  )
	                      20:32 (
	                        {Terminal: 6}(Tok:  6  lin=  1,36  str = ",") (  )
	                        19:31 (
	                          68:141 (
	                            70:144 (
	                              72:147 (
	                                49:97 (
	                                  50:102 (
	                                    {Terminal: 3}(Tok:  3  lin=  1,38  str = "65", int=65) (  )  )  )
	                                71:148 (  )  )
	                              69:145 (  )  )
	                            67:142 (  )  )
	                          20:32 (
	                            {Terminal: 6}(Tok:  6  lin=  1,40  str = ",") (  )
	                            19:31 (
	                              68:141 (
	                                70:144 (
	                                  72:147 (
	                                    49:97 (
	                                      50:104 (
	                                        {Terminal: 5}(Tok:  5  lin=  1,42  str = " Z= ") (  )  )  )
	                                    71:148 (  )  )
	                                  69:145 (  )  )
	                                67:142 (  )  )
	                              20:32 (
	                                {Terminal: 6}(Tok:  6  lin=  1,48  str = ",") (  )
	                                19:31 (
	                                  68:141 (
	                                    70:144 (
	                                      72:147 (
	                                        49:97 (
	                                          50:102 (
	                                            {Terminal: 3}(Tok:  3  lin=  1,50  str = "90", int=90) (  )  )  )
	                                        71:148 (  )  )
	                                      69:145 (  )  )
	                                    67:142 (  )  )
	                                  20:33 (  )  )  )  )  )  )  )  )  )  )
	                {Terminal: 38}(Tok: 38  lin=  1,53  str = ")") (  )  )  )  )  )
	        {Terminal: 7}(Tok:  7  lin=  1,54  str = ";") (  )
	        36:66 (  )  )
	      {Terminal: 34}(Tok: 34  lin=  1,56  str = "}") (  )  )  )  )

	Abstract Syntax Tree:

	{Terminal: 10}(Tok: 10  lin=  1,1   str = "prog") (
  	  {Terminal: 11}(Tok: 11  lin=  1,6   str = "main") (
    	    {Terminal: 33}(Tok: 33  lin=  1,11  str = "{") (
      	      {Terminal: 7}(Tok:  7  lin=  1,54  str = ";") (
        	{Terminal: 23}(Tok: 23  lin=  1,13  str = "print") (
          	  {Terminal: 37}(Tok: 37  lin=  1,18  str = "(") (
            	    {Terminal: 38}(Tok: 38  lin=  1,53  str = ")") (
              	      {Terminal: 5}(Tok:  5  lin=  1,20  str = "ASCII:") (
                	{Terminal: 6}(Tok:  6  lin=  1,28  str = ",") (
                  	  {Terminal: 5}(Tok:  5  lin=  1,30  str = " A= ") (
                    	    {Terminal: 6}(Tok:  6  lin=  1,36  str = ",") (
                      	      {Terminal: 3}(Tok:  3  lin=  1,38  str = "65", int=65) (
                        	{Terminal: 6}(Tok:  6  lin=  1,40  str = ",") (
                          	  {Terminal: 5}(Tok:  5  lin=  1,42  str = " Z= ") (
                            	    {Terminal: 6}(Tok:  6  lin=  1,48  str = ",") (
                              	      {Terminal: 3}(Tok:  3  lin=  1,50  str = "90", int=90) (  )  )  )  )  )  )  )  )  )  )  )  )  )
	{Terminal: 34}(Tok: 34  lin=  1,56  str = "}") (  )  )  )  )
    
Features: 
    Included:
        1. LL Parse machine implemented.
        2. Prediction stack to predict input and generate a Parse Tree of the A7 program.
        3. Error messages in case of user error.
        4. Parse Tree to Abstract Syntax Tree conversion.
    Missing: N/A

Bugs: 
    Not working:
	1. Array parsing.
	2. Class group parsing.
	3. Interface parsing.	
	4. Method parsing.
	5. Object oriented parsing.
