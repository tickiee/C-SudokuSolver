/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package grid;
import java.io.*;
import java.util.ArrayList;

/**
 * Abstract class representing the general interface for a Sudoku grid.
 * Both standard and Killer Sudoku extend from this abstract class.
 */
public abstract class SudokuGrid
{

    /**
     * Load the specified file and construct an initial grid from the contents
     * of the file.  See assignment specifications and sampleGames to see
     * more details about the format of the input files.
     *
     * @param filename Filename of the file containing the intial configuration
     *                  of the grid we will solve.
     *
     * @throws FileNotFoundException If filename is not found.
     * @throws IOException If there are some IO exceptions when openning or closing
     *                  the files.
     */
    public abstract void initGrid(String filename)
        throws NumberFormatException, FileNotFoundException, IOException;


    /**
     * Write out the current values in the grid to file.  This must be implemented
     * in order for your assignment to be evaluated by our testing.
     *
     * @param filename Name of file to write output to.
     *
     * @throws FileNotFoundException If filename is not found.
     * @throws IOException If there are some IO exceptions when openning or closing
     *                  the files.
     */
    public abstract void outputGrid(String filename)
        throws FileNotFoundException, IOException;


    /**
     * Converts grid to a String representation.  Useful for displaying to
     * output streams.
     *
     * @return String representation of the grid.
     */
    public abstract String toString();


    /**
     * Checks and validates whether the current grid satisfies the constraints
     * of the game in question (either standard or Killer Sudoku).  Override to
     * implement game specific checking.
     *
     * @return True if grid satisfies all constraints of the game in question.
     */
    public abstract boolean validate();
    
    /**
     * Returns an arraylist of numbers used in the Sudoku puzzle
     * 
     * @return ArrayList of number list used 
     */
    public abstract ArrayList<Integer> getSudokuNumbersList();
    
    /**
     * Returns the grid
     * 
     * @return ArrayList of the grid (Sudoku Puzzle)
     */
    public abstract ArrayList<ArrayList<Integer>> getSudokuPuzzle();
    
    /**
     * Returns the size (length) of the puzzle
     * 
     * @return an integer representing the size (length) of the puzzle
     */
    public abstract int getPuzzleLengthSize();
    
    /****************************
     * Methods for Killer Sudoku*
     ****************************/
    
    /**
     * Returns the number of cages in the killer sudoku puzzle
     * 
     * @return an integer representing the number of cages
     */
    public abstract int getNumberOfCages();
    
    /**
     * Sets the Cell Value in a given row and column of a puzzle
     * @param cellValue an integer representing the value of the cell
     * @param row an integer representing the row index of the puzzle
     * @param col an integer representing the col index of the puzzle
     */
    public abstract void setCellValue(int cellValue, int row, int col);
    
    /**
     * Resets the cage due to an incorrect input in the cell value
     * @param cellValue an integer representing the value of the cell
     * @param currentRow an integer representing the row index of the puzzle
     * @param currentCol an integer representing the col index of the puzzle
     */    
	public abstract void subtractCage(int cellValue, int currentRow, int currentCol);
	
	/**
	 * Checks if the cage is valid when the cell value changes in a particular row and column
     * @param cellValue an integer representing the value of the cell
     * @param row an integer representing the row index of the puzzle
     * @param col an integer representing the col index of the puzzle
	 * @return a boolean representing if the puzzle is still valid
	 */
	public abstract boolean checkCage(int cellValue, int row, int col);
	
    /********************************************
     * Methods for Killer Sudoku Advanced Solver*
     ********************************************/
	
	/**
	 * Finds out the difference in the current size of the cage to its cage size.
	 * If the difference is 1, it returns true
     * @param row an integer representing the row index of the puzzle
     * @param col an integer representing the col index of the puzzle
	 * @return a boolean representing if the size is of a difference by 1
	 */
	public abstract boolean currentSizeVSCageSize(int row, int col);
	
	/**
	 * Gets the index of the row of the last cell in the cage
     * @param row an integer representing the row index of the puzzle
     * @param col an integer representing the col index of the puzzle
	 * @return an integer representing the index of the last cell's row
	 */
	public abstract int getLastCageRow(int row, int col);

	/**
	 * Gets the index of the col of the last cell in the cage
     * @param row an integer representing the row index of the puzzle
     * @param col an integer representing the col index of the puzzle
	 * @return an integer representing the index of the last cell's col
	 */
	public abstract int getLastCageCol(int row, int col);

	/**
	 * Gets the cell value of the last cell in the cage
     * @param row an integer representing the row index of the puzzle
     * @param col an integer representing the col index of the puzzle
	 * @return an integer representing the index of the last cell's cell value
	 */
	public abstract int getLastCageCellValue(int row, int col);
	
	/**
	 * Checks if the cell has a value, i.e. not -1 (default value for representing the cell has no value)
	 * If it does, return true
     * @param row an integer representing the row index of the puzzle
     * @param col an integer representing the col index of the puzzle
	 * @return a boolean representing if the cell has a value.
	 */
	public abstract boolean checkIfCellHasValue(int row, int col);

    // To be removed
	public abstract String getSudokuNumbersListInString();


	public abstract boolean checkSudokuNumbersList();


	public abstract boolean checkPuzzleRow();


	public abstract boolean checkPuzzleColumn();


	public abstract boolean checkPuzzleBox();
	
	
	public abstract String getKillerSudokuCageToString();


} // end of abstract class SudokuGrid
