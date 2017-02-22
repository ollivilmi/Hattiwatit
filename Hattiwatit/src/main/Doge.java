package main;

import java.util.ArrayList;

import controllers.devices.ColorController;
import controllers.devices.IRController;
import controllers.devices.MotorController;
import controllers.modes.FollowController;
import controllers.modes.ModeController;
import controllers.modes.PatrolController;
import controllers.modes.SmellController;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.utility.Delay;

public class Doge {
	private MotorController motor;
	private IRController ir;
	private ColorController color;

	private FollowController follower;
	private PatrolController patrol;
	private SmellController smell;

	private Menu menu;
	private ArrayList<ModeController> modeList;
	private ArrayList<String> modeNames;
	private String[] menuItems;
	private int selected,
				quit;

	private ModeController currentMode;

	public Doge(Port irPort, Port colorPort, Port motorR, Port motorL) {
		message(0, "Starting");
		message(1, "motors...");
		motor = new MotorController(motorR, motorL, 360);
		message(1, "IR...");
		ir = new IRController(irPort);
		message(1, "colors...");
		color = new ColorController(colorPort);

		motor.start();
		ir.start();
		color.start();

		message(1, "modes...");
		follower = new FollowController(ir, motor);
		patrol = new PatrolController(ir, motor);
		smell = new SmellController(color, motor);

		follower.start();
		patrol.start();
		smell.start();

		LCD.clear(1);
		message(0, "Creating menu...");
		modeList = new ArrayList<ModeController>();
		modeList.add(follower);
		modeList.add(patrol);
		modeList.add(smell);

		modeNames = new ArrayList<String>();
		for (ModeController mc : modeList) {
			modeNames.add(mc.getModeName());
		}
		quit = modeList.size();

		// menu includes all modes + Quit
		int menuSize = modeList.size() + 1;
		menuItems = modeNames.toArray(new String[menuSize]);
		menuItems[quit] = "Quit";

		menu = new Menu(menuItems, "Doge");

		LCD.clear(0);
	}

	public void loopMenu() {
		do {
			selected = menu.showMenu();
			selected = (selected == -1) ? quit : selected;

			if (selected == quit) {
				// Euthanize
				follower.terminate();
				patrol.terminate();
				smell.terminate();

				motor.terminate();
				ir.terminate();
				color.terminate();
			} else {
				currentMode = modeList.get(selected);
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
		} while (selected != quit);
	}

	public static void message(int row, String text) {
		LCD.clear(row);
		LCD.drawString(text, 0, row);
	}
}
