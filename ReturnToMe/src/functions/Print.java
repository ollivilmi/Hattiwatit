package functions;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import values.IR;

public class Print extends Thread {
	private IR sensor;
	private boolean stop = false;
	
	public Print(IR sensor) {
		this.sensor = sensor;
	}
	public void run() {
		while (this.stop != true) {		
				
		LCD.clear(0);
		LCD.clear(1);
		LCD.drawString(String.format("bearing: %d", this.sensor.getBearing()), 0, 0);
		LCD.drawString(String.format("distance: %d", this.sensor.getDistance()), 0, 1);
		Delay.msDelay(50);
		
		}
	}
	public void setStop() {
		this.stop = true;
	}
}
