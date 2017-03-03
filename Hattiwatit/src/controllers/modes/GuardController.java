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
 * @param ir Uses IR sensor to see what is in front
 * @param motor Uses motor to move
 * @param timer Uses timer to alternate moving pattern
 */
	public GuardController(IRController ir, MotorController motor, Timer timer) {
		super("Guard");
		this.ir = ir;
		this.getTimer = timer;
		this.motor = motor;
		devices.add(this.ir);
		devices.add(this.getTimer);
		devices.add(this.motor); //Devices this program uses
	}

	@Override
	protected void action() {
		distance = ir.getDistance(); 
		timer = getTimer.getTimer();
		String msg = "";

		if (distance > 5 && distance <= 50) { //If something is in front
			motor.halt(); //Stop moving
			Doge.message(5, "Woof");
			do {
				distance = ir.getDistance();
				Sound.playSample(bark, 100); //Keep barking until the way is clear
				Delay.msDelay(10);
				msg = "distance:"+Float.toString(distance);
			} while (distance > 5 && distance <= 50);

		} else
			switch (timer) { //switch case uses timer to alternate moving
			case 1:
				motor.forward(); //forward
				msg = "Forward";
				while (timer == 1 && distance > 50) {
					timer = getTimer.getTimer();
					distance = ir.getDistance();
					Delay.msDelay(10);
				}
				break;

			case 2:
				motor.rollLeft(); //left
				msg = "Left";
				while (timer == 2 && distance > 50) {
					timer = getTimer.getTimer();
					distance = ir.getDistance();
					Delay.msDelay(10);
				}
				break;

			}
		Doge.message(4, msg);
		//TODO: Fine tune moving pattern, add different patterns that you can choose from
	}

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
