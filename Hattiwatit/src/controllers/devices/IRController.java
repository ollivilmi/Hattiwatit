package controllers.devices;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;

/**
 * Controller for the IR receiver.
 */
public class IRController extends SensorController {
	private int channel;

	public IRController(Port port) {
		this(port, 0);
	}

	/**
	 * 
	 * @param port
	 *            The port where the sensor is plugged into.
	 * @param channel
	 *            The channel to use for receiving commands.
	 */
	public IRController(Port port, int channel) {
		super(new EV3IRSensor(port));
		this.channel = channel;
	}

	/**
	 * @return The bearing of the remote
	 */
	public float getSeekBearing() {
		return samples.get("Seek")[0];
	}

	/**
	 * @return The currently pressed button on the remote
	 */
	public int getCommand() {
		return ((EV3IRSensor) sensor).getRemoteCommand(channel);
	}

	/**
	 * @return Distance from the nearest object in front of the sensor
	 */
	public float getDistance() {
		return samples.get("Distance")[0];
	}

	/**
	 * @return The distance from the remote
	 */
	public float getSeekDistance() {
		return samples.get("Seek")[1];
	}
}
