package controllers.devices;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;

public class IRController extends SensorController {
	private int channel;

	public IRController(Port port) {
		this(port, 0);
	}

	public IRController(Port port, int channel) {
		super(new EV3IRSensor(port));
		this.channel = channel;
	}

	public float getSeekBearing() {
		return samples.get("Seek")[0];
	}

	public int getCommand() {
		return ((EV3IRSensor) sensor).getRemoteCommand(channel);
	}

	public float getDistance() {
		return samples.get("Distance")[0];
	}

	public float getSeekDistance() {
		return samples.get("Seek")[1];
	}
}
