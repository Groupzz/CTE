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
    5. When the program is running input 
    Sample input: 
        prog main { print ("Sample input", "S= ", 27, "I= ", 9.19);}
    Sample output:
	Used rule: 1 : [0:true:-1, 2:false:-1, 28:false:-1, 4:false:-1, 10:true:-1] : {Terminal: 10}
	Top is same as front. Stack: [0:true:-1, 2:false:-1, 28:false:-1, 4:false:-1] : {Terminal: 10}
	Used rule: 5 : [0:true:-1, 2:false:-1, 28:false:-1] : {Terminal: 11}
	Used rule: 54 : [0:true:-1, 2:false:-1] : {Terminal: 11}   
	Used rule: 2 : [0:true:-1, 3:false:-1, 11:true:-1] : {Terminal: 11}
	Top is same as front. Stack: [0:true:-1, 3:false:-1] : {Terminal: 11}
	Used rule: 3 : [0:true:-1, 34:true:-1, 36:false:-1, 4:false:-1, 33:true:-1] : {Terminal: 33}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 4:false:-1] : {Terminal: 33}
	Used rule: 5 : [0:true:-1, 34:true:-1, 36:false:-1] : {Terminal: 23}
	Used rule: 65 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 61:false:-1] : {Terminal: 23}
	Used rule: 71 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 45:false:-1] : {Terminal: 23}
	Used rule: 87 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 63:false:-1, 23:true:-1] : {Terminal: 23}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 63:false:-1] : {Terminal: 23}
	Used rule: 134 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 64:false:-1, 37:true:-1] : {Terminal: 37}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 64:false:-1] : {Terminal: 37}
	Used rule: 135 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 19:false:-1] : {Terminal: 5}
	Used rule: 31 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 68:false:-1] : {Terminal: 5} 
	Used rule: 141 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 70:false:-1] : {Terminal: 5}
	Used rule: 144 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 72:false:-1] : {Terminal: 5}
	Used rule: 147 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 49:false:-1] : {Terminal: 5}
	Used rule: 97 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 50:false:-1] : {Terminal: 5}
	Used rule: 104 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 5:true:-1] : {Terminal: 5}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1] : {Terminal: 5}
	Used rule: 148 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1] : {Terminal: 6}
	Used rule: 145 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1] : {Terminal: 6}
	Used rule: 142 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1] : {Terminal: 6}
	Used rule: 32 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 19:false:-1, 6:true:-1] : {Terminal: 6}   
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 19:false:-1] : {Terminal: 6} 
	Used rule: 31 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 68:false:-1] : {Terminal: 5} 
	Used rule: 141 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 70:false:-1] : {Terminal: 5}
	Used rule: 144 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 72:false:-1] : {Terminal: 5}
	Used rule: 147 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 49:false:-1] : {Terminal: 5}
	Used rule: 97 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 50:false:-1] : {Terminal: 5}
	Used rule: 104 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 5:true:-1] : {Terminal: 5}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1] : {Terminal: 5}
	Used rule: 148 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1] : {Terminal: 6}
	Used rule: 145 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1] : {Terminal: 6}
	Used rule: 142 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1] : {Terminal: 6}
	Used rule: 32 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 19:false:-1, 6:true:-1] : {Terminal: 6}   
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 19:false:-1] : {Terminal: 6} 
	Used rule: 31 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 68:false:-1] : {Terminal: 3} 
	Used rule: 141 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 70:false:-1] : {Terminal: 3}
	Used rule: 144 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 72:false:-1] : {Terminal: 3}
	Used rule: 147 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 49:false:-1] : {Terminal: 3}
	Used rule: 97 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 50:false:-1] : {Terminal: 3}
	Used rule: 102 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 3:true:-1] : {Terminal: 3}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1] : {Terminal: 3}
	Used rule: 148 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1] : {Terminal: 6}
	Used rule: 145 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1] : {Terminal: 6}
	Used rule: 142 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1] : {Terminal: 6}
	Used rule: 32 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 19:false:-1, 6:true:-1] : {Terminal: 6}   
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 19:false:-1] : {Terminal: 6} 
	Used rule: 31 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 68:false:-1] : {Terminal: 5} 
	Used rule: 141 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 70:false:-1] : {Terminal: 5}
	Used rule: 144 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 72:false:-1] : {Terminal: 5}
	Used rule: 147 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 49:false:-1] : {Terminal: 5}
	Used rule: 97 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 50:false:-1] : {Terminal: 5}
	Used rule: 104 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 5:true:-1] : {Terminal: 5}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1] : {Terminal: 5}
	Used rule: 148 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1] : {Terminal: 6}
	Used rule: 145 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1] : {Terminal: 6}
	Used rule: 142 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1] : {Terminal: 6}
	Used rule: 32 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 19:false:-1, 6:true:-1] : {Terminal: 6}   
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 19:false:-1] : {Terminal: 6} 
	Used rule: 31 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 68:false:-1] : {Terminal: 4} 
	Used rule: 141 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 70:false:-1] : {Terminal: 4}
	Used rule: 144 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 72:false:-1] : {Terminal: 4}
	Used rule: 147 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 49:false:-1] : {Terminal: 4}
	Used rule: 97 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 50:false:-1] : {Terminal: 4}
	Used rule: 103 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1, 4:true:-1] : {Terminal: 4}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1, 71:false:-1] : {Terminal: 4}
	Used rule: 148 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1, 69:false:-1] : {Terminal: 38}
	Used rule: 145 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1, 67:false:-1] : {Terminal: 38}
	Used rule: 142 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1, 20:false:-1] : {Terminal: 38}
	Used rule: 33 : [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1, 38:true:-1] : {Terminal: 38}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1, 7:true:-1] : {Terminal: 38}
	Top is same as front. Stack: [0:true:-1, 34:true:-1, 36:false:-1] : {Terminal: 7}
	Used rule: 66 : [0:true:-1, 34:true:-1] : {Terminal: 34}   
	Top is same as front. Stack: [0:true:-1] : {Terminal: 34}  
	Top is same as front. Stack: [] : {Terminal: 0}
	Passed grammar check
	
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
              {Terminal: 37}(Tok: 37  lin=  1,19  str = "(") (  )
              64:135 (
                19:31 (
                  68:141 (
                    70:144 (
                      72:147 (
                        49:97 (
                          50:104 (
                            	{Terminal: 5}(Tok:  5  lin=  1,20  str = "Sample input") (  )  )  )
                        		71:148 (  )  )
                      	69:145 (  )  )
                    	67:142 (  )  )
                  	20:32 (
                    	{Terminal: 6}(Tok:  6  lin=  1,34  str = ",") (  )
                    	19:31 (
                      	68:141 (
                        	70:144 (
                          	72:147 (
                            	49:97 (
                              	50:104 (
                                	{Terminal: 5}(Tok:  5  lin=  1,36  str = "S= ") (  )  )  )
                            	71:148 (  )  )
                          	69:145 (  )  )
                        	67:142 (  )  )
                      	20:32 (
                        	{Terminal: 6}(Tok:  6  lin=  1,41  str = ",") (  )
                        	19:31 (
                          	68:141 (
                            	70:144 (
                              	72:147 (
                                	49:97 (
                                  	50:102 (
                                    	{Terminal: 3}(Tok:  3  lin=  1,43  str = "27", int=27) (  )  )  )
                                	71:148 (  )  )
                              	69:145 (  )  )
                            	67:142 (  )  )
                          	20:32 (
                            	{Terminal: 6}(Tok:  6  lin=  1,45  str = ",") (  )
                            	19:31 (
                              	68:141 (
                                	70:144 (
                                  	72:147 (
                                    	49:97 (
                                      	50:104 (
                                        	{Terminal: 5}(Tok:  5  lin=  1,47  str = "I= ") (  )  )  )
                                    	71:148 (  )  )
                                  	69:145 (  )  )
                                	67:142 (  )  )
                              	20:32 (
                                	{Terminal: 6}(Tok:  6  lin=  1,52  str = ",") (  )
                                	19:31 (
                                  	68:141 (
                                    	70:144 (
                                      	72:147 (
                                        	49:97 (
                                          	50:103 (
                                            	{Terminal: 4}(Tok:  4  lin=  1,54  str = "9.19", flo=9.190000) (  )  )  ) 
                                        	71:148 (  )  )     
                                      	69:145 (  )  )       
                                    	67:142 (  )  )
                                  	20:33 (  )  )  )  )  )  )  )  )  )  )
                	{Terminal: 38}(Tok: 38  lin=  1,58  str = ")") (  )  )  )  )  )
        	{Terminal: 7}(Tok:  7  lin=  1,59  str = ";") (  ) 
        36:66 (  )  )
      	{Terminal: 34}(Tok: 34  lin=  1,60  str = "}") (  )  )  )  )

	Abstract Syntax Tree:

	{Terminal: 10}(Tok: 10  lin=  1,1   str = "prog") (        
  	{Terminal: 11}(Tok: 11  lin=  1,6   str = "main") (      
    	{Terminal: 33}(Tok: 33  lin=  1,11  str = "{") (       
      	{Terminal: 7}(Tok:  7  lin=  1,59  str = ";") (      
        {Terminal: 23}(Tok: 23  lin=  1,13  str = "print") 
	(
          {Terminal: 37}(Tok: 37  lin=  1,19  str = "(") ( 

            {Terminal: 38}(Tok: 38  lin=  1,58  str = ")") 
	(
              {Terminal: 5}(Tok:  5  lin=  1,20  str = "Sample input") (
                {Terminal: 6}(Tok:  6  lin=  1,34  str = ",") ("S= ") (
                    {Terminal: 6}(Tok:  6  lin=  1,41  str = ",") (
                      {Terminal: 3}(Tok:  3  lin=  1,43  str = "27", int=27) (
                        {Terminal: 6}(Tok:  6  lin=  1,45  str = ",") (
                          {Terminal: 5}(Tok:  5  lin=  1,47  str = "I= ") (
                            {Terminal: 6}(Tok:  6  lin=  1,52  str = ",") (
                              {Terminal: 4}(Tok:  4  lin=  1,54  str = "9.19", flo=9.190000) (  )  )  )  )  )  )  )  )  )  )  )  )  )
      	{Terminal: 34}(Tok: 34  lin=  1,60  str = "}") (  )  )  )  )
    
Features: 
    Included:
        1. List all tokens found in the code source file that is piped into the program.
        2. Display token details such as token ID, line and column number, text of token itself, as well as float and integer literals.
        3. Distinguish between a negative number and minus operator by using spaces.
        4. Ignore comments listed in the code source file.
        5. Inform user of any syntax errors by outputting line and column details of error to standard input.
    Missing: N/A

Bugs: No bugs found.
