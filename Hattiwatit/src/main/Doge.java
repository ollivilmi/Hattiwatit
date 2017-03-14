package main;

import java.util.ArrayList;

import controllers.ControllerManager;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

/**
 * Shows the menu to the user and runs the mode of their choosing.
 */
public class Doge {
	private ControllerManager cm;
	private Menu menu;

	/**
	 * Names of the modes which are show in the menu.
	 */
	private ArrayList<String> modeNames;

	/**
	 * List of items shown in the menu.
	 */
	private String[] menuItems;

	/**
	 * Index of the selected menu item.
	 */
	private int selected;

	/**
	 * Index of the Quit-option in the menu.
	 */
	private int quit;

	/**
	 * 
	 * @param irPort
	 *            Port where the IR receiver is connected.
	 * @param colorPort
	 *            Port where the color sensor is connected.
	 * @param motorR
	 *            Port where the right motor is connected.
	 * @param motorL
	 *            Port where the left motor is connected.
	 * @param touchPort
	 *            Port where the touch sensor is connected.
	 * @param motorT
	 *            Port where the tail motor is connected.
	 */
	public Doge(Port irPort, Port colorPort, Port motorR, Port motorL, Port touchPort, Port motorT) {
		cm = new ControllerManager(irPort, colorPort, motorR, motorL, touchPort, motorT);
		cm.start();

		modeNames = new ArrayList<>(cm.getModeList());

		// menu includes all modes + Quit
		int menuSize = modeNames.size() + 1;
		menuItems = modeNames.toArray(new String[menuSize]);

		quit = menuSize - 1;
		menuItems[quit] = "Quit";

		menu = new Menu(menuItems, "Doge");

		LCD.clear(7);
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
				cm.stop();
			} else {
				runMode(modeNames.get(selected));
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
	 * Starts looping the menu.
	 */
	public void start() {
		loopMenu();
	}

	/**
	 * Start a mode.
	 * 
	 * @param mode
	 *            Mode to run.
	 */
	private void runMode(String mode) {
		cm.startMode(mode);

		message(0, "Running " + mode);
		message(7, "Escape to quit");

		while (Button.ESCAPE.isUp()) {
			Delay.msDelay(50);
		}

		cm.stopMode(mode);

		LCD.clear();

		while (Button.ESCAPE.isDown()) {
			Delay.msDelay(50);
		}
	}
}
