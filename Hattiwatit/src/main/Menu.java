package main;

import lejos.hardware.lcd.LCD;
import lejos.utility.TextMenu;

/**
 * Stores a menu and its items.
 */
public class Menu {
	private TextMenu menu;

	public Menu(String[] menuItems, String menuTitle) {
		menu = new TextMenu(menuItems, 1, menuTitle);
	}

	/**
	 * Shows the menu in the Doge's LCD screen, lets the user to select an item,
	 * and returns the item's index. Clears the screen after the selection has
	 * been made.
	 * 
	 * @return Zero-based index of the selected item.
	 */
	public int showMenu() {
		int selection = menu.select();
		LCD.clear();

		return selection;
	}
}
