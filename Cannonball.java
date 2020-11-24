/**
 * Models a cannonball that can be fired by a cannon. A cannonball has speed,
 * gravity (weight), a position, a direction, a power supplied by the cannon, a
 * color, and a size (diameter).
 */
public class Cannonball {
	private int xSpeed;
	private int ySpeed;
	private int tempYSpeed;
	private int gravity;
	private int xPosition;
	private int yPosition;
	private int direction;
	private int diameter;
	private int xInitSpeed;
	private int yInitSpeed;
	private int cannonPower;

	public Cannonball(int xSpeed, int ySpeed, int gravity, int xPosition, int yPosition, int diameter, int xInitSpeed,
			int yInitSpeed, int power) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.tempYSpeed = ySpeed;
		this.gravity = gravity;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.diameter = diameter;
		this.xInitSpeed = xInitSpeed;
		this.yInitSpeed = yInitSpeed;
		this.cannonPower = power;
	}

	public int getXSpeed() {
		return this.xSpeed;
	}

	public int getYSpeed() {
		return this.ySpeed;
	}

	public int getGravity() {
		return this.gravity;
	}

	public int getXPosition() {
		return this.xPosition;
	}

	public int getYPosition() {
		return this.yPosition;
	}

	public int getDiameter() {
		return this.diameter;
	}

	public int getXInitSpeed() {
		return this.xInitSpeed;
	}

	public int getYInitSpeed() {
		return this.yInitSpeed;
	}

	public int getPower() {
		return this.cannonPower;
	}

	/**
	 * Sets initial speeds of the cannonball.
	 * 
	 * @param x
	 *            The initial x speed.
	 * @param y
	 *            The initial y speed.
	 */
	public void setInitSpeeds(int x, int y) {
		this.xInitSpeed = x;
		this.yInitSpeed = y;
	}

	/**
	 * Sets a new power for the cannonball.
	 * 
	 * @param newPower
	 *            The new power.
	 */
	public void setPower(int newPower) {
		this.cannonPower = newPower;
	}

	/**
	 * The main physics implementation. This method moves the cannonball in
	 * terms of its x and y speeds, and takes into account both gravity and the
	 * cannon power.
	 */
	public void move() {
		this.tempYSpeed += this.gravity; // acceleration due to gravity
		this.yPosition += (this.tempYSpeed + this.yInitSpeed - cannonPower); // vertical
																				// speed
																				// as
																				// affected
																				// by
																				// initial
																				// speed
																				// applied
																				// by
																				// cannon
		this.xPosition += (this.xSpeed + this.xInitSpeed + cannonPower); // horizontal
																			// speed
																			// as
																			// affected
																			// by
																			// initial
																			// speed
																			// applied
																			// by
																			// cannon
	}

	/**
	 * Resets the y speed of the cannonball.
	 */
	public void resetYSpeed() {
		this.tempYSpeed = this.ySpeed;
	}

	/**
	 * Sets the cannonball's position.
	 * 
	 * @param x
	 *            The cannonball's x position.
	 * @param y
	 *            The cannonball's y position.
	 */
	public void setPosition(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;
	}

}
