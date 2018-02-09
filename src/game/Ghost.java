package game;

import game.brain.Node;

import java.awt.Point;
import java.util.List;

public class Ghost extends Pacman {

	int x = 13;
	int y = 13;
	int defaultX = 13;
	int defaultY = 13;

	List<Node> moves;

	public Ghost() {
		super.x = this.defaultX;
		super.y = this.defaultY;
		super.computeGraphicPoint();
		super.point = new Point(super.pointX, super.pointY);
	}

	public Ghost(int x1, int y1) {
		super.x = x1;
		super.y = y1;
		super.computeGraphicPoint();
		super.point = new Point(super.pointX, super.pointY);
	}
	
	@Override
	public void move() {
		if (!moves.isEmpty()) {
			int moveX = moves.get(0).getX();
			int moveY = moves.get(0).getY();

			determineDirection(moveX, moveY);
			this.x = moveX;
			this.y = moveY;
			super.x = this.x;
			super.y = this.y;
//			System.out.println("Direction: " + super.direction);
			moves.remove(0);
		}
//		System.out.println("Ghost : " + this.x + "," + this.y);
	}

	private void determineDirection(int x1, int y1) {
		if (y1 < this.y) {
			super.setDirection(1);
//			System.out.println("UP");
		} else if (y1 > this.y) {
			super.setDirection(2);
//			System.out.println("DOWN");
		}
		if (x1 < this.x) {
			super.setDirection(3);
//			System.out.println("LEFT");
		} else if (x1 > this.x) {
			super.setDirection(4);
//			System.out.println("RIGHT");
		}
	}

}
