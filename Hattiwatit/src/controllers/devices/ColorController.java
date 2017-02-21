package controllers.devices;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;

public class ColorController extends SensorController {
	public ColorController(Port port) {
		super(new EV3ColorSensor(port));
	}

	public float getAmbient() {
		return samples.get("Ambient")[0];
	}

	public int getColorID() {
		return (int) samples.get("ColorID")[0];
	}

	public float getRed() {
		return samples.get("Red")[0];
	}

	public float[] getRGB() {
		return samples.get("RGB");
	}
}
