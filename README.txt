This program recursively solves Kakuro (Cross sums) puzzles. It reads a puzzle file in the current working directory and populates a 2D array of Field objects according to the puzzle file. Expects the file to have the following format:

First Line: (integer representing number of rows) (integer representing number of columns) 

The following rows*columns number of lines: 
if "clue" box: (integer representing across value) (integer representing down value) {note: 0 0 if empty clue box} 
if blank box (integer -1).
Each line in the file represents a box of the puzzle according to the order: left to right, top to bottom.

Sample puzzles are included in the repository, and are named kakuro*.txt. Run the "Kakuro" bytecode to solve all included puzzles (Solutions printed to console).
