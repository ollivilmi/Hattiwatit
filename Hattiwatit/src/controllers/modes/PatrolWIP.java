package controllers.modes;

import controllers.devices.IRController;
import controllers.devices.MotorController;
import functions.Timer;
import lejos.utility.Delay;
import main.Doge;
import java.util.Random;

public class PatrolWIP extends ModeController {
	private MotorController motor;
	private IRController ir;
	private float distance;
	private int timer, direction;
	private Timer getTimer;
	private Random random;

	/**
	 * 
	 * @param ir sensor Uses distance sensor to see what is in front
	 * @param motor Uses motor to move
	 */
	public PatrolWIP(IRController ir, MotorController motor, Timer timer) {
		super("PatrolWIP"); // Names the mode as Patrol in the arraylist
		this.motor = motor;
		this.ir = ir;
		this.getTimer = timer;
		this.random = new Random();
		devices.add(this.ir);
		devices.add(this.motor);
		devices.add(this.getTimer);
		// Devices which this mode uses
	}

	/**
	 * Sharp turn left when something is in front Steer right or left when the
	 * way is clear
	 */
	@Override
	protected void action() {
		distance = ir.getDistance(); // Gets distance
		String msg = "";
		timer = getTimer.getTimer(); // Gets a timer value
		direction = random.nextInt(2) + 1; // Randomly chooses 1 or 2
		Doge.message(6, "Random: " + Integer.toString(direction));
		
		if (distance > 5 && distance <= 50) { //If something is in front
			motor.rollLeft();
			while (distance > 5 && distance <= 50) { //Keeps turning until the way is clear 
				Delay.msDelay(100);
				distance = ir.getDistance();
			}
		} else
			switch (direction) { //Switch for random movement orders
			case 1:
				motor.rollLeft(); // sharp turn left
				msg = "Left";
				while (timer == getTimer.getTimer() && distance > 50) { // 1 Second interval to go left
					distance = ir.getDistance();
					Delay.msDelay(10);
				}
				break;
			case 2:
				motor.rollRight(); // sharp turn right
				msg = "Right";
				while (timer == getTimer.getTimer() && distance > 50) { // 1 Second interval to go right
					distance = ir.getDistance();
					Delay.msDelay(10);
				}
				break;
			}
		Doge.message(4, msg); // LCD prints
		//TODO: add more random movement options
	}

	/**
	 * Gets distance values when using this mode
	 */
	@Override
	public void enable() {
		ir.setMode("Distance");
		super.enable();
	}

	@Override
	public void disable() {
		distance = 0;
		super.disable();
	}
}
