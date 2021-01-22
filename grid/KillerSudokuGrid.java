/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package grid;
import java.io.*;
import java.util.ArrayList;

/**
 * Class implementing the grid for Killer Sudoku.
 * Extends SudokuGrid (hence implements all abstract methods in that abstract
 * class).
 * You will need to complete the implementation for this for task E and
 * subsequently use it to complete the other classes.
 * See the comments in SudokuGrid to understand what each overriden method is
 * aiming to do (and hence what you should aim for in your implementation).
 */
public class KillerSudokuGrid extends SudokuGrid
{
    // TODO: Add your own attributes
	private int boxSize;
	private int puzzleLengthSize;
	private ArrayList<Integer> sudokuNumbersList;
	private ArrayList<ArrayList<Integer>> sudokuPuzzle;
	
	private int numberOfCages;
	private ArrayList<KillerSudokuCage> killerSudokuCage;

    public KillerSudokuGrid() {
        super();

        // TODO: any necessary initialisation at the constructor
        this.boxSize = -1;
        this.puzzleLengthSize = -1;
        this.sudokuNumbersList = new ArrayList<Integer>();
        this.sudokuPuzzle = new ArrayList<ArrayList<Integer>>();
        
        this.numberOfCages = -1;
        this.killerSudokuCage = new ArrayList<KillerSudokuCage>();
    } // end of KillerSudokuGrid()


    /* ********************************************************* */


    @Override
    public void initGrid(String filename)
        throws FileNotFoundException, IOException
    {
        // TODO
    	BufferedReader inReader = new BufferedReader(new FileReader(filename));
    	creatingKillerSudokuPuzzle(inReader);
    } // end of initBoard()
    
    private void creatingKillerSudokuPuzzle(BufferedReader inReader) throws NumberFormatException, IOException {
    	String line;
    	int lineNum = 1;
    	boolean checkingFileError = false;
    	
    	while(!checkingFileError && (line = inReader.readLine()) != null) {
    		String[] tokens = line.split(" ");
    		
    		// Check if puzzle was a Standard Sudoku puzzle instead
    		if(lineNum == 3 && tokens.length > 1) {
    			System.out.println("File error. This is a Standard Sudoku Puzzle");
    			checkingFileError = true;
    		} else {
    			// Check for the size of box
    			if(lineNum == 1 && tokens.length == 1) {
    				puzzleLengthSize = Integer.parseInt(tokens[0]);
    				
    				// Checks if the number is a perfect square, sudoku puzzle boxes need to be perfect square
					double squareRoot = Math.sqrt(puzzleLengthSize);
					if(squareRoot - Math.floor(squareRoot) == 0) {
						boxSize = (int) squareRoot;
					} else {
						System.out.println("Number is not a perfect square");
						checkingFileError = true;
					}
					
					// Creating the arraylist of the puzzle & setting all values in the puzzle to -1
					for(int row = 0; row < puzzleLengthSize; ++row) {
						sudokuPuzzle.add(new ArrayList<Integer>(puzzleLengthSize));
						
						for(int col = 0; col < puzzleLengthSize; ++col) {
							sudokuPuzzle.get(row).add(-1);
						}
					}
    			} else if(lineNum == 2 && tokens.length == puzzleLengthSize) { // Gets the sudoku puzzle numbers    			
					for(int i = 0; i < tokens.length; ++i) {
						int number = Integer.parseInt(tokens[i]);
						
						//Adds the numbers in order
						if(i == 0) { // if it is the first number to be added
							sudokuNumbersList.add(number);
						} else {  // if it is not the first number to be added
							// boolean used to terminate for loop when the number has been added
							boolean placeFound = false; 
							
							for(int j = 0; !placeFound && j < sudokuNumbersList.size(); ++j) {
								if(number <= sudokuNumbersList.get(j)) {
									sudokuNumbersList.add(j, number);
									placeFound = true;
								}
							}
							
							// If the number has not been added, i.e. the added number is the biggest number
							if(!placeFound) {
								sudokuNumbersList.add(sudokuNumbersList.size(), number);
							}
						}
					}
				} else if(lineNum == 3 && tokens.length == 1) { // Gets the number of cages
					numberOfCages = Integer.parseInt(tokens[0]);
				} else if(lineNum >= 4) {
					int numberOfTokens = tokens.length;
					
					int cageSum = Integer.parseInt(tokens[0]);
					ArrayList<Integer> rowCoordinates = new ArrayList<Integer>();
					ArrayList<Integer> colCoordinates = new ArrayList<Integer>();
					
					for(int i = 1; i < tokens.length; ++i) {
						String[] position = tokens[i].split(",");
						
						int row = Integer.parseInt(position[0]);
						int column = Integer.parseInt(position[1]);
						
						if(row >= puzzleLengthSize || column >= puzzleLengthSize) {
							// Row or column is bigger than puzzle size, index out of bounce
							System.out.println("File error. The row or col is out of puzzle size.");
							System.out.println("Line: " + lineNum + " has error.");
							
							checkingFileError = true;
						} else {
							rowCoordinates.add(row);
							colCoordinates.add(column);
						}
					}
					
					KillerSudokuCage cage = new KillerSudokuCage(cageSum, rowCoordinates, colCoordinates);
					
					killerSudokuCage.add(cage);
				} else {
					// File error
					System.out.println("File error. There is an error in the file.");
					System.out.println("Line: " + lineNum + " has error.");
					
					checkingFileError = true;
				}
    		}
    		
    		lineNum = lineNum + 1; //lineNum++
    	}
    }


    @Override
    public void outputGrid(String filename)
        throws FileNotFoundException, IOException
    {
        // TODO
    	PrintWriter outWriter = new PrintWriter(new FileWriter(filename), true);
    	outWriter.println(toString());
    	outWriter.close();
    } // end of outputBoard()


    @Override
    public String toString() {
        // TODO
    	String statement = "";
    	
    	for(int row = 0; row < puzzleLengthSize; ++row) {
    		for(int col = 0; col < puzzleLengthSize; ++col) {
    			String numberInString = String.valueOf(sudokuPuzzle.get(row).get(col));
    			statement = statement + numberInString + ",";
    		}
    		
    		// Checks whether to enter a new line. If its the last iteration, do not enter a new line
    		if(row == puzzleLengthSize - 1) {
    			statement = statement.substring(0, statement.length() - 1);
    		} else {
    			statement = statement.substring(0, statement.length() - 1) + "\n";
    		}
    	}

        // placeholder
    	// return String.valueOf("");
    	return statement;
    } // end of toString()


    @Override
    public boolean validate() {
        // TODO
    	boolean valid = false;
    	
    	boolean check1 = checkSudokuNumbersList();
    	
    	if(check1) {
        	boolean check2 = checkPuzzleRow();
        	boolean check3 = checkPuzzleColumn();
        	boolean check4 = checkPuzzleBox();
        	
        	if(check2 && check3 && check4) {
        		valid = true;
        	}
    	}
    	
        // placeholder
        return valid;
    } // end of validate()
    
//	 Checks if there is any duplicates in the number list, i.e. line 2 of input file
//  private boolean checkSudokuNumbersList() {
  public boolean checkSudokuNumbersList() {
	  boolean inverseboolChecker = true;
  	
  	for(int i = 0; inverseboolChecker && i < sudokuNumbersList.size() - 1; ++i) {
  		int currentNumber = sudokuNumbersList.get(i);
  		int nextNumber = sudokuNumbersList.get(i + 1);
  		
  		if(currentNumber == nextNumber) {
  			inverseboolChecker = false;
  		}
  	}
  	
  	return inverseboolChecker;
  }

//	private boolean checkPuzzleRow() {
	public boolean checkPuzzleRow() {
		boolean boolChecker = false;
		boolean errorInRow = false;
		
  	for(int row = 0; !errorInRow && row < puzzleLengthSize; ++row) {
  		ArrayList<Integer> tempList = new ArrayList<Integer>(sudokuNumbersList);
  		
  		for(int col = 0; !errorInRow && col < puzzleLengthSize; ++col) {
  			int number = sudokuPuzzle.get(row).get(col);
  			if(number != -1) {
  				boolean numberFound = false;
  				
  				for(int i = 0; !numberFound && i < tempList.size(); ++i) {
  					if(number == tempList.get(i)) {
  						tempList.remove(i);
  						numberFound = true;
  					}
  				}
  				/*
  				 *  If the number is not found, the number in the puzzle does not exist in the current list,
  				 *  i.e. it is a duplicated number or number does not exist
  				 */
  				if(!numberFound) {
  					errorInRow = true;
  				}
  			}
  		}
  		
  		// If there is no error in any rows, return true
  		if(!errorInRow) {
  			boolChecker = true;
  		} else {
  			boolChecker = false;
  		}
  	}
		
		return boolChecker;
	}

//	private boolean checkPuzzleColumn() {
	public boolean checkPuzzleColumn() {
		boolean boolChecker = false;
		boolean errorInCol = false;
  	
  	for(int col = 0; !errorInCol && col < puzzleLengthSize; ++col) {
  		ArrayList<Integer> tempList = new ArrayList<Integer>(sudokuNumbersList);
  		for(int row = 0; !errorInCol && row < puzzleLengthSize; ++row) {
  			int number = sudokuPuzzle.get(row).get(col);
  			if(number != -1) {
  				boolean numberFound = false;
//  				System.out.println("Row: " + row + " Col: " + col +" (" + number + ")");
  				for(int i = 0; !numberFound && i < tempList.size(); ++i) {
  					if(number == tempList.get(i)) {
  						tempList.remove(i);
  						numberFound = true;
  					}
  				}
  				/*
  				 *  If the number is not found, the number in the puzzle does not exist in the current list,
  				 *  i.e. it is a duplicated number or number does not exist
  				 */
  				if(!numberFound) {
  					errorInCol = true;
  				}
  			}
  		}
  		// If there is no error in any cols, return true
  		if(!errorInCol) {
  			boolChecker = true;
  		} else {
  			boolChecker = false;
  		}
  	}
		
		return boolChecker;
	}

//  private boolean checkPuzzleBox() {
	public boolean checkPuzzleBox() {
  	boolean boolChecker = false;
		boolean errorInBox = false;
		
		int row = 0;
		int col = 0;
  	int rowStopIndex = boxSize;
  	int colStopIndex = boxSize;
  	
  	while(!errorInBox && rowStopIndex <= puzzleLengthSize) {
//  		System.out.println("Hello" + row + col);
  		ArrayList<Integer> tempList = new ArrayList<Integer>(sudokuNumbersList);
  		
  		for(int x = row; !errorInBox && x < rowStopIndex; ++x) {  			
         		for(int y = col; !errorInBox && y < colStopIndex; ++y) {
      			int number = sudokuPuzzle.get(x).get(y);
      			
//      			System.out.println("Row: " + x + " Col: " + y +" (" + number + ")");
      			
      			if(number != -1) {
      				boolean numberFound = false;
      				
      				for(int i = 0; !numberFound && i < tempList.size(); ++i) {
      					if(number == tempList.get(i)) {
      						tempList.remove(i);
      						numberFound = true;
      					}
      				}
      				
      				/*
      				 *  If the number is not found, the number in the puzzle does not exist in the current list,
      				 *  i.e. it is a duplicated number or number does not exist
      				 */
      				if(!numberFound) {
      					errorInBox = true;
      				}
      			}
      		}
      	}
  		
  		// if col is at the end, continue to the next box row and reset col values
  		if(colStopIndex == puzzleLengthSize) {
  			row = row + boxSize;
  			rowStopIndex = rowStopIndex + boxSize;
  			col = 0;
      		colStopIndex = boxSize;
  		} else { // if col is not at the end, continue to the next box col
  			col = col + boxSize;
      		colStopIndex = colStopIndex + boxSize;
  		}
  	}
  	
		// If there is no error in any boxes, return true
		if(!errorInBox) {
			boolChecker = true;
		} else {
			boolChecker = false;
		}
  	 	
		return boolChecker;
	}
	
//	private boolean checkCage() {
	public boolean checkCage(int cellValue, int row, int col) {
		boolean boolChecker = false;
		boolean cageFound = false;
		
		KillerSudokuCage currentCage = null;
		
		for(KillerSudokuCage cage : killerSudokuCage) {			
			for(int i = 0; !cageFound && i < cage.getListSize(); ++i) {
				if(row == cage.getRowCoordinates(i) &&
						col == cage.getColCoordinates(i)) {
					cageFound = true;
					currentCage = cage;
				}
			}
		}
		
		if(cageFound) {
			currentCage.addCurrentSum(cellValue);
			
			// If the value added is less than the sum, but the size is less than the cage size
			// Valid
			if(currentCage.getCurrentSum() < currentCage.getCageSum() &&
					currentCage.getCurrentSize() < currentCage.getListSize()) {
				boolChecker = true;
			} 
			// If the value added is the same as the sum, AND the size is the same as the cage size
			// Valid
			else if(currentCage.getCurrentSum() == currentCage.getCageSum() &&
					currentCage.getCurrentSize() == currentCage.getListSize()) {
				boolChecker = true;
			} 
		}
		
		return boolChecker;
	}
	
	public int checkCageSize(int row, int col) {
		KillerSudokuCage currentCage = getCage(row, col);
		
		return currentCage.getCurrentSize();
	}
	
	public void subtractCage(int cellValue, int row, int col) {
		KillerSudokuCage currentCage = getCage(row, col);
	
		currentCage.subtractCurrentSum(cellValue);
	}

	@Override
	public ArrayList<Integer> getSudokuNumbersList() {
		return sudokuNumbersList;
	}
	
	@Override
	public ArrayList<ArrayList<Integer>> getSudokuPuzzle() {
		return sudokuPuzzle;
	}
	
	@Override
	public int getPuzzleLengthSize() {
		return puzzleLengthSize;
	}
	
	public int getNumberOfCages() {
		return numberOfCages;
	}
	
	public void setCellValue(int cellValue, int row, int col) {
		sudokuPuzzle.get(row).set(col, cellValue);
	}
	
	// Methods to be removed
	public String getKillerSudokuCageToString() {
		String statement = "";
		
		for(KillerSudokuCage cage : killerSudokuCage) {
			statement = statement + cage.toString() + "\n";
		}
		
		return statement;
	}


	@Override
	public String getSudokuNumbersListInString() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
    /********************************************
     * Methods for Killer Sudoku Advanced Solver*
     ********************************************/

	@Override
	public boolean currentSizeVSCageSize(int row, int col) {
		boolean minusOneDiff = false;
		KillerSudokuCage currentCage = getCage(row, col);
		
		int numberToBeAdded = currentCage.getLastCellValue();
		
		if(currentCage.getCurrentSize() == currentCage.getListSize() - 1) {
			for(int i = 0; !minusOneDiff && i < sudokuNumbersList.size(); ++i) {
				if(numberToBeAdded == sudokuNumbersList.get(i)) {
					minusOneDiff = true;
				}
			}
		}
		
		return minusOneDiff;
	}


	@Override
	public int getLastCageRow(int row, int col) {
		KillerSudokuCage currentCage = getCage(row, col);
		
		return currentCage.getLastRowValue();
	}


	@Override
	public int getLastCageCol(int row, int col) {
		KillerSudokuCage currentCage = getCage(row, col);
		
		return currentCage.getLastColValue();
	}


	@Override
	public int getLastCageCellValue(int row, int col) {	
		KillerSudokuCage currentCage = getCage(row, col);
		
		return currentCage.getLastCellValue();
	}
	
	public boolean checkIfCellHasValue(int row, int col) {
		boolean boolChecker = false;
		
		if(sudokuPuzzle.get(row).get(col) != -1) {
			boolChecker = true;
		}
		
		return boolChecker;
	}
	
	private KillerSudokuCage getCage(int row, int col) {
		boolean cageFound = false;
		KillerSudokuCage currentCage = null;
		
		for(KillerSudokuCage cage : killerSudokuCage) {			
			for(int i = 0; !cageFound && i < cage.getListSize(); ++i) {
				if(row == cage.getRowCoordinates(i) &&
						col == cage.getColCoordinates(i)) {
					cageFound = true;
					currentCage = cage;
				}
			}
		}
		
		return currentCage;
	}
} // end of class KillerSudokuGrid
