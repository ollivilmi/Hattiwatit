package controllers;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import functions.Movement;


public class FollowController extends DeviceController {
	private Movement motor;
	private IRController ir;
	//private Print printer;
	//private Follow follower;
	private float distance, bearing;
	
/**
 * 
 * @param sensor
 * @param motor
 */
	public FollowController (IRController ir, Movement motor) {
		this.motor = motor;
		this.ir = ir;
	}
	
	public void action() {
		
			this.distance = this.ir.getDistance();
			this.bearing = this.ir.getBearing();
			LCD.drawString("Following", 0, 5);
			
			if (this.distance < 5 || ( this.distance == Float.POSITIVE_INFINITY && this.bearing == 0)) {
				this.motor.stop();			
			} else if (bearing < -2) {		
				this.motor.left();	
			} else if (bearing < 3 && bearing > -3) {	
				this.motor.forward();	
			} else if (bearing > 2) {	
				this.motor.right();				
			} 
			Delay.msDelay(10);
			LCD.clear(5);
	}
	@Override
	public void cleanUp() {
	}
}
