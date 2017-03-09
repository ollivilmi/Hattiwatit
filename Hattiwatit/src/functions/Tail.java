package functions;

import controllers.devices.DeviceController;
import controllers.devices.TouchController;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;

public class Tail extends DeviceController {
	private int count = 0;
	private EV3MediumRegulatedMotor motor;
	private TouchController touch;

	public Tail(Port motorT, TouchController touch) {
		this.motor = new EV3MediumRegulatedMotor(motorT);
		this.touch = touch;
		touch.enable();
	}

	@Override
	public void action() {
		motor.setSpeed(600);
		while (count == 0 && alive == true) {
			if (touch.getPress() == 1) {
				count = 4;
			}
		}
		motor.rotate(-60);
		for (int x = 0; x < count; x++) {
			motor.rotate(120);
			motor.rotate(-120);
		}
		motor.rotate(60);
		count = 0;
	}
	
	public void setCount(int newCount) {
		count = newCount;
	}
	
	@Override
	protected void cleanUp() {
	}

	@Override
	public void terminate() {
		motor.close();
		super.terminate();
	}
}