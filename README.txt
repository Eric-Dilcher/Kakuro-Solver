This program recursively solves Kakuro (Cross sums) puzzles. It reads a file in the current working directory and populates a 2D array of Field objects according to the file. Expects the file to have the following format:

First Line: (integer representing number of rows) (integer representing number of columns) 

rows*columns number of lines, each with: if black box: (integer representing across value) (integer representing down value) {note: 0 0 if empty black square} if white box (integer -1). The lines represent the boxes of the puzzle from left to right, top to bottom.

Sample puzzles are included in the repository, and are named Kakuro*.txt.  Currently, the program requires the puzzle files to be in the same directory as the bytecode.

Run the "Kakuro" bytecode to solve all included puzzles (Solutions printed to console).
