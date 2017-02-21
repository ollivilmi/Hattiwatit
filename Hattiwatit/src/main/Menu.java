package main;
import lejos.hardware.lcd.LCD;
import lejos.utility.TextMenu;

public class Menu {
	private String[] menuItems;
	private TextMenu menu;
	/**
	 * 
	 * @param menuItems
	 */
	public Menu(String[] menuItems) {
		this.menuItems = menuItems;
		menu = new TextMenu(this.menuItems);
		
	}

	// TODO: starting position as parameter 
	public int showMenu() {
		int selection;
		
		selection = menu.select();
		LCD.clear();
		
		return selection;
	}
}
