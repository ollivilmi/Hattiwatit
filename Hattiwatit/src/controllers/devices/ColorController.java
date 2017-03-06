package controllers.devices;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;

/**
 * Controller for the Color Sensor.
 */
public class ColorController extends SensorController {
	/**
	 * @param port
	 *            The port where the sensor is plugged into.
	 */
	public ColorController(Port port) {
		super(new EV3ColorSensor(port));
	}

	/**
	 * @return Ambient brightness.
	 */
	public float getAmbient() {
		return samples.get("Ambient")[0];
	}

	/**
	 * @return ColorID under the sensor.
	 * @see <a href=
	 *      "http://www.lejos.org/ev3/docs/lejos/robotics/Color.html">Color
	 *      (leJOS API Documentation)</a>
	 */
	public int getColorID() {
		return (int) samples.get("ColorID")[0];
	}

	/**
	 * @return Amount of reflected IR light.
	 */
	public float getRed() {
		return samples.get("Red")[0];
	}

	/**
	 * @return Amount of Red, Green and Blue light.
	 */
	public float[] getRGB() {
		return samples.get("RGB");
	}
}
