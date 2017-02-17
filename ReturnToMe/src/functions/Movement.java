package functions;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;


public class Movement {
	private EV3LargeRegulatedMotor right, left;
	
	public Movement (Port right, Port left, int speed) {
		this.right = new EV3LargeRegulatedMotor(right);
		this.left = new EV3LargeRegulatedMotor(left);
		this.right.setSpeed(speed);
		this.left.setSpeed(speed);
	}
	public void forward() {
		this.right.backward();
		this.left.backward();
		//Delay.msDelay(delay);
	}
	public void left() {
		this.right.backward();
		this.left.forward();
		//Delay.msDelay(delay);
	}
	public void right() {
		this.right.forward();
		this.left.backward();
		//Delay.msDelay(delay);
	}
	public void backward() {
		this.right.forward();
		this.left.forward();
		//Delay.msDelay(delay);
	}
	public void stop() {
		this.right.stop(true);
		this.left.stop(true);
	}
	
}
