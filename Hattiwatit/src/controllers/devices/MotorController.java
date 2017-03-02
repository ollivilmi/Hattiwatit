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
				defaultSpeed;

	public MotorController(Port right, Port left, int defaultSpeed) {
		motorR = new EV3LargeRegulatedMotor(right);
		motorL = new EV3LargeRegulatedMotor(left);
		this.defaultSpeed = defaultSpeed;
	}

	@Override
	protected void action() {
		motorR.setSpeed(speedR);

		switch (directionR) {
		case forward:
			motorR.backward();
			break;
		case backward:
			motorR.forward();
			break;
		}

		motorL.setSpeed(speedL);

		switch (directionL) {
		case forward:
			motorL.backward();
			break;
		case backward:
			motorL.forward();
			break;
		}
	}

	public void backward() {
		this.backward(defaultSpeed);
	}

	public void backward(int speed) {
		setSpeed(speed);
		directionR = Direction.backward;
		directionL = Direction.backward;
	}

	@Override
	protected void cleanUp() {
		motorR.close();
		motorL.close();
	}

	@Override
	public void disable() {
		// fixes one motor keeps running
		super.disable();
		Delay.msDelay(interval);
		halt();
	}

	@Override
	public void enable() {
		speedR = defaultSpeed;
		speedL = defaultSpeed;
		super.enable();
	}

	public void forward() {
		this.forward(defaultSpeed);
	}

	public void forward(int speed) {
		setSpeed(speed);
		directionR = Direction.forward;
		directionL = Direction.forward;
	}

	public void halt() {
		setSpeed(0);
		motorR.stop(true);
		motorL.stop(true);
	}

	public void rollLeft() {
		this.rollLeft(defaultSpeed);
	}

	public void rollLeft(int speed) {
		setSpeed(speed);
		directionR = Direction.forward;
		directionL = Direction.backward;
	}

	public void rollRight() {
		this.rollRight(defaultSpeed);
	}

	public void rollRight(int speed) {
		setSpeed(speed);
		directionR = Direction.backward;
		directionL = Direction.forward;
	}

	public void setSpeed(int speed) {
		speedR = speed;
		speedL = speed;
	}
}
