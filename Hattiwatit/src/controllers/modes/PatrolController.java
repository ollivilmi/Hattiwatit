package controllers.modes;

import controllers.devices.IRController;
import controllers.devices.MotorController;
import lejos.hardware.lcd.LCD;

public class PatrolController extends ModeController {
	private MotorController motor;
	private IRController ir;
	private float distance;

	/**
	 * 
	 * @param sensor
	 * @param motor
	 */
	public PatrolController(IRController ir, MotorController motor) {
		super("Patrol");
		this.motor = motor;
		this.ir = ir;
		devices.add(this.ir);
		devices.add(this.motor);
	}

	@Override
	protected void action() {
		distance = ir.getDistance();
		String msg = "";
		
		// TODO: distances as variables
		if (distance < 5) {
			motor.backward();
			msg = "backward";
		} else if (distance >= 5 && distance < 50) {
			motor.right();
			msg = "right";
		} else if (distance >= 50) {
			motor.forward();
			msg = "forward";
		}
		
		LCD.clear(4);
		LCD.drawString(msg, 0, 4);
	}

	@Override
	public void enable() {
		ir.setMode("Distance");
		super.enable();
	}
}
