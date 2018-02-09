package game.brain;

import java.util.List;

public class Node implements Comparable<Node>{	
	private int x, y;
	private int f, g, h;
	private Node parent;
	public List<Node> adjacent;
	@SuppressWarnings("unused")
	private int row, col;
	private boolean isVisited;
	
	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	@Override
	public int compareTo(Node that) {
		return this.getF() - that.getF();
	}
	
	public boolean equals(Node that) {
		if (this.getX() == that.getX() && this.getY() == that.getY()) {
			return true;
		}
		return false;
	}
	
	public int getRow() {
		return y;
	}
	
	public int getCol() {
		return x;
	}
	
	public String toString() {
		String s = "Parent: ( null )";
		if(this.parent != null) {
			s = "Parent: ( " + this.parent.x + ", " + this.parent.y + " )"; 
		}
//		String v = "Visited: ( false )";
//		if(this.isVisited == true) {
//			v = "Visited: ( " + this.isVisited + " )"; 
//		}
		return "Node x: " + x + " y: " + y + " "+ s;
	}
	
}
