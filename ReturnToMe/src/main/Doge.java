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
		this.message(0, "Starting");
		this.message(1, "motors...");
		this.motor = new MotorController(motorR, motorL, 360);
		this.message(1, "IR...");
		this.ir = new IRController(irPort);
		this.message(1, "colors...");
		this.color = new ColorController(colorPort);

		this.motor.start();
		this.ir.start();
		this.color.start();

		this.message(1, "modes...");
		this.follower = new FollowController(this.ir, this.motor);
		this.patrol = new PatrolController(this.ir, this.motor);
		this.smell = new SmellController(this.color, this.motor);

		this.follower.start();
		this.patrol.start();
		this.smell.start();

		LCD.clear(1);
		this.message(0, "Creating menu...");
		this.modeList = new ArrayList<ModeController>();
		this.modeList.add(this.follower);
		this.modeList.add(this.patrol);
		this.modeList.add(this.smell);

		this.modeNames = new ArrayList<String>();
		for (ModeController mc : modeList) {
			this.modeNames.add(mc.getModeName());
		}
		this.quit = this.modeList.size();

		this.menuItems = modeNames.toArray(new String[this.modeList.size() + 1]);
		this.menuItems[this.quit] = "Quit";

		this.menu = new Menu(this.menuItems);

		LCD.clear(0);
	}

	public void loopMenu() {
		do {
			this.selected = this.menu.showMenu();
			this.selected = (this.selected == -1) ? this.quit : this.selected;

			if (this.selected == this.quit) {
				// Euthanize
				this.follower.terminate();
				this.patrol.terminate();
				this.smell.terminate();

				this.motor.terminate();
				this.ir.terminate();
				this.color.terminate();
			} else {
				this.currentMode = this.modeList.get(selected);
				this.currentMode.enable();

				// TODO: tell the user how to quit
				LCD.drawString("Running " + this.currentMode.getModeName(), 0, 0);

				while (Button.ESCAPE.isUp()) {
					Delay.msDelay(50);
				}
				LCD.clear(0);

				this.currentMode.disable();

				while (Button.ESCAPE.isDown()) {
					Delay.msDelay(50);
				}
			}
		} while (this.selected != this.quit);
	}

	private void message(int row, String text) {
		LCD.clear(row);
		LCD.drawString(text, 0, row);
	}
}
