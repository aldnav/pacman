package game;

import game.brain.AStarPathfinder;
import game.brain.Node;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameClass extends Applet implements Runnable, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4155821990181103471L;
	public static long fps = 51;
	private int res_length = 480;
	private int res_width = 800;
	private String gameTitle = "Pacman";
	public static int topX = 90;
	public static int topY = 250;
	public static int sizeFactor = 20;

	public int rows = 15;
	public int cols = 15;

	static int BOUNDARY_TOP = topY + sizeFactor;
	static int BOUNDARY_BOTTOM = BOUNDARY_TOP + (sizeFactor * 15);
	static int BOUNDARY_LEFT = topX + sizeFactor;
	static int BOUNDARY_RIGHT = BOUNDARY_LEFT + (sizeFactor * 15);

	private Image image, background, pacmanImage, pacmanUp1, pacmanDown1,
			pacmanLeft1, pacmanRight1, pacmanUp2, pacmanDown2, pacmanLeft2,
			pacmanRight2, pacmanClose, ghost1Image, ghost1Up, ghost1Down,
			ghost1Left, ghost1Right, ghost1Dead, ghost1Dead2, banner, footer;
	private Graphics second;
	private URL base;
	static AudioClip begin, chomp, death, winning, pause;

	private Pacman player;
	private Ghost ghost1;
	static int[][] map1 = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 0
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1 }, // 5
			{ 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1 },
			{ 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0 },
			{ 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1 },
			{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 }, // 10
			{ 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1 },
			{ 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	static int[][] map = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // 0
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, // 5
			{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 }, // 10
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	AStarPathfinder pf;
	Node start;
	Node goal;
	List<Food> foods;
	private boolean play = false;
	private boolean gameOver = false;
	private boolean win = false;
	private int lives = 3;
	private String gameOverString = "Game Over!";
	private int score = 0;
	private String scoreString = "SCORE ";
	private int animCount = 0;
	private int animGhostCount = 0;

	@Override
	public void init() {
		setSize(res_length, res_width);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle(gameTitle);
		try {
			base = getDocumentBase();
		} catch (Exception e) {

		}

		begin = getAudioClip(base, "data/sound/pacman_beginning.au");
		chomp = getAudioClip(base, "data/sound/pacman_chomp.au");
		death = getAudioClip(base, "data/sound/pacman_death.au");
		winning = getAudioClip(base, "data/sound/pacman_intemission.au");
		pause = getAudioClip(base, "data/sound/pacman_eatfruit.au");

		begin.play();

		pacmanClose = getImage(base, "data/images/sliced_01.png");
		pacmanUp1 = getImage(base, "data/images/sliced_02.png");
		pacmanDown1 = getImage(base, "data/images/sliced_36.png");
		pacmanLeft1 = getImage(base, "data/images/sliced_53.png");
		pacmanRight1 = getImage(base, "data/images/sliced_19.png");
		pacmanUp2 = getImage(base, "data/images/sliced_03.png");
		pacmanDown2 = getImage(base, "data/images/sliced_37.png");
		pacmanLeft2 = getImage(base, "data/images/sliced_54.png");
		pacmanRight2 = getImage(base, "data/images/sliced_20.png");
		pacmanImage = pacmanClose;

		ghost1Up = getImage(base, "data/images/sliced_05.png");
		ghost1Down = getImage(base, "data/images/sliced_39.png");
		ghost1Left = getImage(base, "data/images/sliced_56.png");
		ghost1Right = getImage(base, "data/images/sliced_23.png");
		ghost1Dead = getImage(base, "data/images/sliced_65.png");
		ghost1Dead2 = getImage(base, "data/images/sliced_67.png");
		ghost1Image = ghost1Left;

		banner = getImage(base, "data/images/banner.png");
		footer = getImage(base, "data/images/footer.png");
		background = getImage(base, "data/images/map2_rev2.png");
		player = new Pacman();
		ghost1 = new Ghost();

		start = new Node(ghost1.x, ghost1.y);
		goal = new Node(player.x, player.y);
		pf = new AStarPathfinder(map);
		pf.searchPath(start, goal);
		ghost1.moves = pf.reversePath();
		// pf.printPath();
		foods = manageFood(map);
	}

	@Override
	public void start() {
		super.start();

		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void run() {
		while (true) {
			checkCollision();
			if (gameOver == false && play == true) {
				if (animCount < 12) {
					animCount++;
				} else if (animCount == 12) {
					animCount = 0;
				}
				checkEat();
				determinePlayerFace();
				player.update();
				player.move();
				checkCollision();
				determineGhost1Face();
				ghost1.update();
				ghost1.move();
			}
			if (gameOver == true && win == true) {
				if (animCount < 12) {
					animCount++;
				} else if (animCount == 12) {
					animCount = 0;
				}				
				if (animGhostCount == 0) {
					ghost1Image = ghost1Dead;
					animGhostCount = 1;
				} else {
					ghost1Image = ghost1Dead2;
					animGhostCount = 0;
				}
				determinePlayerFace();
				player.update();
				player.move();
			}
			
			repaint();
			try {
				Thread.sleep(fps);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(banner, 0, 0, this);
		g.drawImage(background, 90, 250, this);
		g.drawImage(footer, 90, 550, this);
		displayFood(g);
		g.drawImage(pacmanImage, player.point.x, player.point.y, this);
		g.drawImage(ghost1Image, ghost1.point.x, ghost1.point.y, this);
		g.setColor(Color.white);
		g.drawString("LIVES ", 90, 230);
		g.drawString(scoreString + score, 320, 230);
		g.setColor(Color.yellow);
		for (int i = 0; i < lives; i++) {
			g.fillOval(90 + (i * 20), 230, 10, 10);
		}
		if (gameOver == true && win == false) {
			g.setColor(Color.green);
			g.drawString(gameOverString, 200, 230);
		}
		if (gameOver == true && win == true) {
			g.setColor(Color.green);
			g.drawString("   YOU WIN! ", 190, 230);
			g.drawString(" [R] - Restart ", 190, 240);
		}
		if (!play && !gameOver) {
			g.setColor(Color.green);
			g.drawString(" ARE YOU READY? ", 190, 230);
			g.drawString(" [Space] - Resume ", 190, 240);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_UP && play == true) {
			player.setDirection(1);
			player.moveUp();
			chomp.play();
		} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN && play == true) {
			player.setDirection(2);
			player.moveDown();
			chomp.play();
		} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT && play == true) {
			player.setDirection(3);
			player.moveLeft();
			chomp.play();
		} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT && play == true) {
			player.setDirection(4);
			player.moveRight();
			chomp.play();
		} else if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			pause.play();
			if (play == false) {
				play = true;
			} else if (play == true) {
				play = false;
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_R) {
			repaint();
			begin.play();
			replay();
			play = false;
			repaint();
		}
	}

	private void determinePlayerFace() {
		if (animCount == 0) {
			pacmanImage = pacmanClose;
		}
		if (player.direction == 1) {
			if (animCount == 4) {
				pacmanImage = pacmanUp1;
			} else if (animCount == 8) {
				pacmanImage = pacmanUp2;
			}
		} else if (player.direction == 2) {
			if (animCount == 4) {
				pacmanImage = pacmanDown1;
			} else if (animCount == 8) {
				pacmanImage = pacmanDown2;
			}
		} else if (player.direction == 3) {
			if (animCount == 4) {
				pacmanImage = pacmanLeft1;
			} else if (animCount == 8) {
				pacmanImage = pacmanLeft2;
			}
		} else if (player.direction == 4) {
			if (animCount == 4) {
				pacmanImage = pacmanRight1;
			} else if (animCount == 8) {
				pacmanImage = pacmanRight2;
			}
		}
	}

	private void determineGhost1Face() {
		if (ghost1.direction == 1) {
			ghost1Image = ghost1Up;
		} else if (ghost1.direction == 2) {
			ghost1Image = ghost1Down;
		} else if (ghost1.direction == 3) {
			ghost1Image = ghost1Left;
		} else if (ghost1.direction == 4) {
			ghost1Image = ghost1Right;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		// ghost1.moves = null;
		refreshStart();
		refreshGoal();
		pf.searchPath(start, goal);
		ghost1.moves = pf.reversePath();
		// pf.printPath();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	private void refreshStart() {
		start.setX(ghost1.x);
		start.setY(ghost1.y);
	}

	private void refreshGoal() {
		goal.setX(player.x);
		goal.setY(player.y);
	}

	private void resetStart() {
		start.setX(13);
		start.setY(13);
		ghost1.x = 13;
		ghost1.y = 13;
		ghost1.setX(13);
		ghost1.setY(13);
		ghost1.computeGraphicPoint();
		ghost1.setDirection(0);
		System.out.println("Reset Start. @ " + ghost1.x + "," + ghost1.y);
	}

	private void resetGoal() {
		goal.setX(1);
		goal.setY(1);
		player.setX(1);
		player.setY(1);
		player.computeGraphicPoint();
		player.setDirection(0);
		System.out.println("Reset Goal. @ " + player.x + "," + player.y);
	}

	private void checkCollision() {
		if (player.x == ghost1.x && player.y == ghost1.y && play == true
				&& win == false) {
			lives--;
			death.play();
			resetStart();
			resetGoal();
			// refreshStart();
			// refreshGoal();
			repaint();
			play = false;
			repaint();
			pf.searchPath(start, goal);
			ghost1.moves = pf.reversePath();
		}
		if (lives == 0) {
			gameOver = true;
		}
	}

	private void checkEat() {
		if (!foods.isEmpty()) {
			for (Iterator<Food> iterator = foods.iterator(); iterator.hasNext();) {
				Food f = iterator.next();
				if (player.x == f.x && player.y == f.y) {
					iterator.remove();
					score += 10;
				}
			}
		} else {
			gameOver = true;
			win = true;
			
		}
	}

	private void replay() {
		resetStart();
		resetGoal();
		repaint();
		pf.searchPath(start, goal);
		ghost1.moves = pf.reversePath();
		gameOver = false;
		lives = 3;
		score = 0;
		foods = manageFood(map);
	}

	private List<Food> manageFood(int[][] map) {
		List<Food> f = new ArrayList<Food>();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 0) {
					f.add(new Food(i, j));
				}
			}
		}
		return f;
	}

	public void displayFood(Graphics g) {
		if (!foods.isEmpty()) {
			g.setColor(Color.gray);
			for (Iterator<Food> iterator = foods.iterator(); iterator.hasNext();) {
				Food food = iterator.next();
				g.fillOval(food.point.x, food.point.y, 10, 10);
			}
		}
	}

}
