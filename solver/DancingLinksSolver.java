/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;
import java.util.ArrayList;
import java.util.LinkedList;

import grid.*;

/**
 * Dancing links solver for standard Sudoku.
 */
public class DancingLinksSolver extends StdSudokuSolver 
{
	// TODO: Add attributes as needed.
	private ColumnNode headerNode;
	private ArrayList<Node> solutionList;
	private LinkedList<Node> dancingLinksMatrix;
	
	public DancingLinksSolver() {
		headerNode = null;
		solutionList = new ArrayList<Node>();
		dancingLinksMatrix = new LinkedList<Node>();
	} // end of DancingLinksSolver()
	
	@Override
	public boolean solve(SudokuGrid grid) {
		// TODO: your implementation of the dancing links solver for Killer Sudoku.
		boolean solveable = false;
		
		int index = 0;
		
		createMatrix();
		solveable = findSolution(index);
		
		// placeholder
		return solveable;
	} // end of solve()
	
	private void createMatrix() {
		// Sorry. Couldnt implement
	}
	
	private boolean findSolution(int index) {
		boolean found = false;
		
		if(headerNode.getRightNode() == headerNode) {
			// Solution found
			found = true;
			// Solve the grid with solution
		} else {
			ColumnNode currColNode = selectColumn();
			currColNode.cover();
			
			for(Node node = currColNode.getDownNode(); node != currColNode; node = node.getDownNode()) {
				solutionList.add(node);
				
				for(Node node2 = node.getRightNode(); node2 != node; node2 = node2.getRightNode()) {
					node2.getColumnNode().cover();
				}
				
				found = findSolution(index + 1);
				node = solutionList.remove(solutionList.size() - 1);
				currColNode = node.getColumnNode();
				
				for(Node nodeX = node.getLeftNode(); nodeX != node; nodeX = nodeX.getLeftNode()) {
					nodeX.getColumnNode().uncover();
				}
			}
			
			currColNode.uncover();
		}
		
		return found;
	}
	
	private ColumnNode selectColumn() {
		ColumnNode currColNode = null;
		int min = Integer.MAX_VALUE;
		
		for(ColumnNode colNode = (ColumnNode) headerNode.getRightNode(); colNode != headerNode; colNode = (ColumnNode) colNode.getRightNode()) {
			if(colNode.getColSize() < min) {
				min = colNode.getColSize();
				currColNode = colNode;
			}
		}
		
		return currColNode;
	}
} // end of class DancingLinksSolver
