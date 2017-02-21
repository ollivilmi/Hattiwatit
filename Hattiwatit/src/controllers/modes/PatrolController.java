package controllers.modes;

import controllers.devices.IRController;
import controllers.devices.MotorController;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class PatrolController extends ModeController {
	private MotorController motor;
	private IRController ir;
	private float distance;
	private int interval = 10;

	/**
	 * 
	 * @param sensor
	 * @param motor
	 */
	public PatrolController(IRController ir, MotorController motor) {
		super("Patrol");
		this.motor = motor;
		this.ir = ir;
		this.devices.add(this.ir);
		this.devices.add(this.motor);
	}

	protected void action() {
		this.distance = this.ir.getDistance();
		String msg = "";
		
		// TODO: distances as variables
		if (distance < 5) {
			this.motor.backward();
			msg = "backward";
		} else if (distance >= 5 && distance < 50) {
			this.motor.right();
			msg = "right";
		} else if (distance >= 50) {
			this.motor.forward();
			msg = "forward";
		}
		
		LCD.drawString(msg, 0, 4);
		
		Delay.msDelay(this.interval);
		
		LCD.clear(4);
	}

	@Override
	public void enable() {
		this.ir.setMode("Distance");
		super.enable();
	}
}
