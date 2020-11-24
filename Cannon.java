import java.util.ArrayList;

/**
 * Models a cannon to be used in the game. A cannon has a starting force, a
 * current orientation, an X,Y position, a limited amount of ammunition, a list
 * of possible orientations to have, and extra power supplied by the user. A
 * cannon also has a set of similar cannonballs that can be fired.
 */
public class Cannon {

	private int xForce;
	private int yForce;
	private int cannonPos;
	private int X;
	private int Y;
	private int turretX;
	private int turretY;
	private int extraPower;
	private ArrayList<int[][]> orientations = new ArrayList<int[][]>();
	private Cannonball ball;
	private int ammo;
	private int[][] xForces = { { 0, 36 }, { 1, 25 }, { 2, 16 }, { 3, 9 } };

	private int[][] yForces = { { 0, 0 }, { 1, -9 }, { 2, -16 }, { 3, -25 } };
	private int[][] startingTurretPoints = { { 15, 40 }, { 15, 50 }, { 50, 50 }, { 50, 40 }, { 15, 40 } };
	private int[][] turretPoints2 = { { 10, 35 }, { 15, 45 }, { 50, 35 }, { 45, 25 }, { 10, 35 } };
	private int[][] turretPoints3 = { { 7, 28 }, { 12, 38 }, { 44, 23 }, { 38, 14 }, { 7, 28 } };
	private int[][] turretPoints4 = { { 0, 25 }, { 8, 33 }, { 37, 12 }, { 30, 5 }, { 0, 25 } };

	public Cannon(int X, int Y, int ammo, int power) {
		this.xForce = 36;
		this.yForce = 0;
		this.cannonPos = 0;
		this.X = X;
		this.Y = Y;
		this.ammo = ammo;
		setUpOrientations();
		ball = new Cannonball(1, 0, 1, calcEjectionPointX(), calcEjectionPointY(), 10, this.xForce, this.yForce,
				this.extraPower);
		this.extraPower = power;
	}

	/**
	 * Set up the turret orientations.
	 */
	private void setUpOrientations() {
		orientations.add(startingTurretPoints);
		orientations.add(turretPoints2);
		orientations.add(turretPoints3);
		orientations.add(turretPoints4);
	}

	public int getCannonPos() {
		return this.cannonPos;
	}

	/**
	 * Sets a new position for the cannon, and calculates the ejection point of
	 * the turret in its new position.
	 */
	public void setCannonPos(int newPos) {
		this.cannonPos = newPos;
		ball.setPosition(calcEjectionPointX(), calcEjectionPointY());
		this.xForce = this.xForces[newPos][1];
		this.yForce = this.yForces[newPos][1];
		this.ball.setInitSpeeds(this.xForce, this.yForce);
	}

	/**
	 * Sets the power of the cannon.
	 * 
	 * @param newPower
	 *            The new power of the cannon.
	 */
	public void setPower(int newPower) {
		this.extraPower = newPower;
		this.ball.setPower(newPower);
	}

	public int getX() {
		return this.X;
	}

	public int getY() {
		return this.Y;
	}

	public int getXForce() {
		return this.xForce;
	}

	public int getYForce() {
		return this.yForce;
	}

	public int[][] getOrientation(int index) {
		return orientations.get(index);
	}

	public Cannonball getBall() {
		return this.ball;
	}

	/**
	 * Calculates the x position at which the cannonball will be ejected.
	 * 
	 * @return The x point.
	 */
	public int calcEjectionPointX() {
		return (this.orientations.get(this.cannonPos)[2][0] + this.orientations.get(this.cannonPos)[3][0]) / 2 + 160;
	}

	/**
	 * Calculates the y position at which the cannonball will be ejected.
	 * 
	 * @return The y point.
	 */
	public int calcEjectionPointY() {
		return (this.orientations.get(this.cannonPos)[2][1] + this.orientations.get(this.cannonPos)[3][1]) / 2 + 530;
	}

	/**
	 * Attempts to fire the cannon.
	 * 
	 * @return Whether the cannon is able to fire.
	 */
	public boolean attemptFire() {
		if (this.ammo > 0) {

			ammo--;
			return true;
		} else {
			return false;
		}
	}
}
