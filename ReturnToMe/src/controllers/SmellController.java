package controllers;

import functions.Movement;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class SmellController extends DeviceController{
	private Movement motor;
	private EV3ColorSensor color;
	//private Smell smell;
	
	public SmellController(EV3ColorSensor color, Movement motor) {
		this.motor = motor;
		this.color = color;
		//this.smell = new Smell(this.color, this.motor);
	}
	
	public void action () {
		switch (color.getColorID()) {
		case Color.YELLOW:
			LCD.clear(5);
			LCD.drawString("Pee", 0, 5);
			motor.stop();
			//Tail wagging here
			break;
		default:	
			this.motor.forward();
			LCD.clear(5);
			LCD.drawString("Seeking", 0, 5);
			break;
		}
		
		Delay.msDelay(10);
	}
	public void cleanUp() {
		this.color.close();
	}
}