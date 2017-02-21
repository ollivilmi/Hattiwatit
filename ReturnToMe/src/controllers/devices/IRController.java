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
		return this.samples.get("Seek")[0];
	}

	public int getCommand() {
		return ((EV3IRSensor) this.sensor).getRemoteCommand(this.channel);
	}

	public float getDistance() {
		return this.samples.get("Distance")[0];
	}

	public float getSeekDistance() {
		return this.samples.get("Seek")[1];
	}
}
