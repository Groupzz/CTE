# CTE
Class Number: CECS 444 - Section 01

Project Name: Project #1 - Lexer

Team Name: CTE (Compile Time Error)

Team Members: Aleks Dziewulska, Jessica Hilario, Jamil Khan, Joshua Lorenzen

Intro: The objective of this assignment is to write a lexer for the A5 language lexicon: its tokens (i.e., legal “words”).

 (Aleks) Contents: files in .zip submission (can be done on Friday)

External Requirements: No special requirements were necessary

Setup and Installation: No special setup/installation required

(Aleks) Sample invocation and results to see: 

One of the methods possible using Linux terminal or Powershell

1. Compile program through terminal using command "javac Lexer.java".
2. Use "cat" command to pipe file through standard input into "Lexer.java" program and run the program.
3. Results should be all tokens that are found in the file that was piped into the program.

Windows Terminal (Aleks)

Features (both included and missing): 
1. Lists all tokens found in a file that is piped into the program
2. Displays token details such as token ID, line and column number, text of token itself, as well as float and integer literals.
3. Distinguishes between a negative number and minus operator by using spaces.
4. Ignores comments listed in the file that is piped through the program.
5. Informs user of any errors thrown by giving them line and column details of error.

Bugs (if any): No bugs found.
