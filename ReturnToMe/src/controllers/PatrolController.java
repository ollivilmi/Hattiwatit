package controllers;

import functions.Movement;
import lejos.utility.Delay;
import lejos.hardware.lcd.LCD;

public class PatrolController extends DeviceController {
	private Movement motor;
	private IRController ir;
	//private Patrol patrol;
	private float distance;
	/**
	 * 
	 * @param sensor
	 * @param motor
	 */
	public PatrolController(IRController ir, Movement motor) {
		this.motor = motor;
		this.ir = ir;
		//this.patrol = new Patrol(this.ir, this.motor);
	}
	public void action() {

			this.distance = this.ir.getDistance();
			LCD.drawString("Patrolling", 0, 5);
			
			if (distance < 5) {
				this.motor.backward();
			} else if (distance >= 5 && distance < 50) {
				motor.right();
				Delay.msDelay(1000);
			} else if (distance >= 50) {
				motor.forward();
			}
			Delay.msDelay(10);
			LCD.clear(5);
		
	}
	@Override
	public void cleanUp() {
	}
	@Override
	public void disable() {
		super.disable();
		motor.stop();
	}
}
