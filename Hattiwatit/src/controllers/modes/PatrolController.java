package controllers.modes;

import controllers.devices.IRController;
import controllers.devices.MotorController;
import functions.Timer;
import lejos.utility.Delay;
import main.Doge;
import java.util.Random;

public class PatrolController extends ModeController {
	private MotorController motor;
	private IRController ir;
	private float distance;
	private int timer, direction;
	private int lastTurn = 0;
	private Timer getTimer;
	private Random random;

	/**
	 * 
	 * @param ir sensor Uses distance sensor to see what is in front
	 * @param motor Uses motor to move
	 * @param Timer Uses timer to alternate moving patterns
	 */
	public PatrolController(IRController ir, MotorController motor, Timer timer) {
		super("Patrol"); // Adds name to a list of mode names
		this.motor = motor;
		this.ir = ir;
		this.getTimer = timer;
		this.random = new Random();
		devices.add(this.ir);
		devices.add(this.motor);
		devices.add(this.getTimer); // Devices this program uses, disables them
		// when you disable the program
	}

	/**
	 * Sharp turn left when something is in front Steer right or left when the
	 * way is clear
	 */
	@Override
	protected void action() {
		distance = ir.getDistance(); 
		timer = getTimer.getTimer(); 
		direction = random.nextInt(4) + 1;
		Doge.message(6, "Random: " + Integer.toString(direction));
		
		if (distance > 5 && distance <= 50) { //If something is in front
			if (lastTurn == 0) {
			motor.rollRight();
			}
			else if (lastTurn == 1) {
				motor.rollLeft();
			}
			while (distance > 5 && distance <= 50) { //Turns around
				Doge.message(4, "distance:" + Float.toString(distance));
				Delay.msDelay(1000);
				distance = ir.getDistance(); //TODO: Remember last turn
			}
		} else
			switch (direction) { //Switch for random movement orders
			case 1:
				motor.gentleLeft(700);
				lastTurn = 0;
				Doge.message(4, "Gentle left");
				Delay(distance, timer);
				break;

			case 2:
				motor.gentleRight(700);
				lastTurn = 1;
				Doge.message(4, "Gentle right");
				Delay(distance, timer);
				break;
				
			case 3:
				motor.sharpLeft(700);
				lastTurn = 0;
				Doge.message(4, "Sharp left");
				Delay(distance, timer);
				break;
				
			case 4:
				motor.sharpRight(700);
				lastTurn = 1;
				Doge.message(4, "Sharp right");
				Delay(distance, timer);
				break;
				
			}
	}
		
	public void Delay(float distance, int timer) {
		while (timer == getTimer.getTimer() && distance > 50) {
			distance = ir.getDistance();
			Delay.msDelay(10);
		}
	
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
