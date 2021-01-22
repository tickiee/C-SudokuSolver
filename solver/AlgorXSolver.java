/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;
import java.util.ArrayList;

import grid.*;

/**
 * Algorithm X solver for standard Sudoku.
 */
public class AlgorXSolver extends StdSudokuSolver
{
    // TODO: Add attributes as needed.
	private SudokuGrid grid;
	
	private int puzzleLengthSize;
	
	private ArrayList<ArrayList<Integer>> exactCoverMatrix;
	private ArrayList<Integer> availableNumbersList;
	private ArrayList<ArrayList<Integer>> copyingRemovedRows;
	private ArrayList<Integer> colToCheck;

    public AlgorXSolver() {
        // TODO: any initialisation you want to implement.
    	this.grid = null;
    	
    	this.puzzleLengthSize = -1;
    	
    	exactCoverMatrix = new ArrayList<ArrayList<Integer>>();
    	availableNumbersList = new ArrayList<Integer>();
    	copyingRemovedRows = new ArrayList<ArrayList<Integer>>();
    	colToCheck = new ArrayList<Integer>();
    } // end of AlgorXSolver()


    @Override
    public boolean solve(SudokuGrid grid) {
        // TODO: your implementation of the Algorithm X solver for standard Sudoku.
    	boolean solveable = false;
    	
    	this.grid = grid;
    	this.puzzleLengthSize = grid.getPuzzleLengthSize();
    	this.availableNumbersList = grid.getSudokuNumbersList();
    	
    	initexactCoverMatrix();
    	initMatrixToSudoku();
    	
//    	ArrayList<Integer> confirmedRows = new ArrayList<Integer>();
//    	ArrayList<Integer> columnsAvailable = new ArrayList<Integer>();
    	
//    	for(int i = 2; i <= matrixConstraintsSize; ++i) {
//    		columnsAvailable.add(i);
//    	}
//    	System.out.println(columnsAvailable.toString());

    	/** To backtrack from rowIndex to the sudoku puzzle 
    	 * rowIndex to Sudoku Puzzle
    	 * int row = index / (dimLength ^ 2)
    	 * leftIndex = index % (dimLength ^ 2)
    	 * int col = leftIndex / (dimLength ^ 1)
    	 * leftIndex = index % (dimLength ^ 1)
    	 * int num = leftIndex
    	 */
    	
//    	solveable = findSolution(exactCoverMatrix, columnsAvailable, confirmedRows);
    	
    	// Remove this method
    	printExactCoverMatrix();
    	
        // placeholder
        return solveable;
    } // end of solve()
    
    private void initexactCoverMatrix() {
    	int matrixRowSize = (int) Math.pow(Double.valueOf(puzzleLengthSize), 3);
    	int matrixColSize = (int) Math.pow(Double.valueOf(puzzleLengthSize), 2) * 4 + 1; //+1 for labeling
    	System.out.println("Matrix Row Size: " + matrixRowSize);
    	System.out.println("Matrix Col Size: " + matrixColSize);
    	
    	for(int row = 0; row < matrixRowSize; ++row) {
    		exactCoverMatrix.add(new ArrayList<Integer>(matrixColSize));
    		exactCoverMatrix.get(row).add(row);
    		for(int col = 1; col < matrixColSize; ++col) {
    			exactCoverMatrix.get(row).add(0);
    		}
    	}
    	
    	cellConstraint();
    	rowConstraint();
    	colConstraint();
    	boxConstraint();
    }
    
    private void cellConstraint() {
    	int rowValue = 0;
    	int rowStopper = puzzleLengthSize;
    	int rowIncrement = rowStopper;
    	
    	int colStopper = exactCoverMatrix.get(0).size() / 4 + 1; // +1
    	
//    	System.out.println(colStopper);
    	for(int col = 1; col < colStopper; ++col) {
    		for(int row = rowValue; row < rowStopper; ++row) {
    			exactCoverMatrix.get(row).set(col, 1);
    		}
    		
    		rowValue = rowValue + rowIncrement;
    		rowStopper = rowStopper + rowIncrement;
    	}
    }
    
    private void rowConstraint() {
    	// count is used to cycle through the while loop based on the length of the puzzle
    	int count = 0;
    	int countStopper = puzzleLengthSize;
    	
    	int rowValue = 0;
    	int rowStopper = (int) Math.pow(puzzleLengthSize, 2); //16
    	int rowIncrement = rowStopper; //16
    	
    	int colValue = exactCoverMatrix.get(0).size() / 4 + 1; //16 +1
    	int colStopper = puzzleLengthSize + colValue;  //20
    	int colIncrement = puzzleLengthSize;
    	
    	while(count < countStopper) {
    		int currentColValue = colValue;
    		
    		for(int row = rowValue; row < rowStopper; ++row) {
    			exactCoverMatrix.get(row).set(colValue, 1);
//    			System.out.println("Row :" + row + " colValue " + colValue + " (1)");
				colValue = colValue + 1;
				
    			if(colValue >= colStopper) {
    				colValue = currentColValue;
    			}
    		}
    		
    		count = count + 1;
    		
    		rowValue = rowValue + rowIncrement;
    		rowStopper = rowStopper + rowIncrement;
    		
    		colValue = colValue + colIncrement;
    		colStopper = colStopper + colIncrement;
    	} 	
    }
    
    private void colConstraint() {
    	// count is used to cycle through the while loop based on the length of the puzzle
    	int count = 0;
    	int countStopper = puzzleLengthSize;
    	
    	int rowValue = 0;
    	int rowStopper = (int) Math.pow(puzzleLengthSize, 2); // 16
    	int rowIncrement = rowStopper;
    	
    	int colValue = (exactCoverMatrix.get(0).size() / 4) * 2 + 1; // 32 +1
    	int initialColValue = colValue;
    	
    	while(count < countStopper) {
    		for(int row = rowValue; row < rowStopper; ++row) {
    			exactCoverMatrix.get(row).set(colValue, 1);
//    			System.out.println("Row :" + row + " colValue " + colValue + " (1)");
    			colValue = colValue + 1;
    		}
    		
    		count = count + 1;
    		
    		rowValue = rowValue + rowIncrement;
    		rowStopper = rowStopper + rowIncrement;
    		
    		colValue = initialColValue;
    	}
    }
    
    private void boxConstraint() {
    	
    	int cellIndex = 1;
    	
    	int rowValue = 0;
    	int rowStopper = puzzleLengthSize;
    	int rowIncrement = rowStopper;
    	
    	int colIncrement = puzzleLengthSize;
    	
    	while(rowValue < exactCoverMatrix.size()) {
    		
    		int boxNumber = boxNumber(cellIndex);
    		int colValue = ((exactCoverMatrix.get(0).size() - 1) / 4) * 3 + boxNumber * puzzleLengthSize - colIncrement + 1; // colStopper - 4 +1
//    		int colValue = (exactCoverMatrix.get(0).size() / 4) * 3 + boxNumber * puzzleLengthSize - colIncrement + 1; // colStopper - 4 +1
    		
    		for(int row = rowValue; row < rowStopper; ++row) {
    			exactCoverMatrix.get(row).set(colValue, 1);
    			colValue = colValue + 1;
    		}
    		
    		rowValue = rowValue + rowIncrement;
    		rowStopper = rowStopper + rowIncrement;
    		cellIndex = cellIndex + 1;
    	}
    }
    
    public int boxNumber(int cellIndex) {
    	int boxNumber = -1;
    	
    	int sqrtSize = (int) Math.sqrt(puzzleLengthSize);
    	ArrayList<Integer> lowerBound = new ArrayList<Integer>(sqrtSize);
    	ArrayList<Integer> upperBound = new ArrayList<Integer>(sqrtSize);
    	
    	int lb = 1;
    	int ub = sqrtSize;
    	int count = 0;
    	
    	while(count < sqrtSize) {
    		lowerBound.add(lb);
    		upperBound.add(ub);
    		lb = lb + sqrtSize;
    		ub = ub + sqrtSize;
    		
    		count = count + 1;
    	}
    	
    	boolean found = false;
    	
//    	System.out.println("Cell Index: " + cellIndex);
    	
    	for(int i = 0; !found && i < lowerBound.size(); ++i) {
//    		System.out.println("lower bound: " + lowerBound.get(i) + " " + " upper bound: " + upperBound.get(i));
    		if((cellIndex % puzzleLengthSize) >= lowerBound.get(i) && (cellIndex % puzzleLengthSize) <= upperBound.get(i)) {
    			found = true;
    			boxNumber = i + 1;
    		} else if((i + 1) == lowerBound.size() && cellIndex % puzzleLengthSize == 0) {
    			found = true;
    			boxNumber = i + 1;
    		}   		
			
//			System.out.println("BN: " + boxNumber);
			int maxCellIndex = (int) (Math.pow(puzzleLengthSize, 2) / sqrtSize);
			int maxCellIndexIncrement = maxCellIndex;
			while(cellIndex > maxCellIndex) {
				maxCellIndex = maxCellIndex + maxCellIndexIncrement;
				boxNumber = boxNumber + sqrtSize;
			}
    	}    	
    	return boxNumber;
    }
    
    private void initMatrixToSudoku() {
    	ArrayList<Integer> rowsToRemove = new ArrayList<Integer>();
    	
    	for(int row = 0; row < puzzleLengthSize; ++row) {
    		for(int col = 0; col < puzzleLengthSize; ++col) {
    			int cellNumber = grid.getSudokuPuzzle().get(row).get(col);
    			if(cellNumber != -1) {
    				boolean found = false;
    				
    				int cellIndex = -1;
    				
    				boolean foundCellNumberFromList = false;
    				for(int i = 0; !foundCellNumberFromList && i < availableNumbersList.size(); ++i) {
    					if(cellNumber == availableNumbersList.get(i)) {
    						cellIndex = i;
    						foundCellNumberFromList = true;
    					}
    				}
    				
    				int rowValue = (int) (row * Math.pow(puzzleLengthSize, 2) + col * puzzleLengthSize + cellIndex);
    				
    				System.out.println("Row Value: " + rowValue);
    				rowsToRemove.add(rowValue);
    				
//    				// Finding the i value (the value of X in # from RXCX#X)
//    				for(int i = 0; !found && i < availableNumbersList.size(); ++i) {
//    					if(cellNumber == availableNumbersList.get(i)) {
//    						found = true;
//    						rowValue = rowValue + i; //The row to keep
//    						
//    						// Copy the row out for pasting after removeFromMatrix() method
//    						copyingRemovedRows.add(new ArrayList<Integer>(exactCoverMatrix.get(rowValue)));
////    						System.out.println(copyingRemovedRows.toString());
//    						
//    						// Find the columns that have 1 in the row
//    						columnsToCheck(rowValue);
//    					}
//    				}
    			}
    		}
    	}
    	
    	/** This method below is having error **/
//		removeFromMatrix(rowsToRemove);
    }
    
    private void removeFromMatrix(ArrayList<Integer> rowsToRemove) {
//    	System.out.println("Row Size: " + matrix.size());
//    	System.out.println("Col Size: " + matrix.get(rowIndex).size());
    	ArrayList<Integer> columnsToRemove = new ArrayList<Integer>();
    	ArrayList<Integer> minorRowsToRemove = new ArrayList<Integer>();
    	
    	int rowsToRemoveIndex = 0;
    	
    	while(rowsToRemoveIndex < rowsToRemove.size()) {
        	boolean found = false;
        	int rowIndex = -1;
        	int rowValue = rowsToRemove.get(0);
        	
        	for(int i = 0; !found && i < exactCoverMatrix.size(); ++i) {
        		if(rowValue == exactCoverMatrix.get(i).get(0)) {
        			rowIndex = i;
        			found = true;
        		}
        	}
        	
        	System.out.print("Row Index: " + rowIndex);
        	
        	int initialCol = 1;
        	while(initialCol < exactCoverMatrix.get(rowIndex).size()) {
        		System.out.println(exactCoverMatrix.get(rowIndex).get(initialCol));
        		if(exactCoverMatrix.get(rowIndex).get(initialCol) == 1) {
        			
        			boolean duplicate = false;
        			// Do not add duplicates
        			for(int i = 0; i < columnsToRemove.size(); ++i) {
        				if(initialCol == columnsToRemove.get(i)) {
        					duplicate = true;
        				}
        			}
        			
        			if(!duplicate) {
        				// Adds columns to be removed
        				columnsToRemove.add(initialCol);
        			}
        		}
        		
        		initialCol++;
        	}
    	}
    	
    	int index = 0;
    	while(index < exactCoverMatrix.size()) {
    		boolean toBeRemoved = false;
    		
    		for(int i = 0; !toBeRemoved && i < columnsToRemove.size(); ++i) {
    			if(exactCoverMatrix.get(index).get(columnsToRemove.get(i)) == 1) {
    				toBeRemoved = true;
    			}
    		}
    		
    		if(toBeRemoved) {
    			boolean duplicate = false;
    			// Do not add duplicates
    			for(int i = 0; i < minorRowsToRemove.size(); ++i) {
    				if(index == minorRowsToRemove.get(i)) {
    					duplicate = true;
    				}
    			}
    			
    			if(!duplicate) {
    				// Adds rows to be removed
    				minorRowsToRemove.add(index);
    			}
    		} else {
    			index = index + 1;
    		}
    	}
  
    	// Delete col in columnsToRemove
    	int row = 0;
    	while(row < exactCoverMatrix.size()) {
    		for(int i = 0; i < columnsToRemove.size(); ++i) {
    			exactCoverMatrix.get(row).remove(columnsToRemove.get(i) - i);
    		}
    		
    		row = row + 1;
    	}
    	
    	// Delete row in minorRowsToRemove
    	for(int i = 0; i < minorRowsToRemove.size(); ++i) {
    		exactCoverMatrix.remove(minorRowsToRemove.get(i) - i);
    	}
    	
    	// Delete rows in rowsToRemove
    	for(int i = 0; i < rowsToRemove.size(); ++i) {
    		exactCoverMatrix.remove(rowsToRemove.get(i) - i);
    	}
    }
    
//    private boolean findSolution(ArrayList<ArrayList<Integer>> matrix,
//    								ArrayList<Integer> columnsAvailable, ArrayList<Integer> rowsSelected) {
//    	boolean solveable = false;
//    	int matrixConstraintsSize = matrix.get(0).size();
//    	int matrixRowColCellSize = matrix.size();
//    	
//    	if(matrixConstraintsSize == 1) {
//    		// Soltuion found
//    	} else {
//    		int chosenColumn = columnsAvailable.get(0) - 1;
//    		
//    		for(int r = 0; r < matrixRowColCellSize; ++r) {
//    			if(matrix.get(r).get(chosenColumn) == 1) {
//    				rowsSelected.add(matrix.get(r).get(0));
//    				ArrayList<ArrayList<Integer>> savedMatrix = new ArrayList<ArrayList<Integer>>(matrix);
//    				System.out.println(savedMatrix.toString());
//    				ArrayList<Integer> savedColumnsAvailable = new ArrayList<Integer>(columnsAvailable);
//    				int savedRowSize = matrixRowColCellSize;
//					int savedColSize = matrixConstraintsSize;
//					
//    				for(int j = 0; j < matrixConstraintsSize; ++j) {
//    					if(matrix.get(r).get(j) == 1) {
//    						for(int i = 0; i < matrixRowColCellSize; ++i) {
//    							if(matrix.get(i).get(j) == 1) {
//    								// Delete row i
//    								matrix.remove(i);
//    								
//    								matrixRowColCellSize = matrixRowColCellSize - 1;
//    							}
//    						}
//    						
//    						// Delete col j
//    						for(int rows = 0; rows < matrixRowColCellSize; ++rows) {
//    							matrix.get(rows).remove(j);
//    						}
//    						
//    						matrixConstraintsSize = matrixConstraintsSize - 1;
//    					}
//    				}
//    				
//    				// Delete row r
//    				matrix.remove(r);
//    				
//    				matrixRowColCellSize = matrixRowColCellSize - 1;
//    				
//    				solveable = findSolution(matrix, columnsAvailable, rowsSelected);
//    				
//    				matrix = new ArrayList<ArrayList<Integer>>(savedMatrix);
//    				columnsAvailable = new ArrayList<Integer>(savedColumnsAvailable);
//    				matrixRowColCellSize = savedRowSize;
//    				matrixConstraintsSize = savedColSize;
//    				
//    				boolean found = false;
//    				for(int i = 0; !found && i < rowsSelected.size(); ++i) {
//    					if(rowsSelected.get(i) == matrix.get(r).get(0)) {
//    						rowsSelected.remove(i);
//    						matrixRowColCellSize = matrixRowColCellSize - 1;
//    						found = true;
//    					}
//    				}
//    			}
//    		}
//    		System.out.println("Hello");
//    		columnsAvailable.remove(0);
//    	}
//    	return solveable;
//    }
    
    // Remove this method
    public void printExactCoverMatrix() {
    	String statement = "";
    	
    	for(int row = 0; row < exactCoverMatrix.size(); ++row) {
    		for(int col = 0; col < exactCoverMatrix.get(0).size(); ++col) {
    			String numberInString = String.valueOf(exactCoverMatrix.get(row).get(col));
    			statement = statement + numberInString + ",";
    		}
    		
    		// Checks whether to enter a new line. If its the last iteration, do not enter a new line
    		if(row == exactCoverMatrix.size() - 1) {
    			statement = statement.substring(0, statement.length() - 1);
    		} else {
    			statement = statement.substring(0, statement.length() - 1) + "\n";
    		}
    	}
    	
    	System.out.println(statement);
    }
} // end of class AlgorXSolver
