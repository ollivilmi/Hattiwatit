package controllers.modes;

import controllers.devices.IRController;
import controllers.devices.MotorController;
import main.Doge;

public class PatrolController extends ModeController {
	private MotorController motor;
	private IRController ir;
	private float distance;

	/**
	 * 
	 * @param ir sensor Uses distance sensor to see what is in front
	 * @param motor Uses motor to move
	 */
	public PatrolController(IRController ir, MotorController motor) {
		super("Patrol"); //Names the mode as Patrol in the arraylist
		this.motor = motor;
		this.ir = ir;
		devices.add(this.ir);
		devices.add(this.motor); //Devices which this mode uses
	}
/**
 * Turn right when something is in front
 * Move forward when the way is clear
 */
	@Override
	protected void action() {
		distance = ir.getDistance(); //Gets distance
		String msg = "";
		
		
		if (distance >= 5 && distance < 50) { //Turn right when something is in front
			msg = "right";
			while (distance >= 5 && distance < 50) {
				motor.right();
			}
		} else if (distance >= 50) { //Move forward when the way is clear
			motor.forward();
			msg = "forward";
		}
		//TODO  make movement random by using timer and java.util.rand
		Doge.message(4, msg); //LCD prints
	}

	/**
	 * Gets distance values when using this mode
	 */
	@Override
	public void enable() {
		ir.setMode("Distance");
		super.enable();
	}
}
