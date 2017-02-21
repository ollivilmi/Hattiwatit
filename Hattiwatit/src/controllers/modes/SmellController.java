package controllers.modes;

import controllers.devices.ColorController;
import controllers.devices.MotorController;
import lejos.hardware.lcd.LCD;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class SmellController extends ModeController {
	private MotorController motor;
	private ColorController color;
	private int interval = 10;

	public SmellController(ColorController color, MotorController motor) {
		super("Smell");
		this.motor = motor;
		this.color = color;
		devices.add(color);
		devices.add(motor);
	}

	@Override
	protected void action() {
		switch (color.getColorID()) {
		case Color.YELLOW:
			LCD.drawString("Pee", 0, 5);
			motor.halt();
			// Tail wagging here
			break;
		default:
			motor.forward();
			LCD.drawString("Seeking", 0, 5);
			break;
		}

		Delay.msDelay(interval);
		
		LCD.clear(5);
	}
	
	@Override
	public void enable() {
		color.setMode("ColorID");
		super.enable();
	}
}