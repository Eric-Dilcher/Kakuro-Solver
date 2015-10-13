import java.util.*;
import java.io.*;

import javax.swing.JFileChooser;

/**
 * This is a class that solves a Kakuro (cross-sums) puzzle.
 * 
 * @author Eric Dilcher
 */
public class Kakuro {

  // class variables
  private Field grid[][]; // 2D array to represent the puzzle grid

  private static String askUserForFile() {
    String filename = null; // holds filename String
    File file = null; // holds File object
    JFileChooser fileopen = new JFileChooser();

    // Ask the user to select a puzzle file
    int ret = fileopen.showDialog(null, "Select puzzle file to open");
    // Check for errors
    if (ret == JFileChooser.APPROVE_OPTION) {
      file = fileopen.getSelectedFile();
      System.out.println("Input puzzle selected: " + file);
      filename = fileopen.getName(file);
    }
    return filename;
  }

  /**
   * Reads a file in the current working directory and populates a 2D array of
   * Field objects according to the file. 
   */
  public static Field[][] readKakuroFile(String filename) {
    File file = null;
    Field grid[][] = null;
    int rows;
    int cols;
    final int minBoardSize = 2;

    // Open a new file object, and check for exceptions
    file = new File(System.getProperty("user.dir") + "/" + filename);
    try {
      Scanner s = new Scanner(file);
      int nextInt; // declare an integer to hold the value read.
      int across; // declare integers for future use.
      int down;

      rows = s.nextInt(); // read the first int
      cols = s.nextInt(); // read the second int

      // input validation
      if (rows <= minBoardSize || cols <= minBoardSize) {
        System.out.println("Error - bad input file. One or more of the board "
            + "dimensions is less than two.");
        System.exit(-1);
      }

      // declare a 2D array of Field objects with the correct dimensions.
      grid = new Field[rows][cols];

      // loop over each element in the 2D array O(N)
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          nextInt = s.nextInt(); // read the next int
          if (nextInt < -1) { // check for bad input
            System.out.println("Error - bad input file. There exists an "
                + "integer less than -1");
            System.exit(-1); // exit program
          } 
          else if (nextInt == -1) { // check if a white field
            // create the appropriate Field object
            grid[i][j] = new Field();
          } 
          else { // otherwise it is a black field
            across = nextInt;
            down = s.nextInt();
			// ints greater than -1 always come in pairs
            if (down < 0) {
              System.out.println("Error - bad input file.");
              System.exit(-1);
            } 
            else {
              // create the appropriate Field object
              grid[i][j] = new Field(across, down);
            }
          }
        }
      }
      s.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error - file not found - bye-bye!");
      System.exit(-1);
    } catch (NumberFormatException e) {
      System.out.println("Invalid integer read from file!");
      System.exit(-1);
    } catch (NoSuchElementException e) {
      System.out.println("Ooops, the scanner ran out of integers to read from "
          + "the file before filling the board");
      System.exit(-1);
    }
    return grid; // return the 2D array of Field objects
  }



  /* Constructor that prompts the user for a puzzle file */
  public Kakuro() {
    this.grid = readKakuroFile(askUserForFile());
  }

  /*
   * Constructor that accepts the filename string of a puzzle
   * file
   */
  public Kakuro(String filename) {
    this.grid = readKakuroFile(filename);
  }

  public Field[][] getGrid() {
    return this.grid;
  }

  public void solve() {
    // start solving (recursively) for the top left field
    if (solve(this.grid, 0, 0)) {
      System.out.println("Solution found:");
      this.display();
    } else {
      System.out.println("no solution found");
    }
  }
  
  public static boolean solve(Field[][] grid, int row, int col) {
    int numRows = grid.length;
    int numCols = grid[0].length;
    
    //if the function was called on an invalid row, then we solved the puzzle
    if (row == numRows) {
      return true;
    }
    //if columns overflowed, then go to beginning of next line
    else if (col == numCols) {
      return solve(grid, row + 1, 0);
    }
    //if this field is not an adjustable field, go to next field to the right
    else if (!grid[row][col].isAdjustable()) {
      return solve(grid, row, col + 1);
    }

    for (int testVal = 1; testVal <= 9; testVal++) {
      if (valid(grid, row, col, testVal)) {
        grid[row][col].setPlayerValue(testVal);
        if (solve(grid, row, col + 1)) {
          return true;
        }
      }
    }
    //if we reach here, there is no solution to the puzzle
    return false;
  }
  
  //check to see if field is valid 
  private static boolean valid(Field[][] grid, int row, int col, int value) {
    int tempSum = value;
    int totalValue = 0;

    if (isValidRow(grid, row, col, value) && isValidRow(grid, row, col, value)){
      return true;
    }
    else{
      return false;
    }
  }
  
  private static boolean isValidRow(Field[][] grid, int row, int col, int value){
    int tempSum = value;
    int totalValue = 0;
    // check a row for duplicates and sum restraint (only need to check "left")
    for (int i = col - 1; i >= 0; i--) {
      if (!grid[row][i].isAdjustable()) { // break when black field is reached
        totalValue = grid[row][i].getAcross();
        break;
      }
      tempSum += grid[row][i].getPlayerValue();
      if (grid[row][i].getPlayerValue() == value) {
        return false;
      }
    }
    // check if sum of row exceeds specified number
    if (tempSum > totalValue) { 
      return false;
    }
    //if we reach the right end of the board, and the sum is less than the required amount
    if (col == grid[0].length - 1) {
      if (tempSum < totalValue) {
        return false;
      }
    }
    //if the next square is a black square, and the sum is less than the required amount
    else if (!grid[row][col + 1].isAdjustable()) {
      if (tempSum < totalValue) {
        return false;
      }
    } 
    return true;  // if none of the above conditions are met, return true
  }
  
  private static boolean isValidCol(Field[][] grid, int row, int col, int value){
    final int rowLength = grid[0].length;
    int tempSum = value;
    int totalValue = 0;
  
    // check a column for duplicates and sum restraint (only need to check "up")
    for (int i = row - 1; i >= 0; i--) {
      if (!grid[i][col].isAdjustable()) { // break if black field
        totalValue = grid[i][col].getDown();
        break;
      }
      tempSum += grid[i][col].getPlayerValue();
      if (grid[i][col].getPlayerValue() == value) {
        return false;
      }
    }
    // check if sum of col exceeds specified number
    if (tempSum > totalValue) {
      return false;
    }
    //if we reach the top of the board, and the sum is less than the required amount
    if (row == grid.length - 1) {
      if (tempSum < totalValue) {
        return false;
      }
    }
    //if the next square is a black square, and the sum is less than the required amount
    else if (!grid[row + 1][col].isAdjustable()) {
      if (tempSum < totalValue) {
        return false;
      }
    }
    return true; // if none of the above conditions are met, return true
  }
  
  public void display(){
    final int numRows = this.grid.length;
    final int numCols = this.grid[0].length;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        System.out.print(getCellString(grid, i,j));
      }
    }    
  }
  
  private static String getCellString(Field[][] grid, int row, int col){
    final int numCols = grid[0].length;
    String cellString;
    if (grid[row][col].isAdjustable()) {
      cellString = getAdjustableCellString(grid[row][col].getPlayerValue());
    } 
    else {
      cellString = getNotAdjustableCellString(grid[row][col].getDown(), grid[row][col].getAcross());
    }
    if (col == numCols - 1) {
      cellString += "\n";
    }
    return cellString;
  }
  
  private static String getAdjustableCellString(int playerValue){
    String cellString = null;
    if (playerValue == 0){
      cellString = "[     ]";
    }
    else{
    cellString = "[  " + playerValue
            + "  ]";
    }
    return cellString;
  }
   
  private static String getNotAdjustableCellString(int downVal, int acrossVal){
    String cellString = null;
    String downString = "" + downVal;
    String acrossString = "" + acrossVal;
    int downLen = downString.length();
    int acrossLen = acrossString.length();
    
    if (downLen == 2) {
      cellString = "[" + downString + "\\" + acrossString;
    } 
    else {
      cellString = "[ " + downString + "\\" + acrossString;
    }
    if (acrossLen == 2) {
      cellString += "]";
    } 
    else {
      cellString += " ]";
    }
    return cellString;
  }
    
  public static void main(String[] args) {
    // test on several valid puzzle inputs
    Kakuro k = new Kakuro("kakuro0.txt");
    System.out.println("Puzzle: kakuro0.txt");
    k.display();
    System.out.println("");
    k.solve();
    System.out.println("");
    k = new Kakuro("kakuro1.txt");
    System.out.println("Puzzle: kakuro1.txt");
    k.display();
    System.out.println("");
    k.solve();
    System.out.println("");
    k = new Kakuro("kakuro2.txt");
    System.out.println("Puzzle: kakuro2.txt");
    k.display();
    System.out.println("");
    k.solve();
    System.out.println("");
    k = new Kakuro("kakuro3.txt");
    System.out.println("Puzzle: kakuro3.txt");
    k.display();
    System.out.println("");
    k.solve();
    System.out.println("");
    k = new Kakuro("kakuro4.txt");
    System.out.println("Puzzle: kakuro4.txt");
    k.display();
    System.out.println("");
    k.solve();
  }
}

