package solver;

public class ColumnNode extends Node {
	private int colSize;
	private String colName;
	
	public ColumnNode(String colName) {
		super();
		this.colSize = 0;
		this.colName = colName;
		this.colNode = this;
	}
	
	@Override
	public void cover() {
		unlinkLeftRight();
		
		for(Node node = this.down; node != this; node = node.down) {
			for(Node node2 = node.right; node2 != node; node2 = node2.right) {
				node2.unlinkUpDown();
				node2.colNode.colSize = node2.colNode.colSize - 1;			
			}
		}
	}
	
	@Override
	public void uncover() {
		for(Node node = this.up; node != this; node = node.up) {
			for(Node node2 = node.left; node2 != node; node2 = node2.left) {
				node2.colNode.colSize = node2.colNode.colSize + 1;
				node2.relinkUpDown();
			}
		}
		
		relinkLeftRight();
	}
	
	public int getColSize() {
		return colSize;
	}
}
