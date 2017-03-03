package main;

import java.util.ArrayList;

import controllers.ControllerManager;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

/**
 * Class for starting the devices and modes, and running the mode which the user
 * chooses.
 */
public class Doge {
	private ControllerManager cm;
	private Menu menu;
	private ArrayList<String> modeNames;
	private String[] menuItems;
	private int selected,
				quit;

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
	 * Starts the devices' and modes' threads, and shows the menu.
	 */
	public void start() {
		loopMenu();
	}

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
