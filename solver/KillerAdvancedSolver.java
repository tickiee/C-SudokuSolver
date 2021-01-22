/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;
import java.util.ArrayList;

import grid.SudokuGrid;

/**
 * Your advanced solver for Killer Sudoku.
 */
public class KillerAdvancedSolver extends KillerSudokuSolver
{
    // TODO: Add attributes as needed.
	private int puzzleLengthSize;
	private ArrayList<Integer> availableNumbersList;

    public KillerAdvancedSolver() {
        // TODO: any initialisation you want to implement.
    	this.puzzleLengthSize = -1;
    	this.availableNumbersList = new ArrayList<Integer>();
    } // end of KillerAdvancedSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        // TODO: your implementation of your advanced solver for Killer Sudoku.
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
    	
    	int rowOfLastCage = -1;
    	int colOfLastCage = -1;
    	int cellValueOfLastCage = -1;
    	
    	if(rowValue >= puzzleLengthSize) {
    		found = true;
    	} else {
    		// Check if there is no cell value in the grid
    		if(!grid.checkIfCellHasValue(rowValue, colValue)) {
        		ArrayList<Integer> tempList = new ArrayList<Integer>(availableNumbersList);
        		while(!found && tempList.size() > 0) {
            		cellValue = tempList.get(0);
            		tempList.remove(0);
        				
        			grid.setCellValue(cellValue, currentRow, currentCol);
               			
        			boolean checkCage = grid.checkCage(cellValue, currentRow, currentCol);
        			
    				//Difference
    				boolean minusOneDiff = grid.currentSizeVSCageSize(currentRow, currentCol);
    				boolean cageLastCell = true;
    				
    				if(minusOneDiff) {
    					cageLastCell = false;
    					rowOfLastCage = grid.getLastCageRow(currentRow, currentCol);
    					colOfLastCage = grid.getLastCageCol(currentRow, currentCol);
    					cellValueOfLastCage = grid.getLastCageCellValue(currentRow, currentCol);
    					
    					grid.setCellValue(cellValueOfLastCage, rowOfLastCage, colOfLastCage);
    					
            			cageLastCell = grid.checkCage(cellValueOfLastCage, rowOfLastCage, colOfLastCage);
    				}
        			
        			if(grid.validate() && checkCage && cageLastCell) {
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
        			
        			if(!found) {
            			grid.subtractCage(cellValue, currentRow, currentCol);
            			
            			if(rowOfLastCage != -1 && colOfLastCage != -1) {
            				grid.subtractCage(cellValueOfLastCage, rowOfLastCage, colOfLastCage);
            				grid.setCellValue(-1, rowOfLastCage, colOfLastCage);
            			}
        			}
        		}
            		
        		if(!found) {    			
        			grid.setCellValue(-1, currentRow, currentCol);
        		}
    		} else {
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
    	}
    	return found;
    }
} // end of class KillerAdvancedSolver
