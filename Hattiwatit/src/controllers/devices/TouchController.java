package controllers.devices;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
/**
 * Controller for the Touch Sensor
 *
 */
public class TouchController extends SensorController {
/**
 * @param port
 * The port where the sensor is plugged in
 */
	public TouchController(Port port) {
		super(new EV3TouchSensor(port));
	}
	
	/**
	 *  Touch sensor press
	 * @return
	 */
	public float getPress(){
		return samples.get("Touch")[0];
	}

}
