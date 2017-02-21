package controllers.devices;

import java.util.HashMap;

import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public abstract class SensorController extends DeviceController {

	protected BaseSensor sensor;
	protected SensorMode sensorMode;
	protected String modeName;
	protected HashMap<String, float[]> samples;
	protected int interval = 10;

	public SensorController(BaseSensor sensor) {
		this.sensor = sensor;

		String initialMode = this.sensor.getName();
		this.setMode(initialMode);
		
		this.samples = new HashMap<String, float[]>();

		// populate samples
		for (String m : sensor.getAvailableModes()) {
			int sampleSize = sensor.getMode(m).sampleSize();
			samples.put(m, new float[sampleSize]);
		}
	}
	
	@Override
	protected void action() {
		this.sensorMode.fetchSample(samples.get(modeName), 0);
		Delay.msDelay(interval);
	}

	@Override
	protected void cleanUp() {
		this.sensor.close();
	}

	public void setMode(String mode) {
		this.sensor.setCurrentMode(mode);
		this.sensorMode = this.sensor.getMode(mode);
		this.modeName = mode;
	}
}
