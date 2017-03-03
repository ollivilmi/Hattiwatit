package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import controllers.devices.ColorController;
import controllers.devices.DeviceController;
import controllers.devices.IRController;
import controllers.devices.MotorController;
import controllers.modes.FollowController;
import controllers.modes.ModeController;
import controllers.modes.PatrolController;
import controllers.modes.SmellController;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import main.Doge;

public class ControllerManager {
	private MotorController motor;
	private IRController ir;
	private ColorController color;

	private FollowController follower;
	private PatrolController patrol;
	private SmellController smell;

	private ArrayList<DeviceController> deviceList;
	private HashMap<String, ModeController> modeList;

	public ControllerManager(Port irPort, Port colorPort, Port motorR, Port motorL) {
		Doge.message(0, "Starting");
		Doge.message(0, "devices...");
		motor = new MotorController(motorR, motorL, 360);
		ir = new IRController(irPort);
		color = new ColorController(colorPort);

		deviceList = new ArrayList<DeviceController>();
		deviceList.add(motor);
		deviceList.add(ir);
		deviceList.add(color);

		Doge.message(0, "modes");
		follower = new FollowController(ir, motor);
		patrol = new PatrolController(ir, motor);
		smell = new SmellController(color, motor);

		modeList = new HashMap<String, ModeController>();
		for (ModeController mode : new ModeController[] { follower, patrol, smell }) {
			modeList.put(mode.getModeName(), mode);
		}
		
		LCD.clear(0);
	}

	public Set<String> getModeList() {
		return modeList.keySet();
	}

	public void start() {
		for (Runnable device : deviceList) {
			new Thread(device).start();
		}

		for (Runnable mode : modeList.values()) {
			new Thread(mode).start();
		}
	}

	public void startMode(String name) {
		modeList.get(name).enable();
	}

	public void stop() {
		for (Controller device : deviceList) {
			device.terminate();
		}

		for (Controller mode : modeList.values()) {
			mode.terminate();
		}
	}

	public void stopMode(String name) {
		modeList.get(name).disable();
	}
}
