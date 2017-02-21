package controllers.modes;

import controllers.devices.IRController;
import controllers.devices.MotorController;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class FollowController extends ModeController {
	private MotorController motor;
	private IRController ir;
	private float distance, bearing;
	private int distanceMin = 5,
				distanceMax = 99,
				forwardThreshold = 4,
				interval = 10;

	/**
	 * 
	 * @param sensor
	 * @param motor
	 */
	public FollowController(IRController ir, MotorController motor) {
		// TODO: name as a variable
		super("Follow");
		this.motor = motor;
		this.ir = ir;
		this.devices.add(ir);
		this.devices.add(motor);
	}

	protected void action() {
		this.distance = this.ir.getSeekDistance();
		this.bearing = this.ir.getSeekBearing();
		String msg = "";
		
		if (this.distance <= this.distanceMin || (this.distance >= this.distanceMax && this.bearing == 0)) {
			msg = "halt";
			this.motor.halt();
		} else if (this.bearing < (-this.forwardThreshold)) {
			msg = "left";
			this.motor.left();
		} else if (this.bearing <= this.forwardThreshold && this.bearing >= (-this.forwardThreshold)) {
			msg = "forward";
			this.motor.forward();
		} else if (this.bearing > this.forwardThreshold) {
			msg = "right";
			this.motor.right();
		}
		
		LCD.clear(4);
		LCD.drawString(msg, 0, 4);
		
		Delay.msDelay(this.interval);
	}

	@Override
	public void enable() {
		this.ir.setMode("Seek");
		super.enable();
	}
}
