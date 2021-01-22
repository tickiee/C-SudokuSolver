/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;
import java.util.ArrayList;

import grid.*;

/**
 * Backtracking solver for Killer Sudoku.
 */
public class KillerBackTrackingSolver extends KillerSudokuSolver
{
    // TODO: Add attributes as needed.
	private int puzzleLengthSize;
	private ArrayList<Integer> availableNumbersList;
	
    public KillerBackTrackingSolver() {
        // TODO: any initialisation you want to implement.
    	this.puzzleLengthSize = -1;
    	this.availableNumbersList = new ArrayList<Integer>();
    } // end of KillerBackTrackingSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        // TODO: your implementation of a backtracking solver for Killer Sudoku.
    	boolean solveable = false;
    	this.puzzleLengthSize = grid.getPuzzleLengthSize();
    	
    	this.availableNumbersList = grid.getSudokuNumbersList();
    	
    	int rowValue = 0;
    	int colValue = 0;
    	
    	solveable = findSolution(grid, rowValue, colValue);
    	
        // placeholder
        return solveable;
    } // end of solve()
    
    private boolean findSolution(SudokuGrid grid, int rowValue, int colValue) {
    	boolean found = false;
    	
    	int currentRow = rowValue;
    	int currentCol = colValue;
    	int cellValue = -1;
    	
    	if(rowValue >= puzzleLengthSize) {
    		found = true;
    	} else {
    		ArrayList<Integer> tempList = new ArrayList<Integer>(availableNumbersList);
    		while(!found && tempList.size() > 0) {
        		cellValue = tempList.get(0);
        		tempList.remove(0);
//				System.out.println("Current Row: " + currentRow + 
//						" Current Column: " + currentCol +
//						" Cell Value:" + cellValue);
    				
    			grid.setCellValue(cellValue, currentRow, currentCol);
    			
//    			System.out.println(grid.toString());
//    			System.out.println("Validation says: " + grid.validate());
//    			System.out.println("Numbers List: " + grid.checkSudokuNumbersList());
//    			System.out.println("Row: " + grid.checkPuzzleRow());
//    			System.out.println("Column: " + grid.checkPuzzleColumn());
//    			System.out.println("Box: " + grid.checkPuzzleBox());
    			
    			boolean checkCage = grid.checkCage(cellValue, currentRow, currentCol);
//    			System.out.println("Cage: " + checkCage);
    			
    			if(grid.validate() && checkCage) {
    				int newRow = currentRow;
    				int newCol = currentCol;
        				
    				if(newCol == puzzleLengthSize - 1) {
    					newCol = 0;
    					newRow = newRow + 1;
    				} else {
    					newCol = newCol + 1;
    				}
        				        				
    				found = findSolution(grid, newRow, newCol);
    			}
    			
    			grid.subtractCage(cellValue, currentRow, currentCol);
    		}
        		
    		if(!found) {    			
    			grid.setCellValue(-1, currentRow, currentCol);
    		}
    	}
    	return found;
    }
} // end of class KillerBackTrackingSolver()
