package controllers.modes;

import controllers.devices.ColorController;
import controllers.devices.IRController;
import controllers.devices.MotorController;
import functions.Tail;
import functions.Timer;
import lejos.robotics.Color;
import lejos.utility.Delay;
import main.Doge;
import java.util.Random;

public class PatrolController extends ModeController {
	private MotorController motor;
	private IRController ir;
	private ColorController color;
	private float distance;
	private int timer, direction, colorID;
	private int lastTurn = 0;
	private Timer getTimer;
	private Random random;
	private Tail tail;

	/**
	 * 
	 * @param ir
	 *            sensor Uses IR to measure distance
	 * @param motor
	 *            Uses motor to move
	 * @param Timer
	 *            Uses timer to alternate moving patterns
	 */
	public PatrolController(IRController ir, MotorController motor, Timer timer, ColorController color, Tail tail) {
		super("Patrol"); // Adds name to a list of mode names
		this.motor = motor;
		this.ir = ir;
		this.color = color;
		this.getTimer = timer;
		this.random = new Random();
		this.tail = tail;
		devices.add(this.ir);
		devices.add(this.motor);
		devices.add(this.getTimer);
		devices.add(this.color);
		// Devices this program uses, disables them
		// when you disable the program
	}

	/**
	 * If on yellow, stop and wag tail. If not, and something is in front, turn in the opposite
	 * direction of the last turn. Alternate between random movement orders (that are all left
	 * or right) every 2 seconds. (Timer can be changed in Timer.java)
	 */
	@Override
	protected void action() {
		distance = ir.getDistance();
		timer = getTimer.getTimer();
		colorID = color.getColorID();
		direction = random.nextInt(4) + 1;
		Doge.message(6, "Random: " + Integer.toString(direction));

		if (colorID == Color.YELLOW) { // If sensor is on yellow, stop motor
			motor.halt();
			tail.wagTail(4,700);
			while (colorID == Color.YELLOW) {
				colorID = color.getColorID();
				Delay.msDelay(500);
			}
		} else if (distance > 5 && distance <= 50) { //If something is in front, change direction
			if (lastTurn == 0) { 
				motor.rollRight();
			} else if (lastTurn == 1) {
				motor.rollLeft();
			}
			while (distance > 5 && distance <= 50 && colorID != Color.YELLOW) { // Delay to to
				Delay.msDelay(1000);										//give some time
				distance = ir.getDistance();								//to turn
				colorID = color.getColorID();
			}
		} else
			switch (direction) { // Switch for random movement orders
			case 1:
				motor.gentleLeft(700);
				lastTurn = 0;
				Doge.message(4, "Gentle left");
				Delay(distance, timer, colorID);
				break;

			case 2:
				motor.gentleRight(700);
				lastTurn = 1;
				Doge.message(4, "Gentle right");
				Delay(distance, timer, colorID);
				break;

			case 3:
				motor.sharpLeft(700);
				lastTurn = 0;
				Doge.message(4, "Sharp left");
				Delay(distance, timer, colorID);
				break;

			case 4:
				motor.sharpRight(700);
				lastTurn = 1;
				Doge.message(4, "Sharp right");
				Delay(distance, timer, colorID);
				break;

			}
	}
/**
 * Uses this delay to alternate moving patterns and check sensors.
 * @param distance current distance 
 * @param timer current timer
 * @param colorID current color
 */
	public void Delay(float distance, int timer, int colorID) { 
		while (timer == getTimer.getTimer() && distance > 50 && colorID != Color.YELLOW) {
			distance = ir.getDistance();
			colorID = color.getColorID();
			Delay.msDelay(10); //Reduces copy pasta, while moving keeps checking sensors
		}					   //and timers
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
	public void disable() {
		distance = 0;
		super.disable();
	}
}
