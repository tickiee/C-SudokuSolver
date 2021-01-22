package solver;

public class Node {
	protected Node left;
	protected Node right;
	protected Node up;
	protected Node down;
	
	protected ColumnNode colNode;
	
	public Node() {
		left = this;
		right = this;
		up = this;
		down = this;
	}
	
	public Node(ColumnNode colNode) {
		this();
		
		this.colNode = colNode;
	}
	
	/*
	 * Methods used to link and unlink nodes
	 */
	protected Node linkDown(Node node) {
		node.down = this.down;
		node.down.up = node;
		node.up = this;
		this.down = node;
		
		return node;
	}
	
	protected Node linkRight(Node node) {
		node.right = this.right;
		node.right.left = node;
		node.left = this;
		this.right = node;
		
		return node;
	}
	
	protected void unlinkLeftRight() {
		this.right.left = this.left;
		this.left.right = this.right;
	}
	
	protected void relinkLeftRight() {
		this.right.left = this;
		this.left.right = this;
	}
	
	protected void unlinkUpDown() {
		this.up.down = this.down;
		this.down.up = this.up;
	}
	
	protected void relinkUpDown() {
		this.down.up = this;
		this.up.down = this;
	}
	
	/*
	 * Getters
	 */
	public Node getUpNode() {
		return up;
	}
	
	public Node getDownNode() {
		return down;
	}
	
	public Node getLeftNode() {
		return left;
	}
	
	public Node getRightNode() {
		return right;
	}
	
	public ColumnNode getColumnNode() {
		return colNode;
	}
	
	public void cover() {
		
	};
	
	public void uncover() {
		
	};
}
