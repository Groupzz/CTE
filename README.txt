# CTE
Class Number: CECS 444 - Section 01

Project Name: Project #1 - Lexer

Team Name: CTE

Team Members: Aleks Dziewulska, Jessica Hilario, Jamil Khan, Joshua Lorenzen

Intro: The objective of this assignment is to write a lexer for the A5 language lexicon: its tokens (i.e., legal “words”).
       The lexer reads a code file or typed through standard input and divides it up into single tokens.

Contents: README.txt, Lexer.java, Token.java

External Requirements: No special requirements necessary

Setup and Installation: No special setup/installation required

Sample invocation and results to see: 
    1. Open the Linux terminal or Windows Powershell.
    2. Navigate to the folder with both Lexer.java and Token.java files.
    3. Once in the folder, compile the program using command "javac Lexer.java".
    4. To run the program you can:
        4a. Use "cat FILENAME | java Lexer" command to pipe file through standard input into "Lexer.java" program. 
            (FILENAME is the code file to compile)
        4b. Use "java Lexer" command to enter code through standard input. To close the input stream use ctrl + d
            in the Linux terminal or ctrl + z in the Windows Powershell.
    5. Results should be a standard output of all tokens that are found in the compiled code file.
    Sample input: 
        prog main { print ("Sample input", "S= ", 27, "I= ", 9.19);}
    Sample output:
        (Tok: 10  lin=  1,1   str = "prog")
        (Tok: 11  lin=  1,6   str = "main")
        (Tok: 33  lin=  1,11  str = "{")
        (Tok: 23  lin=  1,13  str = "print")
        (Tok: 37  lin=  1,19  str = "(")
        (Tok:  5  lin=  1,20  str = "Sample input")
        (Tok:  6  lin=  1,34  str = ",")
        (Tok:  5  lin=  1,36  str = "S= ")
        (Tok:  6  lin=  1,41  str = ",")
        (Tok:  3  lin=  1,43  str = "27", int=27)
        (Tok:  6  lin=  1,45  str = ",")
        (Tok:  5  lin=  1,47  str = "I= ")
        (Tok:  6  lin=  1,52  str = ",")
        (Tok:  4  lin=  1,54  str = "9.19", flo=9.190000)
        (Tok: 38  lin=  1,58  str = ")")
        (Tok:  7  lin=  1,59  str = ";")
        (Tok: 34  lin=  1,60  str = "}")
        (Tok:  0  lin=  2,1   str = "")
    

Features: 
    Included:
        1. List all tokens found in the code source file that is piped into the program.
        2. Display token details such as token ID, line and column number, text of token itself, as well as float and integer literals.
        3. Distinguish between a negative number and minus operator by using spaces.
        4. Ignore comments listed in the code source file.
        5. Inform user of any syntax errors by outputting line and column details of error to standard input.
    Missing: N/A

Bugs: No bugs found.
