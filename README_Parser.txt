# CTE
Class Number: CECS 444 - Section 01

Project Name: Project #2 - Parser

Team Name: CTE

Team Members: Aleks Dziewulska, Jessica Hilario, Jamil Khan, Joshua Lorenzen
Emails (respectively): aleksandra.dziewulska@student.csulb.edu, jamil.khan@student.csulb.edu,
                       jessica.hilario@student.csulb.edu, joshua.lorenzen@student.csulb.edu
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

     ( Node #0 {Non-terminal: 1} Rule: 1
       ( Node #5 {Terminal: 10} (Tok: 10  lin=  1,1   str = "prog") )
       ( Node #4 {Non-terminal: 4} Rule: 5 )
       ( Node #3 {Non-terminal: 28} Rule: 54 )
       ( Node #2 {Non-terminal: 2} Rule: 2
         ( Node #7 {Terminal: 11} (Tok: 11  lin=  1,6   str = "main") )
         ( Node #6 {Non-terminal: 3} Rule: 3
           ( Node #11 {Terminal: 33} (Tok: 33  lin=  1,11  str = "{") )
           ( Node #10 {Non-terminal: 4} Rule: 5 )
           ( Node #9 {Non-terminal: 36} Rule: 65
             ( Node #14 {Non-terminal: 61} Rule: 71
               ( Node #15 {Non-terminal: 45} Rule: 87
                 ( Node #17 {Terminal: 23} (Tok: 23  lin=  1,13  str = "print") )
                 ( Node #16 {Non-terminal: 63} Rule: 134
                   ( Node #19 {Terminal: 37} (Tok: 37  lin=  1,19  str = "(") )
                   ( Node #18 {Non-terminal: 64} Rule: 135
                     ( Node #21 {Non-terminal: 19} Rule: 31
                       ( Node #23 {Non-terminal: 68} Rule: 141
                         ( Node #25 {Non-terminal: 70} Rule: 144
                           ( Node #27 {Non-terminal: 72} Rule: 147
                             ( Node #29 {Non-terminal: 49} Rule: 97
                               ( Node #30 {Non-terminal: 50} Rule: 104
                                 ( Node #31 {Terminal: 5} (Tok:  5  lin=  1,20  str = "Sample input") )  )  )
                             ( Node #28 {Non-terminal: 71} Rule: 148 )  )
                           ( Node #26 {Non-terminal: 69} Rule: 145 )  )
                         ( Node #24 {Non-terminal: 67} Rule: 142 )  )
                       ( Node #22 {Non-terminal: 20} Rule: 32
                         ( Node #33 {Terminal: 6} (Tok:  6  lin=  1,34  str = ",") )
                         ( Node #32 {Non-terminal: 19} Rule: 31
                           ( Node #35 {Non-terminal: 68} Rule: 141
                             ( Node #37 {Non-terminal: 70} Rule: 144
                               ( Node #39 {Non-terminal: 72} Rule: 147
                                 ( Node #41 {Non-terminal: 49} Rule: 97
                                   ( Node #42 {Non-terminal: 50} Rule: 104
                                     ( Node #43 {Terminal: 5} (Tok:  5  lin=  1,36  str = "S= ") )  )  )
                                 ( Node #40 {Non-terminal: 71} Rule: 148 )  )
                               ( Node #38 {Non-terminal: 69} Rule: 145 )  )
                             ( Node #36 {Non-terminal: 67} Rule: 142 )  )
                           ( Node #34 {Non-terminal: 20} Rule: 32
                             ( Node #45 {Terminal: 6} (Tok:  6  lin=  1,41  str = ",") )
                             ( Node #44 {Non-terminal: 19} Rule: 31
                               ( Node #47 {Non-terminal: 68} Rule: 141
                                 ( Node #49 {Non-terminal: 70} Rule: 144
                                   ( Node #51 {Non-terminal: 72} Rule: 147
                                     ( Node #53 {Non-terminal: 49} Rule: 97
                                       ( Node #54 {Non-terminal: 50} Rule: 102
                                         ( Node #55 {Terminal: 3} (Tok:  3  lin=  1,43  str = "27", int=27) )  )  )
                                     ( Node #52 {Non-terminal: 71} Rule: 148 )  )
                                   ( Node #50 {Non-terminal: 69} Rule: 145 )  )
                                 ( Node #48 {Non-terminal: 67} Rule: 142 )  )
                               ( Node #46 {Non-terminal: 20} Rule: 32
                                 ( Node #57 {Terminal: 6} (Tok:  6  lin=  1,45  str = ",") )
                                 ( Node #56 {Non-terminal: 19} Rule: 31
                                   ( Node #59 {Non-terminal: 68} Rule: 141
                                     ( Node #61 {Non-terminal: 70} Rule: 144
                                       ( Node #63 {Non-terminal: 72} Rule: 147
                                         ( Node #65 {Non-terminal: 49} Rule: 97
                                           ( Node #66 {Non-terminal: 50} Rule: 104
                                             ( Node #67 {Terminal: 5} (Tok:  5  lin=  1,47  str = "I= ") )  )  )
                                         ( Node #64 {Non-terminal: 71} Rule: 148 )  )
                                       ( Node #62 {Non-terminal: 69} Rule: 145 )  )
                                     ( Node #60 {Non-terminal: 67} Rule: 142 )  )
                                   ( Node #58 {Non-terminal: 20} Rule: 32
                                     ( Node #69 {Terminal: 6} (Tok:  6  lin=  1,52  str = ",") )
                                     ( Node #68 {Non-terminal: 19} Rule: 31
                                       ( Node #71 {Non-terminal: 68} Rule: 141
                                         ( Node #73 {Non-terminal: 70} Rule: 144
                                           ( Node #75 {Non-terminal: 72} Rule: 147
                                             ( Node #77 {Non-terminal: 49} Rule: 97
                                               ( Node #78 {Non-terminal: 50} Rule: 103
                                                 ( Node #79 {Terminal: 4} (Tok:  4  lin=  1,54  str = "9.19", flo=9.190000) )  )  )
                                             ( Node #76 {Non-terminal: 71} Rule: 148 )  )
                                           ( Node #74 {Non-terminal: 69} Rule: 145 )  )
                                         ( Node #72 {Non-terminal: 67} Rule: 142 )  )
                                       ( Node #70 {Non-terminal: 20} Rule: 33 )  )  )  )  )  )  )  )  )  )
                     ( Node #20 {Terminal: 38} (Tok: 38  lin=  1,58  str = ")") )  )  )  )  )
             ( Node #13 {Terminal: 7} (Tok:  7  lin=  1,59  str = ";") )
             ( Node #12 {Non-terminal: 36} Rule: 66 )  )
           ( Node #8 {Terminal: 34} (Tok: 34  lin=  1,60  str = "}") )  )  )  )

    Abstract Syntax Tree:

     ( Node #0 {Terminal: 10} (Tok: 10  lin=  1,1   str = "prog")
       ( Node #2 {Terminal: 11} (Tok: 11  lin=  1,6   str = "main")
         ( Node #6 {Terminal: 33} (Tok: 33  lin=  1,11  str = "{")
           ( Node #9 {Terminal: 7} (Tok:  7  lin=  1,59  str = ";")
             ( Node #14 {Terminal: 23} (Tok: 23  lin=  1,13  str = "print")
               ( Node #16 {Terminal: 37} (Tok: 37  lin=  1,19  str = "(")
                 ( Node #18 {Terminal: 38} (Tok: 38  lin=  1,58  str = ")")
                   ( Node #21 {Terminal: 5} (Tok:  5  lin=  1,20  str = "Sample input")
                     ( Node #22 {Terminal: 6} (Tok:  6  lin=  1,34  str = ",")
                       ( Node #32 {Terminal: 5} (Tok:  5  lin=  1,36  str = "S= ")
                         ( Node #34 {Terminal: 6} (Tok:  6  lin=  1,41  str = ",")
                           ( Node #44 {Terminal: 3} (Tok:  3  lin=  1,43  str = "27", int=27)
                             ( Node #46 {Terminal: 6} (Tok:  6  lin=  1,45  str = ",")
                               ( Node #56 {Terminal: 5} (Tok:  5  lin=  1,47  str = "I= ")
                                 ( Node #58 {Terminal: 6} (Tok:  6  lin=  1,52  str = ",")
                                   ( Node #68 {Terminal: 4} (Tok:  4  lin=  1,54  str = "9.19", flo=9.190000) )  )  )  )  )  )  )  )  )  )  )  )  )
           ( Node #8 {Terminal: 34} (Tok: 34  lin=  1,60  str = "}") )  )  )  )

    
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
