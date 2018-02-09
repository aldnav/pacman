package game;

import java.awt.Point;

public class Food {
	Point point;
	boolean eaten;
	int x, y;
	
	public boolean isEaten() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		this.eaten = eaten;
	}

	public Food(int x, int y) {
		this.x = x;
		this.y = y;
		managePosition(x, y);
	}
	
	private void managePosition(int x1, int y1){
		this.point = new Point((x1 * 20) + GameClass.topX + 5,
				               (y1 * 20) + GameClass.topY + 5);
	}
	
}
