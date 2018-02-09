package game.brain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStarPathfinder {
	private int[][] searchArea;
	public List<Node> path;

	public AStarPathfinder(int[][] map) {
		this.searchArea = map;
	}

	public void searchPath(Node start, Node goal) {
		Queue<Node> open = new PriorityQueue<Node>();
		List<Node> close = new ArrayList<Node>();
		path = new LinkedList<Node>();		
		
		start.setG(0);
		start.setH(calculateDistance(start, goal));
		start.setF(start.getH() + start.getG());
		start.setParent(null);
		
		open.add(start);
		
		if (start.equals(goal)) {
			System.out.println("Start and Goal is equal.");			
			return;
		}
		
		while (!open.isEmpty()) {
			Node current = null;
			current = open.peek();			
			if (open.size() == 0) {
				System.out.println("No possible route.");
			}
			if (current.equals(goal)) {
//				System.out.println("Reconstruct path from goal.");
				if (!path.contains(current)) {
					path.add(current);
				}
				
				List<Node> nodes = new ArrayList<Node>();
				while(current.getParent() != null) {
					nodes.add(current);
					current = current.getParent();
				}
				nodes.add(start);
				path = nodes;
				nodes = null;
				
				break;
			}
			close.add(open.poll());
			current.adjacent = getAdjacentOrtho(current);
			
			for (Node n: current.adjacent) {				
				int tentativeG = current.getG() + getGBetween(n, current);
				
				if (close.contains(n) && tentativeG >= n.getG()) {					
					continue;
				}
				
				if (!close.contains(n) || tentativeG < n.getG()) {					
					n.setG(getGBetween(n, current));
					n.setH(calculateDistance(n, goal));
					n.setF(n.getG() + n.getH());
					n.setParent(current);					
					if (!open.contains(n)) {
						open.add(n);
					}
					if (!path.contains(current)) {						
						path.add(current);
					}
					close.add(current);
				}				
			}			
		}
		
	}
		
	private List<Node> getAdjacentOrtho(Node n) {
		int x = n.getX();
		int y = n.getY();
		List<Node> adj = new ArrayList<Node>();
		if (y - 1 >=  0 && isWalkable(y - 1, x) == true) {
			adj.add(new Node(x, y - 1));
//			System.out.println("N");
		}
		if (x - 1 >= 0 && isWalkable(y, x - 1) == true) {
			adj.add(new Node(x - 1, y));
//			System.out.println("W");
		}
		if (x + 1 < searchArea[0].length && isWalkable(y, x + 1) == true) {
			adj.add(new Node(x + 1, y));
//			System.out.println("E");
		}		
		if (y + 1 < searchArea.length && isWalkable(y + 1, x) == true) {
			adj.add(new Node(x,y+1));
//			System.out.println("S");			
		}
		return adj;
	}

	@SuppressWarnings("unused")
	private List<Node> getAdjacent(Node n) {
		int x = n.getX();
		int y = n.getY();
		List<Node> adj = new ArrayList<Node>();
		if (y - 1 >=  0) {				
			if (x - 1 >= 0) {
				adj.add(new Node(x - 1, y - 1));
//				System.out.println("NW");
			}
			adj.add(new Node(x, y - 1));
//			System.out.println("N");
			if (x + 1 < searchArea[0].length) {
				adj.add(new Node(x + 1, y - 1));
//				System.out.println("NE");
			}
		}
		if (x - 1 >= 0) {
			adj.add(new Node(x - 1, y));
//			System.out.println("W");
		}
		if (x + 1 < searchArea[0].length) {
			adj.add(new Node(x + 1, y));
//			System.out.println("E");
		}
		
		if (y + 1 < searchArea.length) {			
			if (x-1 >= 0) {
				adj.add(new Node(x - 1, y + 1));
//				System.out.println("SW"); 
			}
			adj.add(new Node(x,y+1));
//				System.out.println("S");
			if (x + 1 < searchArea[0].length) {
				adj.add(new Node(x + 1, y + 1));
//				System.out.println("SE");
			}
		}
		return adj;
	}
	
	public boolean isWalkable(int x, int y) {
		return getValueAt(x,y) == 0 ? true:false;
	}
	
	public boolean isWalkable(Node n) {
		return getValueAt(n.getRow(), n.getCol()) == 0 ? true:false;
	}
	
	public int getGBetween(Node node1, Node node2) { // getG
		// horizontal / vertical
		if (node1.getX() == node2.getX() || node1.getY() == node2.getY()) {
			return Math.abs(10 * (node2.getX() - node1.getX())
					+ (10 * (node2.getY() - node1.getY())));
		} else { // if they are diagonal to each other return diagonal distance:
			return 14;
		}
	}

	private int calculateDistance(Node start, Node goal) {
		return (10 * Math.abs(goal.getX() - start.getX()) + (10 * Math.abs(goal
				.getY() - start.getY())));
	}
	
	public void printPath() {		
		for(Node n : path) {
			System.out.println(n);
		}
		System.out.println(path.size());
	}
	
	public int getValueAt(int row, int col) {
		return searchArea[row][col];
	}
	
	public void displayAdjacent(Node n) {
		if (n.adjacent == null) {
			System.out.println("get adjacent first or no adjacent at all..");
		} else {
			System.out.println(n);
			System.out.println("No. of neigbors: " + n.adjacent.size());
			for (Node neighbor: n.adjacent) {
				System.out.println(neighbor);
			}
		}
	}
	
	public List<Node> reversePath() {
		Collections.reverse(path);
		return path;
	}

	public static void main(String[] args) {
		int[][] map = { 
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0 } };
		
		Node goal = new Node(0,0);
		Node start = new Node(6,4);
		
		System.out.println("Start " + start);
		System.out.println("Goal " + goal);
		
		AStarPathfinder pathfinder = new AStarPathfinder(map);
		long startTime = System.currentTimeMillis();
		pathfinder.searchPath(start, goal);
		long endTime = System.currentTimeMillis();		
		System.out.println("Search duration: " + (endTime - startTime) + " ms");
		pathfinder.printPath();
		pathfinder.reversePath();
		pathfinder.printPath();
	}

}
