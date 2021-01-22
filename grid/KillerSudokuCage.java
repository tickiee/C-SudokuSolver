package grid;
import java.util.ArrayList;

public class KillerSudokuCage {

	private int cageSum;
	private ArrayList<Integer> rowCoordinates;
	private ArrayList<Integer> colCoordinates;
	private int listSize;
	
	private int currentSum;
	private int currentSize;
	
	public KillerSudokuCage(int cageSum, ArrayList<Integer> rowCoordinates, ArrayList<Integer> colCoordinates) {
		this.cageSum = cageSum;
		this.rowCoordinates = rowCoordinates;
		this.colCoordinates = colCoordinates;
		this.listSize = rowCoordinates.size();
		
		this.currentSum = 0;
		this.currentSize = 0;
	}
	
	public void setCageSum(int sum) {
		this.cageSum = sum;
	}
	
	public int getCageSum() {
		return cageSum;
	}
	
	public int getListSize() {
		return listSize;
	}
	
	public int getCurrentSize() {
		return currentSize;
	}
	
	public int getCurrentSum() {
		return currentSum;
	}
	
	public void addCurrentSum(int addToSum) {
		currentSum = currentSum + addToSum;
		currentSize = currentSize + 1;
	}
	
	public void subtractCurrentSum(int subToSum) {
		currentSum = currentSum - subToSum;
		currentSize = currentSize - 1;
	}
	
	public int getRowCoordinates(int index) {
		return rowCoordinates.get(index);
	}
	
	public int getColCoordinates(int index) {
		return colCoordinates.get(index);
	}
	
	public String toString() {
		String statement = "";
		statement = statement + "Cage Sum: " + cageSum + "\n";
		
		for(int i = 0; i < rowCoordinates.size(); ++i) {
			String line = "Row: " + rowCoordinates.get(i) + " Col: " + colCoordinates.get(i);
			statement = statement + line + "\n";
		}
	
		return statement;
	}
	
	/********************************************
     * Methods for Killer Sudoku Advanced Solver*
     ********************************************/
	
	public int getLastRowValue() {
		return rowCoordinates.get(listSize - 1);
	}
	
	public int getLastColValue() {
		return colCoordinates.get(listSize - 1);
	}
	
	public int getLastCellValue() {
		return cageSum - currentSum;
	}
}
