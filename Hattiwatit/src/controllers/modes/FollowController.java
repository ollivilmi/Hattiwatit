package controllers.modes;

import controllers.devices.IRController;
import controllers.devices.MotorController;
import main.Doge;

/**
 * Follows the IR remote within ~2 meter radius.
 */
public class FollowController extends ModeController {
	private MotorController motor;
	private IRController ir;
	/**
	 * Distance to the IR remote.
	 */
	private float distance;

	/**
	 * Bearing to the IR remote.
	 */
	private float bearing;

	/**
	 * Minimum distance to the IR remote.
	 */
	private int distanceMin = 5;

	/**
	 * Maximum distance to the IR remote.
	 */
	private int distanceMax = 99;

	/**
	 * Maximum (absolute) bearing value which results in a forward motion.
	 */
	private int forwardThreshold = 8;

	/**
	 * Minimum forward speed.
	 */
	private int speedMin = 450;

	/**
	 * Maximum forward speed.
	 */
	private int speedMax;

	/**
	 * Current forward speed.
	 */
	private int speed = speedMin;

	/**
	 * @param ir
	 *            IR controller to locate the remote control
	 * @param motor
	 *            Motor controller to use for moving
	 */
	public FollowController(IRController ir, MotorController motor) {
		// TODO: name as a variable
		super("Follow");
		this.motor = motor;
		this.ir = ir;
		devices.add(ir);
		devices.add(motor);
		speedMax = motor.getMaxSpeed();
	}

	/**
	 * Gets the remote's position and moves towards it.
	 */
	@Override
	protected void action() {
		// get distance and bearing to the IR remote
		distance = ir.getSeekDistance();
		bearing = ir.getSeekBearing();
		String msg = "";

		// how far away is the remote
		float distanceRatio = distance / distanceMax;
		speedMax = motor.getMaxSpeed();
		speed = (int) (distanceRatio * (speedMax - speedMin)) + speedMin;

		if (distance <= distanceMin || (distance >= distanceMax && bearing == 0)) {
			// remote is too close or is not found
			msg = "halt";
			motor.halt();
		} else if (bearing < (-forwardThreshold)) {
			// remote is to the left
			msg = "left";
			motor.rollLeft();
		} else if (bearing <= forwardThreshold && bearing >= (-forwardThreshold)) {
			// remote is straight ahead
			msg = "forward";
			motor.forward(speed);
		} else if (bearing > forwardThreshold) {
			// remote is to the right
			msg = "right";
			motor.rollRight();
		}

		// print the direction
		Doge.message(4, msg);
		Doge.message(5, String.format("speed: %d", speed));
	}

	/**
	 * Sets the IR sensor into the right mode, then enables the mode.
	 */
	@Override
	public void enable() {
		ir.setMode("Seek");
		super.enable();
	}
}
