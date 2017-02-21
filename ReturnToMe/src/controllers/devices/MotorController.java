package controllers.devices;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class MotorController extends DeviceController {
	private enum Direction {
		backward, forward
	}

	private Direction directionR = Direction.forward,
					  directionL = Direction.forward;
	private EV3LargeRegulatedMotor motorR,
								   motorL;
	private int speedR,
				speedL,
				defaultSpeed,
				interval = 10;

	public MotorController(Port right, Port left, int defaultSpeed) {
		this.motorR = new EV3LargeRegulatedMotor(right);
		this.motorL = new EV3LargeRegulatedMotor(left);
		this.defaultSpeed = defaultSpeed;
	}

	@Override
	protected void action() {
		this.motorR.setSpeed(speedR);

		switch (directionR) {
		case forward:
			this.motorR.forward();
			break;
		case backward:
			this.motorR.backward();
			break;
		}

		this.motorL.setSpeed(speedL);

		switch (directionL) {
		case forward:
			this.motorL.forward();
			break;
		case backward:
			this.motorL.backward();
			break;
		}

		Delay.msDelay(interval);
	}

	public void backward() {
		this.backward(this.defaultSpeed);
	}

	public void backward(int speed) {
		this.setSpeed(speed);
		this.directionR = Direction.forward;
		this.directionL = Direction.forward;
	}

	@Override
	protected void cleanUp() {
		this.motorR.close();
		this.motorL.close();
	}

	@Override
	public void disable() {
		this.halt();
		super.disable();
	}

	@Override
	public void enable() {
		this.speedR = this.defaultSpeed;
		this.speedL = this.defaultSpeed;
		super.enable();
	}

	public void forward() {
		this.forward(this.defaultSpeed);
	}

	public void forward(int speed) {
		this.setSpeed(speed);
		this.directionR = Direction.backward;
		this.directionL = Direction.backward;
	}

	public void halt() {
		this.setSpeed(0);
		this.motorR.stop(true);
		this.motorL.stop(true);
	}

	public void left() {
		this.left(defaultSpeed);
	}

	public void left(int speed) {
		this.setSpeed(speed);
		this.directionR = Direction.backward;
		this.directionL = Direction.forward;
	}

	public void right() {
		this.right(defaultSpeed);
	}

	public void right(int speed) {
		this.setSpeed(speed);
		this.directionR = Direction.forward;
		this.directionL = Direction.backward;
	}

	public void setSpeed(int speed) {
		this.speedR = speed;
		this.speedL = speed;
	}
}
