package controllers.devices;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

/**
 * Controller for the motors. Each moving method can be called with or without a
 * speed parameter. If no speed parameter is given, {@link #defaultSpeed} is
 * used as the speed value.
 */
public class MotorController extends DeviceController {
	public enum Direction {
		backward, forward
	}

	/**
	 * The rotating direction of the right motor.
	 */
	private Direction directionR = Direction.forward;

	/**
	 * The rotating direction of the left motor.
	 */
	private Direction directionL = Direction.forward;

	private EV3LargeRegulatedMotor motorR;
	private EV3LargeRegulatedMotor motorL;

	/**
	 * Rotating speed of the right motor in degrees per second.
	 */
	private int speedR;

	/**
	 * Rotating speed of the left motor in degrees per second.
	 */
	private int speedL;

	/**
	 * Default rotating speed for the motors in degrees per second.
	 */
	private int defaultSpeed;

	/**
	 * @param right
	 *            The port where the right motor is plugged into.
	 * @param left
	 *            The port where the left motor is plugged into.
	 * @param defaultSpeed
	 *            The default speed of the motors.
	 */
	public MotorController(Port right, Port left, int defaultSpeed) {
		motorR = new EV3LargeRegulatedMotor(right);
		motorL = new EV3LargeRegulatedMotor(left);
		this.defaultSpeed = defaultSpeed;
	}

	/**
	 * Rotates the motors according to their speed and direction values.
	 */
	@Override
	protected void action() {
		motorR.setSpeed(speedR);

		switch (directionR) {
		case forward:
			motorR.backward();
			break;
		case backward:
			motorR.forward();
			break;
		}

		motorL.setSpeed(speedL);

		switch (directionL) {
		case forward:
			motorL.backward();
			break;
		case backward:
			motorL.forward();
			break;
		}
	}

	public void backward() {
		this.backward(defaultSpeed);
	}

	public void backward(int speed) {
		move(speed, speed, Direction.backward, Direction.backward);
	}

	@Override
	protected void cleanUp() {
		motorR.close();
		motorL.close();
	}

	@Override
	public void disable() {
		// fixes one motor keeps running
		super.disable();
		Delay.msDelay(interval);
		halt();
	}

	@Override
	public void enable() {
		speedR = defaultSpeed;
		speedL = defaultSpeed;
		super.enable();
	}

	public void forward() {
		this.forward(defaultSpeed);
	}

	public void forward(int speed) {
		move(speed, speed, Direction.forward, Direction.forward);
	}

	public void gentleLeft() {
		gentleLeft(defaultSpeed);
	}

	public void gentleLeft(int speed) {
		move(speed, (int) (.7 * speed), Direction.forward, Direction.forward);
	}

	public void gentleRight() {
		gentleRight(defaultSpeed);
	}

	public void gentleRight(int speed) {
		move((int) (.7 * speed), speed, Direction.forward, Direction.forward);
	}

	public int getMaxSpeed() {
		return (int) motorR.getMaxSpeed();
	}
	
	/**
	 * Stops the motors.
	 */
	public void halt() {
		setSpeed(0);
		motorR.stop(true);
		motorL.stop(true);
	}

	/**
	 * The generic moving method for the motors.
	 * 
	 * @param speedR
	 * @param speedL
	 * @param dirR
	 * @param dirL
	 */
	public void move(int speedR, int speedL, Direction dirR, Direction dirL) {
		setSpeed(speedR, speedL);
		directionR = dirR;
		directionL = dirL;
	}

	public void rollLeft() {
		this.rollLeft(defaultSpeed);
	}

	public void rollLeft(int speed) {
		move(speed, speed, Direction.forward, Direction.backward);
	}

	public void rollRight() {
		this.rollRight(defaultSpeed);
	}

	public void rollRight(int speed) {
		move(speed, speed, Direction.backward, Direction.forward);
	}

	public void setSpeed(int speed) {
		setSpeed(speed, speed);
	}

	public void setSpeed(int speedR, int speedL) {
		this.speedR = speedR;
		this.speedL = speedL;
	}

	public void sharpLeft() {
		sharpLeft(defaultSpeed);
	}

	public void sharpLeft(int speed) {
		move(speed, (int) (.3 * speed), Direction.forward, Direction.forward);
	}

	public void sharpRight() {
		sharpRight(defaultSpeed);
	}

	public void sharpRight(int speed) {
		move((int) (.3 * speed), speed, Direction.forward, Direction.forward);
	}
}
