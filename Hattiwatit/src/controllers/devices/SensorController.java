package controllers.devices;

import java.util.HashMap;

import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.SensorMode;

public abstract class SensorController extends DeviceController {

	protected BaseSensor sensor;
	protected SensorMode sensorMode;
	protected String modeName;
	protected HashMap<String, float[]> samples;

	public SensorController(BaseSensor sensor) {
		this.sensor = sensor;

		String initialMode = this.sensor.getName();
		setMode(initialMode);

		samples = new HashMap<String, float[]>();

		// populate samples
		for (String m : sensor.getAvailableModes()) {
			int sampleSize = sensor.getMode(m).sampleSize();
			samples.put(m, new float[sampleSize]);
		}
	}

	@Override
	protected void action() {
		sensorMode.fetchSample(samples.get(modeName), 0);
	}

	@Override
	protected void cleanUp() {
		sensor.close();
	}

	public void setMode(String mode) {
		sensor.setCurrentMode(mode);
		sensorMode = sensor.getMode(mode);
		modeName = mode;
	}
}
