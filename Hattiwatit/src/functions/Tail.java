package functions;

import controllers.devices.DeviceController;
import controllers.devices.TouchController;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;

public class Tail extends DeviceController {
	private int count = 0;
	int speed = 700;
	private EV3MediumRegulatedMotor motor;
	private TouchController touch;
	/**
	 * @param motorT Uses small motor to control tail
	 * @param touch Uses TouchController to get button presses
	 */
	public Tail(Port motorT, TouchController touch) {
		this.motor = new EV3MediumRegulatedMotor(motorT);
		this.touch = touch;
		touch.enable();

	}

	/**
	 * Thread is waiting for a Touch sensor press or a method call to change
	 * count value The tail wags "count" times and uses the speed variable
	 * to change speed
	 * 
	 * Leaves the loop if the program is shutting down
	 */
	@Override
	public void action() {
		while (count == 0 && alive == true) { //If the device isn't shutting down, wait
			if (touch.getPress() == 1) {     // for something to change count
				count = 4;
			}
		}
		motor.setSpeed(speed);
		if (alive == true) {
			motor.rotate(-60);   //Rotate the tail to be ready for wagging
			for (int x = 0; x < count; x++) { //Wag "count" times
				motor.rotate(120);  
				motor.rotate(-120);
			}
			motor.rotate(60); //Rotate the tail back to the original position
			count = 0; //Reset count
		}
	}
	/**
	 * The general method that is called to wag the tail from different modes
	 * @param newCount How many times the tail wags
	 * @param newSpeed How fast
	 */
	public void wagTail(int newCount, int newSpeed) {
		count = newCount;
		speed = newSpeed;
	}
	/**
	 * Shuts down motor after closing the program
	 */
	@Override
	protected void cleanUp() {
		motor.close();
	}
}