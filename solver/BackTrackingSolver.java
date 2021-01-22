/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;
import java.util.ArrayList;

import grid.*;

/**
 * Backtracking solver for standard Sudoku.
 */
public class BackTrackingSolver extends StdSudokuSolver
{
    // TODO: Add attributes as needed.
	private SudokuGrid grid;
	private int puzzleLengthSize;
	
	private ArrayList<Integer> rowPuzzleIndexes;
	private ArrayList<Integer> colPuzzleIndexes;
	private ArrayList<Integer> availableNumbersList;
	private int rowcolListIndexSize;
	
    public BackTrackingSolver() {
        // TODO: any initialisation you want to implement.
    	this.grid = null;
    	this.puzzleLengthSize = -1;
    	this.rowPuzzleIndexes = new ArrayList<Integer>();
    	this.colPuzzleIndexes = new ArrayList<Integer>();
    	this.availableNumbersList = new ArrayList<Integer>();
    	this.rowcolListIndexSize = -1;
    } // end of BackTrackingSolver()

    @Override
    public boolean solve(SudokuGrid grid) {
        // TODO: your implementation of the backtracking solver for standard Sudoku.
    	boolean solveable = false;
    	this.grid = grid;
    	this.puzzleLengthSize = grid.getPuzzleLengthSize();
    	
    	for(int row = 0; row < puzzleLengthSize; ++row) {
    		for(int col = 0; col < puzzleLengthSize; ++col) {
    			if(grid.getSudokuPuzzle().get(row).get(col) == -1) {
    				rowPuzzleIndexes.add(row);
    				colPuzzleIndexes.add(col);
    			}
    		}
    	}
    	
    	this.rowcolListIndexSize = rowPuzzleIndexes.size();
    	int rowcolListIndex = 0;
    
    	// To be removed
    	this.availableNumbersList = grid.getSudokuNumbersList();
    	
    	solveable = performDFSRecursion(rowcolListIndex);

        // placeholder
        return solveable;
    } // end of solve()
    
    private boolean performDFSRecursion(int rowcolListIndex) {
    	boolean boolChecker = false;
    	
    	if(rowcolListIndex >= rowcolListIndexSize) {
    		boolChecker = true;
    	} else {
        	ArrayList<Integer>tempList = new ArrayList<Integer>(availableNumbersList);
        	while(!boolChecker && tempList.size() > 0) {
        		int getFrontNumber = tempList.get(0);
        		int rowValue = rowPuzzleIndexes.get(rowcolListIndex);
        		int colValue = colPuzzleIndexes.get(rowcolListIndex);
        		
        		grid.getSudokuPuzzle().get(rowValue).set(colValue, getFrontNumber);
        		
        		tempList.remove(0);
        		
        		if(grid.validate()) {
        			boolChecker = performDFSRecursion(rowcolListIndex + 1);
        		} 
        	}
        	
        	// Reset the number to -1 if all numbers in numbersList has been checked
        	if(!boolChecker) {
        		grid.getSudokuPuzzle().get(rowPuzzleIndexes.get(rowcolListIndex)).set(colPuzzleIndexes.get(rowcolListIndex), -1);
        	}
    	}
    	
    	return boolChecker;
    }
} // end of class BackTrackingSolver()
