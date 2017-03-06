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
	private float distance;
	private float bearing;
	private int distanceMin = 5;
	private int distanceMax = 99;
	private int forwardThreshold = 4;

	/**
	 * @param ir
	 *            IR controller to use
	 * @param motor
	 *            Motor controller to use
	 */
	public FollowController(IRController ir, MotorController motor) {
		// TODO: name as a variable
		super("Follow");
		this.motor = motor;
		this.ir = ir;
		devices.add(ir);
		devices.add(motor);
	}

	/**
	 * Gets the remote's position and moves towards it.
	 */
	@Override
	protected void action() {
		distance = ir.getSeekDistance();
		bearing = ir.getSeekBearing();
		String msg = "";

		if (distance <= distanceMin || (distance >= distanceMax && bearing == 0)) {
			msg = "halt";
			motor.halt();
		} else if (bearing < (-forwardThreshold)) {
			msg = "left";
			motor.rollLeft();
		} else if (bearing <= forwardThreshold && bearing >= (-forwardThreshold)) {
			msg = "forward";
			motor.forward();
		} else if (bearing > forwardThreshold) {
			msg = "right";
			motor.rollRight();
		}

		Doge.message(4, msg);
	}

	@Override
	public void enable() {
		ir.setMode("Seek");
		super.enable();
	}
}
