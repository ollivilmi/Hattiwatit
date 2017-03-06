package main;

import lejos.hardware.lcd.LCD;
import lejos.utility.TextMenu;

public class Menu {
	private TextMenu menu;

	public Menu(String[] menuItems, String menuTitle) {
		menu = new TextMenu(menuItems, 1, menuTitle);
	}

	public int showMenu() {
		int selection = menu.select();
		LCD.clear();

		return selection;
	}
}
