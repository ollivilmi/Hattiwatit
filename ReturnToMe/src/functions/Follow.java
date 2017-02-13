package functions;
import lejos.utility.Delay;
import values.IR;

public class Follow extends Thread {
	private IR sensor;
	private Movement motor;
	private boolean stop = false;
	private int distance, bearing;
	public Follow (IR sensor, Movement motor) {
		this.sensor = sensor;
		this.motor = motor;
	}
	
	public void run() {
		while (this.stop != true) {
			
			this.distance = this.sensor.getDistance();
			this.bearing = this.sensor.getBearing();
			
			if (this.distance < 5 || ( this.distance == Float.POSITIVE_INFINITY && this.bearing == 0)) {
				this.motor.stop();			
			} else if (bearing < -2) {		
				this.motor.left();	
			} else if (bearing < 3 && bearing > -3) {	
				this.motor.forward();	
			} else if (bearing > 2) {	
				this.motor.right();				
			} 
			Delay.msDelay(10);
		}
	}	
	public void setStop() {
		this.stop = true;
	}
}
