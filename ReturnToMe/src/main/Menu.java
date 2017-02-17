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
		this.menu = new TextMenu(this.menuItems);
		
	}
	public int showMenu() {
		int selection;
		
		selection = this.menu.select();
		LCD.clear();
		
		return selection;
	}
}
