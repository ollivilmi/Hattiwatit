package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import controllers.devices.ColorController;
import controllers.devices.DeviceController;
import controllers.devices.IRController;
import controllers.devices.MotorController;
import controllers.devices.TouchController;
import controllers.modes.FollowController;
import controllers.modes.GuardController;
import controllers.modes.ModeController;
import controllers.modes.PatrolController;
import functions.Tail;
import functions.Timer;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import main.Doge;

/**
 * Starts and stops the devices' and modes' threads. Use
 * {@link #startMode(String)} to start a mode.
 */
public class ControllerManager {
	// modes
	private MotorController motor;
	private IRController ir;
	private ColorController color;
	private TouchController touch;

	// functions
	private Timer timer;
	private Tail tail;

	// devices
	private FollowController follower;
	private PatrolController patrol;
	private GuardController guard;

	/**
	 * List of devices.
	 */
	private ArrayList<DeviceController> deviceList;

	/**
	 * List of modes mapped to their names.
	 */
	private HashMap<String, ModeController> modeList;

	/**
	 * 
	 * @param irPort
	 *            Port where the IR receiver is connected.
	 * @param colorPort
	 *            Port where the color sensor is connected.
	 * @param motorR
	 *            Port where the right motor is connected.
	 * @param motorL
	 *            Port where the right motor is connected.
	 * @param touchPort
	 *            Port where the touch sensor is connected.
	 * @param motorT
	 *            Port where the tail motor is connected.
	 */
	public ControllerManager(Port irPort, Port colorPort, Port motorR, Port motorL, Port touchPort, Port motorT) {
		Doge.message(0, "Starting");
		Doge.message(1, "devices...");
		motor = new MotorController(motorR, motorL, 360);
		ir = new IRController(irPort);
		color = new ColorController(colorPort);
		touch = new TouchController(touchPort);

		timer = new Timer();
		tail = new Tail(motorT, touch);

		Doge.message(1, "modes");
		follower = new FollowController(ir, motor);
		patrol = new PatrolController(ir, motor, timer, color, tail);
		guard = new GuardController(ir, motor, timer);

		// tail runs constantly in the background
		tail.enable();

		// populate the device list
		deviceList = new ArrayList<DeviceController>();
		for (DeviceController device : new DeviceController[] { motor, ir, color, timer, tail, touch }) {
			deviceList.add(device);
		}

		// populate the mode list
		modeList = new HashMap<String, ModeController>();
		for (ModeController mode : new ModeController[] { follower, patrol, guard }) {
			modeList.put(mode.getModeName(), mode);
		}

		LCD.clear();
	}

	/**
	 * Get a list of available modes.
	 * 
	 * @return List of available mode.
	 */
	public Set<String> getModeList() {
		return modeList.keySet();
	}

	/**
	 * Starts the devices' and modes' threads.
	 */
	public void start() {
		for (Runnable device : deviceList) {
			new Thread(device).start();
		}

		for (Runnable mode : modeList.values()) {
			new Thread(mode).start();
		}
	}

	/**
	 * Starts a mode.
	 * 
	 * @param name
	 *            Name of the mode.
	 */
	public void startMode(String name) {
		modeList.get(name).enable();
	}

	/**
	 * Stops the devices' and modes' threads.
	 */
	public void stop() {
		for (Controller device : deviceList) {
			device.terminate();
		}

		for (Controller mode : modeList.values()) {
			mode.terminate();
		}
	}

	/**
	 * Stop a mode.
	 * 
	 * @param name
	 *            Name of the mode.
	 */
	public void stopMode(String name) {
		modeList.get(name).disable();
	}
}
