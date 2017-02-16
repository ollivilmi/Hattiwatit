package values;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;
import lejos.hardware.port.Port;

public class IRSeeker extends Thread {
	private EV3IRSensor sensor;
	private SensorMode seeker;
	private float[] samples;
	private float bearing, distance;
	private boolean stop = false;
	
	public IRSeeker(Port port) {
		this.sensor = new EV3IRSensor(port);
		this.seeker = this.sensor.getSeekMode();
		this.samples = new float[seeker.sampleSize()];
	}
	public void run() {
		while (this.stop != true) {
			this.seeker.fetchSample(this.samples, 0);
			this.bearing = this.samples[0];
			this.distance = this.samples[1];
			Delay.msDelay(10);
		}	
	}
	public int getBearing() {
		return (int)this.bearing;
	}
	public int getDistance() {
		return (int)this.distance;
	}
	public void setStop() {
		this.stop = true;
	}
}
