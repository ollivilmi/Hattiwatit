package main;

import java.util.ArrayList;

import controllers.Controller;
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
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

/**
 * Class for starting the devices and modes, and running the mode which the user
 * chooses.
 */
public class Doge {
	private MotorController motor;
	private IRController ir;
	private ColorController color;
	private TouchController touch;

	private FollowController follower;
	private PatrolController patrol;
	private GuardController guard;
	private Tail tail;
	private Timer timer;

	private Menu menu;

	/**
	 * List of devices which are started when booting, and stopped when exiting.
	 */
	private ArrayList<DeviceController> deviceList;

	/**
	 * List of modes which are shown in the menu.
	 */
	private ArrayList<ModeController> modeList;

	/**
	 * The names of the modes which are shown in the menu.
	 */
	private ArrayList<String> modeNames;

	/**
	 * All items shown in the menu which include all the modes and the
	 * Quit-option.
	 */
	private String[] menuItems;

	/**
	 * Variable for the selected menu item.
	 */
	private int selected;

	/**
	 * Variable to store the Quit-option's position in the menu.
	 */
	private int quit;

	/**
	 * @param irPort
	 *            Port where the IR sensor is connected
	 * @param colorPort
	 *            Port where the color sensor is connected
	 * @param motorR
	 *            Port where the right motor is connected
	 * @param motorL
	 *            Port where the left motor is connected
	 * @param touchPort
	 *            Port where the touch sensor is connected
	 * @param motorT
	 *            Port where the tail motor is connected
	 */
	public Doge(Port irPort, Port colorPort, Port motorR, Port motorL, Port touchPort, Port motorT) {
		message(0, "Starting");
		message(1, "devices...");
		motor = new MotorController(motorR, motorL, 360);
		ir = new IRController(irPort);
		color = new ColorController(colorPort);
		timer = new Timer();
		touch = new TouchController(touchPort);
		tail = new Tail(motorT, touch);

		deviceList = new ArrayList<DeviceController>();
		deviceList.add(motor);
		deviceList.add(ir);
		deviceList.add(color);
		deviceList.add(timer);
		deviceList.add(tail);
		deviceList.add(touch);

		message(1, "modes...");
		follower = new FollowController(ir, motor);
		patrol = new PatrolController(ir, motor, timer, color, tail);
		guard = new GuardController(ir, motor, timer);

		message(1, "tail...");
		tail.enable();

		message(1, "menu...");
		modeList = new ArrayList<ModeController>();
		modeList.add(follower);
		modeList.add(patrol);
		modeList.add(guard);

		modeNames = new ArrayList<String>();
		for (ModeController mc : modeList) {
			modeNames.add(mc.getModeName());
		}

		// menu includes all modes + Quit
		int menuSize = modeList.size() + 1;
		menuItems = modeNames.toArray(new String[menuSize]);

		quit = menuSize - 1;
		menuItems[quit] = "Quit";

		menu = new Menu(menuItems, "Doge");

		LCD.clear();
	}

	/**
	 * Shows the menu and lets the user choose the mode to run. Exits when
	 * either Quit is selected or ESCAPE is pressed.
	 */
	private void loopMenu() {
		do {
			selected = menu.showMenu();
			selected = (selected < 0) ? quit : selected;

			if (selected == quit) {
				// Euthanize
				stop();
			} else {
				runMode(modeList.get(selected));
			}
		} while (selected != quit);
	}

	/**
	 * Clears a specific row in the Doge's LCD screen, and draws a message into
	 * it.
	 * 
	 * @param row
	 *            Row where the message is shown.
	 * @param text
	 *            Text to be displayed.
	 */
	public static void message(int row, String text) {
		LCD.clear(row);
		LCD.drawString(text, 0, row);
	}

	/**
	 * Starts the devices' and modes' threads, and shows the menu.
	 */
	public void start() {
		for (Runnable device : deviceList) {
			new Thread(device).start();
		}

		for (Runnable mode : modeList) {
			new Thread(mode).start();
		}

		loopMenu();
	}

	/**
	 * Runs a mode.
	 * 
	 * @param currentMode
	 *            Mode to run.
	 */
	private void runMode(ModeController currentMode) {
		currentMode.enable();

		message(0, "Running " + currentMode.getModeName());
		message(7, "Escape to quit");

		while (Button.ESCAPE.isUp()) {
			Delay.msDelay(50);
		}

		currentMode.disable();

		LCD.clear();

		while (Button.ESCAPE.isDown()) {
			Delay.msDelay(50);
		}
	}

	/**
	 * Stops the devices' and modes' threads.
	 */
	private void stop() {
		for (Controller device : deviceList) {
			device.terminate();
		}

		for (Controller mode : modeList) {
			mode.terminate();
		}
	}
}
