import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;

/**
 * The main CannonGame class. This class handles all the drawing-to-screen jobs.
 * The constants initialized at the start provide for all the non-changing
 * aspects of the game screen.
 */
public class CannonGame extends JFrame implements MouseListener {

	private static final int GAME_STATE_INTRO = 0;
	private static final int GAME_STATE_PLAYING = 1;
	private static final int GAME_STATE_ENDING = 2;
	private static final int WINDOW_WIDTH = 1250;
	private static final int WINDOW_HEIGHT = 700;
	private static final int UP_BUTTON_XPOS = 50;
	private static final int UP_BUTTON_YPOS = 100;
	private static final int UP_BUTTON_HEIGHT = 30;
	private static final int UP_BUTTON_WIDTH = 80;
	private static final int DOWN_BUTTON_XPOS = 50;
	private static final int DOWN_BUTTON_YPOS = 150;
	private static final int DOWN_BUTTON_HEIGHT = 30;
	private static final int DOWN_BUTTON_WIDTH = 80;
	private static final int FIRE_BUTTON_XPOS = 50;
	private static final int FIRE_BUTTON_YPOS = 200;
	private static final int FIRE_BUTTON_HEIGHT = 30;
	private static final int FIRE_BUTTON_WIDTH = 80;
	private static final int POWER_METER_XPOS = 30;
	private static final int POWER_METER_YPOS = 250;
	private static final int POWER_METER_HEIGHT = 20;
	private static final int POWER_METER_WIDTH = 120;
	private static final int CANNON_X_POS = 100;
	private static final int CANNON_Y_POS = 550;
	private static final int TITLE_X = 350;
	private static final int TITLE_Y = 300;
	private static final int TURRET_X_DISP = 165;
	private static final int TURRET_Y_DISP = 535;
	private Cannon gameCannon;
	private ArrayList<Target> targets = new ArrayList<Target>();

	private int gameState;
	private int powerState;
	private Graphics gfx;

	public static void main(String[] args) {
		new CannonGame();
	}

	public CannonGame() {
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.show();
		this.addMouseListener(this);
		this.gameState = GAME_STATE_INTRO;
		this.powerState = 0;
		this.gameCannon = new Cannon(CANNON_X_POS, CANNON_Y_POS, 50, this.powerState);
		this.setTargets();
		gfx = this.getGraphics();
	}

	/**
	 * Paints the game screen.
	 * 
	 * @param gfx
	 *            The Graphics object.
	 */
	public void paint(Graphics gfx) {
		gfx.setColor(Color.gray);
		int width = this.getWidth();
		int height = this.getHeight();
		gfx.fillRect(0, 0, width, height);
		if (this.gameState == GAME_STATE_INTRO) {
			this.drawTitleScreen();
		} else if (this.gameState == GAME_STATE_PLAYING) {
			this.drawCannon();
			this.drawPlatform();
			this.drawButtons();
			this.drawTargets(targets);
		} else if (this.gameState == GAME_STATE_ENDING) {
			this.drawEndingScreen();
		}
	}

	/**
	 * Paints the title screen.
	 */
	private void drawTitleScreen() {
		gfx.setColor(Color.black);
		gfx.drawString("Cannon Game: Click to Continue", TITLE_X, TITLE_Y);
	}

	/**
	 * Paints the ending screen.
	 */
	private void drawEndingScreen() {
		gfx.setColor(Color.black);
		gfx.drawString("Congratulations! You won!", TITLE_X, TITLE_Y);
	}

	/**
	 * Draws the game cannon on screen.
	 */
	private void drawCannon() {

		gfx.setColor(Color.black);

		gfx.fillArc(gameCannon.getX(), gameCannon.getY(), 80, 70, 0, 180);
		for (int i = 1; i < gameCannon.getOrientation(0).length; i++) {
			int X1 = TURRET_X_DISP + gameCannon.getOrientation(0)[i - 1][0];
			int Y1 = TURRET_Y_DISP + gameCannon.getOrientation(0)[i - 1][1];
			int X2 = TURRET_X_DISP + gameCannon.getOrientation(0)[i][0];
			int Y2 = TURRET_Y_DISP + gameCannon.getOrientation(0)[i][1];
			gfx.drawLine(X1, Y1, X2, Y2);
		}
	}

	/**
	 * Draws the platform on screen.
	 */
	private void drawPlatform() {
		gfx.setColor(Color.lightGray);
		gfx.fillRect(0, CANNON_Y_POS + 35, CANNON_X_POS + 80, 70);
		gfx.fillRect(0, CANNON_Y_POS + 105, CANNON_X_POS + 130, 90);
		gfx.fillRect(0, CANNON_Y_POS + 195, CANNON_X_POS + 160, 90);
	}

	/**
	 * Sets the positions and colors of the randomly generated targets and the
	 * final target.
	 */
	private void setTargets() {
		Random colorGen = new Random();
		Random posGen = new Random();
		for (int i = 0; i < 5; i++) {
			int r = colorGen.nextInt(255);
			int g = colorGen.nextInt(255);
			int b = colorGen.nextInt(255);
			int y = posGen.nextInt(120) + (i * 100);
			this.targets.add(new Target(100, 50, new Color(r, g, b), 500, y));
		}
		for (int i = 0; i < 8; i++) {
			int r = colorGen.nextInt(255);
			int g = colorGen.nextInt(255);
			int b = colorGen.nextInt(255);
			int y = posGen.nextInt(80) + (i * 70);
			this.targets.add(new Target(60, 50, new Color(r, g, b), 650, y));
		}
		for (int i = 0; i < 10; i++) {
			int r = colorGen.nextInt(255);
			int g = colorGen.nextInt(255);
			int b = colorGen.nextInt(255);
			int y = posGen.nextInt(60) + (i * 40);
			this.targets.add(new Target(40, 50, new Color(r, g, b), 800, y));
		}
		this.targets.add(new Target(200, 100, Color.black, 1000, 150));
	}

	/**
	 * Draws all the targets from the target list onto the screen.
	 */
	private void drawTargets(ArrayList targets) {
		Iterator<Target> iter = targets.iterator();
		while (iter.hasNext()) {
			Target t = iter.next();
			gfx.setColor(t.getColor());
			gfx.fillRect(t.getXPosition(), t.getYPosition(), t.getWidth(), t.getHeight());
		}
	}

	/**
	 * Draws the control buttons on screen.
	 */
	private void drawButtons() {

		gfx.setColor(Color.black);
		gfx.drawRect(UP_BUTTON_XPOS, UP_BUTTON_YPOS, UP_BUTTON_WIDTH, UP_BUTTON_HEIGHT);
		gfx.setColor(Color.green);
		gfx.drawString("UP", UP_BUTTON_XPOS + 30, UP_BUTTON_YPOS + 20);
		gfx.setColor(Color.black);
		gfx.drawRect(DOWN_BUTTON_XPOS, DOWN_BUTTON_YPOS, DOWN_BUTTON_WIDTH, DOWN_BUTTON_HEIGHT);
		gfx.setColor(Color.red);
		gfx.drawString("DOWN", DOWN_BUTTON_XPOS + 20, DOWN_BUTTON_YPOS + 20);
		gfx.setColor(Color.black);
		gfx.drawRect(FIRE_BUTTON_XPOS, FIRE_BUTTON_YPOS, FIRE_BUTTON_WIDTH, FIRE_BUTTON_HEIGHT);
		gfx.setColor(Color.orange);
		gfx.drawString("FIRE!", FIRE_BUTTON_XPOS + 20, FIRE_BUTTON_YPOS + 20);
		gfx.setColor(Color.black);
		gfx.drawRect(POWER_METER_XPOS, POWER_METER_YPOS, POWER_METER_WIDTH, POWER_METER_HEIGHT);
		gfx.drawString("POWER", POWER_METER_XPOS + 30, POWER_METER_YPOS + 40);
		gfx.setColor(Color.white);
		gfx.fillArc(POWER_METER_XPOS + 5, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		gfx.setColor(Color.green);
		gfx.drawArc(POWER_METER_XPOS + 35, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		gfx.setColor(Color.yellow);
		gfx.drawArc(POWER_METER_XPOS + 65, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		gfx.setColor(Color.red);
		gfx.drawArc(POWER_METER_XPOS + 95, POWER_METER_YPOS + 3, 15, 15, 0, 360);
	}

	/**
	 * Erases a target from the screen.
	 * 
	 * @param target
	 *            The Target to be erased.
	 */
	private void eraseTarget(Target target) {
		gfx.setColor(Color.gray);
		gfx.fillRect(target.getXPosition(), target.getYPosition(), target.getWidth(), target.getHeight());
	}

	/**
	 * Creates a small explosion on screen.
	 * 
	 * @param xPos
	 *            The X-Coordinate of the explosion.
	 * @param yPos
	 *            The Y-Coordinate of the explosion.
	 */
	private void smallBlast(int xPos, int yPos) {
		gfx.setColor(Color.white);
		gfx.fillArc(xPos, yPos, 10, 10, 0, 360);
		wait(200);
		gfx.setColor(Color.gray);
		gfx.fillArc(xPos, yPos, 10, 10, 0, 360);
		gfx.setColor(Color.yellow);
		gfx.fillArc(xPos - 5, yPos - 5, 20, 20, 0, 360);
		wait(200);
		gfx.setColor(Color.gray);
		gfx.fillArc(xPos - 5, yPos - 5, 20, 20, 0, 360);
		gfx.setColor(Color.red);
		gfx.fillArc(xPos - 10, yPos - 10, 30, 30, 0, 360);
		wait(200);
		gfx.setColor(Color.gray);
		gfx.fillArc(xPos - 10, yPos - 10, 30, 30, 0, 360);
	}

	/**
	 * Creates a large explosion on screen.
	 * 
	 * @param xPos
	 *            The X-Coordinate of the explosion.
	 * @param yPos
	 *            The Y-Coordinate of the explosion.
	 */
	private void largeBlast(int xPos, int yPos) {
		for (int i = 0; i < 200; i++) {
			if (i < 50) {
				gfx.setColor(Color.white);
			} else if (i >= 50 && i < 100) {
				gfx.setColor(Color.yellow);
			} else if (i >= 100 && i < 150) {
				gfx.setColor(Color.orange);
			} else if (i >= 150 && i < 200) {
				gfx.setColor(Color.red);
			}
			gfx.fillArc(xPos - i, yPos - i, 10 + (2 * i), 10 + (2 * i), 0, 360);
			wait(10);
			gfx.setColor(Color.gray);
			gfx.fillArc(xPos - i, yPos - i, 10 + (2 * i), 10 + (2 * i), 0, 360);
		}
	}

	/**
	 * Changes the cannon turret's orientation, and ends up drawing it on screen
	 * with the help of the moveCannon method.
	 * 
	 * @param direction
	 *            The direction in which to move the turret (Up or Down).
	 * @return The new position of the turret.
	 */
	private int changeCannonPos(String direction) {
		int cannonPos = gameCannon.getCannonPos();
		moveCannon(TURRET_X_DISP, TURRET_Y_DISP, Color.gray, gameCannon.getOrientation(cannonPos));

		if (direction.equals("Up") && cannonPos != 3) {
			cannonPos += 1;
		}
		if (direction.equals("Down") && cannonPos != 0) {
			cannonPos -= 1;
		}
		moveCannon(TURRET_X_DISP, TURRET_Y_DISP, Color.black, gameCannon.getOrientation(cannonPos));
		return cannonPos;
	}

	/**
	 * Draws the cannon in the specified color along the points specified.
	 * 
	 * @param x
	 *            The main X-Coordinate.
	 * @param x
	 *            The main Y-Coordinate.
	 * @param color
	 *            The color of the turret.
	 * @param turretPoints
	 *            The array of ints that represents the basic set of coordinates
	 *            the turret will be drawn along.
	 */
	private void moveCannon(int x, int y, Color color, int[][] turretPoints) {
		gfx.setColor(color);
		for (int i = 1; i < turretPoints.length; i++) {
			int X1 = x + turretPoints[i - 1][0];
			int Y1 = y + turretPoints[i - 1][1];
			int X2 = x + turretPoints[i][0];
			int Y2 = y + turretPoints[i][1];
			gfx.drawLine(X1, Y1, X2, Y2);
		}
	}

	/**
	 * Commences firing of the cannon. The cannonball is ejected from the cannon
	 * turret, and it travels along until it hits a target or its fire duration
	 * has expired. At conclusion of the sequence, the ball is returned to its
	 * original position.
	 */
	private void fireCannon() {
		if (gameCannon.attemptFire() == true) {
			Cannonball ball = this.gameCannon.getBall();
			for (int i = 0; i < 60; i++) {
				drawBall();
				wait(50);
				eraseBall();
				if (hasHitTarget(ball) != null) {
					Target t = hasHitTarget(ball);
					i = 60;
					if (t.getHeight() == 200) {
						largeBlast(ball.getXPosition(), ball.getYPosition());
						this.gameState = GAME_STATE_ENDING;
						wait(300);
						this.repaint();
					} else {
						smallBlast(ball.getXPosition(), ball.getYPosition());
					}
					eraseTarget(t);
					this.targets.remove(t);
					drawTargets(this.targets);
				} else {
					ball.move();
				}
			}
			ball.resetYSpeed();
			ball.setPosition(this.gameCannon.calcEjectionPointX(), this.gameCannon.calcEjectionPointY());
		} else {
			System.out.println("Cannot fire! Need more ammo!");
		}
	}

	/**
	 * Draws the ball image on the screen.
	 */
	private void drawBall() {
		gfx.setColor(Color.black);
		Cannonball ball = this.gameCannon.getBall();
		gfx.fillArc(ball.getXPosition(), ball.getYPosition(), ball.getDiameter(), ball.getDiameter(), 0, 360);
	}

	/**
	 * Removes the ball image from the screen.
	 */
	private void eraseBall() {
		gfx.setColor(Color.gray);
		Cannonball ball = this.gameCannon.getBall();
		gfx.fillArc(ball.getXPosition(), ball.getYPosition(), ball.getDiameter(), ball.getDiameter(), 0, 360);
	}

	/**
	 * Determines if the cannonball has hit a target, and, if so, returns the
	 * target hit. Returns null if no target his been hit.
	 * 
	 * @param ball
	 *            The cannonball to check.
	 * @return The Target that was hit if one was hit, null if no Target was
	 *         hit.
	 */
	private Target hasHitTarget(Cannonball ball) {
		Iterator<Target> iter = targets.iterator();
		while (iter.hasNext()) {
			Target t = iter.next();
			if (isWithin(ball.getXPosition(), t.getXPosition(), t.getXPosition() + t.getWidth())
					&& isWithin(ball.getYPosition(), t.getYPosition(), t.getYPosition() + t.getHeight())) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Returns true if the specified num parameter can be found within the two
	 * other numbers.
	 * 
	 * @param num
	 *            The number to be checked.
	 * @param p1
	 *            The first boundary num is to be checked against.
	 * @param p2
	 *            The second boundary num is to be checked against.
	 * @return Whether num is between p1 and p2.
	 */
	private boolean isWithin(int num, int p1, int p2) {
		if (num >= p1 && num <= p2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Changes the visual representation of the current cannon power, and
	 * changes the actual power of the cannon.
	 * 
	 * @param newPowerState
	 *            The new power state of the cannon.
	 */
	private void changeMeter(int newPowerState) {
		emptyPower(this.powerState);
		if (newPowerState == 0) {
			fillPower(0);
		} else if (newPowerState == 1) {
			fillPower(1);
		} else if (newPowerState == 2) {
			fillPower(2);
		} else if (newPowerState == 3) {
			fillPower(3);
		}
		powerState = newPowerState;
		this.gameCannon.setPower(this.powerState * 3);
	}

	/**
	 * Fill the correct power circle according to the passed parameter.
	 * 
	 * @param powerState
	 *            The power state of the cannon.
	 */
	private void fillPower(int powerState) {
		if (powerState == 0) {
			gfx.setColor(Color.white);
			gfx.fillArc(POWER_METER_XPOS + 5, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		} else if (powerState == 1) {
			gfx.setColor(Color.green);
			gfx.fillArc(POWER_METER_XPOS + 35, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		} else if (powerState == 2) {
			gfx.setColor(Color.yellow);
			gfx.fillArc(POWER_METER_XPOS + 65, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		} else if (powerState == 3) {
			gfx.setColor(Color.red);
			gfx.fillArc(POWER_METER_XPOS + 95, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		}
	}

	/**
	 * Empties the correct power circle according to the passed parameter.
	 * 
	 * @param powerState
	 *            The power state of the cannon.
	 */
	private void emptyPower(int powerState) {
		gfx.setColor(Color.gray);
		if (powerState == 0) {
			gfx.fillArc(POWER_METER_XPOS + 5, POWER_METER_YPOS + 3, 15, 15, 0, 360);
			gfx.setColor(Color.white);
			gfx.drawArc(POWER_METER_XPOS + 5, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		} else if (powerState == 1) {
			gfx.fillArc(POWER_METER_XPOS + 35, POWER_METER_YPOS + 3, 15, 15, 0, 360);
			gfx.setColor(Color.green);
			gfx.drawArc(POWER_METER_XPOS + 35, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		} else if (powerState == 2) {
			gfx.fillArc(POWER_METER_XPOS + 65, POWER_METER_YPOS + 3, 15, 15, 0, 360);
			gfx.setColor(Color.yellow);
			gfx.drawArc(POWER_METER_XPOS + 65, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		} else if (powerState == 3) {
			gfx.fillArc(POWER_METER_XPOS + 95, POWER_METER_YPOS + 3, 15, 15, 0, 360);
			gfx.setColor(Color.red);
			gfx.drawArc(POWER_METER_XPOS + 95, POWER_METER_YPOS + 3, 15, 15, 0, 360);
		}
	}

	public void mouseClicked(MouseEvent event) {
	}

	public void mouseEntered(MouseEvent event) {
	}

	public void mouseExited(MouseEvent event) {
	}

	public void mousePressed(MouseEvent event) {
	}

	/**
	 * Executes when the mouse button is released.
	 * 
	 * @param event
	 *            The MouseEvent recorded.
	 */
	public void mouseReleased(MouseEvent event) {
		if (this.gameState == GAME_STATE_INTRO) {
			this.gameState = GAME_STATE_PLAYING;
			this.repaint();
		}
		if (this.gameState == GAME_STATE_PLAYING) {
			if (isWithin(event.getX(), UP_BUTTON_XPOS, UP_BUTTON_XPOS + UP_BUTTON_WIDTH)
					&& isWithin(event.getY(), UP_BUTTON_YPOS, UP_BUTTON_YPOS + UP_BUTTON_HEIGHT)) {
				gameCannon.setCannonPos(this.changeCannonPos("Up"));
			}
			if (isWithin(event.getX(), DOWN_BUTTON_XPOS, DOWN_BUTTON_XPOS + DOWN_BUTTON_WIDTH)
					&& isWithin(event.getY(), DOWN_BUTTON_YPOS, DOWN_BUTTON_YPOS + DOWN_BUTTON_HEIGHT)) {
				gameCannon.setCannonPos(this.changeCannonPos("Down"));
			}
			if (isWithin(event.getX(), FIRE_BUTTON_XPOS, FIRE_BUTTON_XPOS + FIRE_BUTTON_WIDTH)
					&& isWithin(event.getY(), FIRE_BUTTON_YPOS, FIRE_BUTTON_YPOS + FIRE_BUTTON_HEIGHT)) {
				fireCannon();
			}
			if (isWithin(event.getX(), POWER_METER_XPOS, POWER_METER_XPOS + POWER_METER_WIDTH)
					&& isWithin(event.getY(), POWER_METER_YPOS, POWER_METER_YPOS + POWER_METER_HEIGHT)) {
				if (isWithin(event.getX(), POWER_METER_XPOS + 5, POWER_METER_XPOS + 20)) {
					changeMeter(0);
				}
				if (isWithin(event.getX(), POWER_METER_XPOS + 35, POWER_METER_XPOS + 50)) {
					changeMeter(1);
				}
				if (isWithin(event.getX(), POWER_METER_XPOS + 65, POWER_METER_XPOS + 80)) {
					changeMeter(2);
				}
				if (isWithin(event.getX(), POWER_METER_XPOS + 95, POWER_METER_XPOS + 110)) {
					changeMeter(3);
				}
			}

		}
	}

	/**
	 * Waits for a specified number of milliseconds before finishing. This
	 * provides an easy way to specify a small delay which can be used when
	 * producing animations.
	 * 
	 * @param milliseconds
	 *            Number of milliseconds to wait.
	 */
	public void wait(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// ignoring exception at the moment
		}
	}
}
