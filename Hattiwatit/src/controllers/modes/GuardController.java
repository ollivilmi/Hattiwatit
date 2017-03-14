package controllers.modes;

import controllers.devices.IRController;
import controllers.devices.MotorController;
import functions.Timer;
import lejos.hardware.Sound;
import lejos.utility.Delay;
import main.Doge;

import java.io.File;

public class GuardController extends ModeController {
	private IRController ir;
	private float distance;
	private File bark = new File("bark.wav");
	private Timer getTimer;
	private MotorController motor;
	private int timer;

	/**
	 * 
	 * @param ir
	 *            Uses IR sensor to measure distance
	 * @param motor
	 *            Uses motor to move
	 * @param timer
	 *            Uses timer to alternate moving pattern
	 */
	public GuardController(IRController ir, MotorController motor, Timer timer) {
		super("Guard"); // Adds name to a list of mode names
		this.ir = ir;
		this.getTimer = timer;
		this.motor = motor;
		devices.add(this.ir);
		devices.add(this.getTimer);
		devices.add(this.motor); // Devices this program uses, disables them
									// when you disable the program
	}

	/**
	 * Checks timer values and distance values: changes movement directions
	 * to forward or left every interval and stops to bark if something is in front
	 */
	@Override
	protected void action() {
		distance = ir.getDistance();
		timer = getTimer.getTimer();

		if (distance > 5 && distance <= 50) { // If something is in front
			motor.halt(); // Stop moving
			Doge.message(5, "Woof");
			do {
				distance = ir.getDistance();
				Sound.playSample(bark, 100); // Keep barking until the way is clear
												
				Doge.message(4, "distance:" + Float.toString(distance));
				Delay.msDelay(10);
			} while (distance > 5 && distance <= 50);

		} else
			switch (timer) { // switch case uses timer to alternate moving
			case 1:
				motor.forward(600); // forward
				Doge.message(4, "Forward");
				while (timer == 1 && distance > 50) {
					timer = getTimer.getTimer();
					distance = ir.getDistance();
					Delay.msDelay(10);
				}
				break;

			case 2:
				motor.rollLeft(300); // left
				Doge.message(4, "Left");
				while (timer == 2 && distance > 50) {
					timer = getTimer.getTimer();
					distance = ir.getDistance();
					Delay.msDelay(10);
				}
				break;

			}
		// TODO: Fine tune moving pattern, add different patterns that you can
		// choose from
	}
	/**
	 * Resumes this mode
	 */
	@Override
	public void enable() {
		ir.setMode("Distance");
		super.enable();
	}
	/**
	 * Pauses this mode
	 */
	@Override
	public void disable() { // sets distance to 0 when disabling so the program
							// doesnt get stuck
		distance = 0;
		super.disable();
	}
}
