package game;

import java.awt.Point;

public class Pacman {
	Point point;
	int x;
	int y;
	int pointX;
	int pointY;
	int direction; // U, D, L, R
	int moveFactor = 1;
	int defaultX = 1;
	int defaultY = 1;

	public Pacman() {
		this.x = defaultX;
		this.y = defaultY;
		computeGraphicPoint();
		this.point = new Point(pointX, pointY);
	}

	public Pacman(int x, int y) {
		this.x = x;
		this.y = y;
		computeGraphicPoint();
		this.point = new Point(pointX, pointY);
	}

	public void computeGraphicPoint() {
		this.pointX = (this.x * GameClass.sizeFactor) + GameClass.topX;
		this.pointY = (this.y * GameClass.sizeFactor) + GameClass.topY;
	}

	public void update() {
		computeGraphicPoint();
		this.point.x = pointX;
		this.point.y = pointY;

	}

	public void moveUp() {
		if (this.y > 1 && GameClass.map[this.y-1][this.x] == 0) {
			this.y--;
		}
	}

	public void moveDown() {
		if (this.y < 13 && GameClass.map[this.y+1][this.x] == 0) {
			this.y++;
		}
	}

	public void moveLeft() {
		if (this.x > 1 && GameClass.map[this.y][this.x-1] == 0) {
			this.x--;
		}
	}

	public void moveRight() {
		if (this.x < 13 && GameClass.map[this.y][this.x+1] == 0) {
			this.x++;
		}
	}

	public void setDirection(int n) {
		this.direction = n;
	}

	public void move() {
		if (direction == 1) {
			moveUp();
		} else if (direction == 2) {
			moveDown();
		} else if (direction == 3) {
			moveLeft();
		} else if (direction == 4) {
			moveRight();
		}
//		System.out.println("Pacman : " + this.x + "," + this.y);
	}
	
	public void setX(int x1) {
		this.x = x1;
	}
	
	public void setY(int y1) {
		this.y = y1;
	}
	
	public void resetPosition() {
		this.x = defaultX;
		this.y = defaultY;
		computeGraphicPoint();
		this.point = new Point(pointX, pointY);
	}

}
