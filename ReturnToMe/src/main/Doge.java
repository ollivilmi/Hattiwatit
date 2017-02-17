package main;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;
import controllers.FollowController;
import controllers.IRController;
import controllers.PatrolController;
import controllers.SmellController;
import functions.Movement;


public class Doge {
	private Menu menu;
	private FollowController follower;
	private int selected;
	private SmellController smell;
	private PatrolController patrol;
	private IRController ir;
	private Movement motor;
	private EV3ColorSensor color;
	private String[] menuItems = {
			"Euthanize",
			"Follow",
			"Patrol",
			"Seek smells"
	};

	
	public Doge (Port irport, Port colorport, Port motorR, Port motorL) {
		this.motor = new Movement(motorR, motorL, 360);
		this.ir = new IRController(irport);
		this.color = new EV3ColorSensor(colorport);
		this.menu = new Menu(menuItems);
		this.ir.start();
		this.follower = new FollowController(this.ir, this.motor);
		this.patrol = new PatrolController(this.ir, this.motor);
		this.smell = new SmellController(this.color, this.motor);
		this.follower.start();
		this.patrol.start();
		this.smell.start();
		}
	
	public void loopMenu() {
		do {
			selected = this.menu.showMenu();
			
			
			switch (selected) {
			case 0:
				// Euthanize
				this.follower.terminate();
				this.patrol.terminate();
				this.smell.terminate();
				this.ir.terminate();
				break;
				
			case 1:
				// Follow
				this.ir.enable();
				this.ir.setMode(1);
				this.follower.enable();
				while (Button.ESCAPE.isUp()) {
					Delay.msDelay(50);
					LCD.drawString("Running", 0, 0);
				}
				LCD.clear(0);
				this.follower.disable();
				this.ir.disable();
				break;
				
			case 2:
				// Patrol
				this.ir.enable();
				this.ir.setMode(0);
				this.patrol.enable();
				while (Button.ESCAPE.isUp()) {
					Delay.msDelay(50);
					LCD.drawString("Running", 0, 0);
				}
				LCD.clear(0);
				this.patrol.disable();
				this.ir.disable();
				break;
				
			case 3:
				// Seek smells
				this.smell.enable();
				while (Button.ESCAPE.isUp()) {
					Delay.msDelay(50);
					LCD.drawString("Running", 0, 0);
				}
				LCD.clear(0);
				this.smell.disable();
			}
		} while (selected != 0);
	}
}
