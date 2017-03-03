package controllers.modes;
import controllers.devices.ColorController;
import controllers.devices.MotorController;
import lejos.robotics.Color;
import main.Doge;

public class SmellController extends ModeController {
	private MotorController motor;
	private ColorController color;

	/**
	 * 
	 * @param color Uses color sensor to find yellow
	 * @param motor Uses motors to move
	 */
	public SmellController(ColorController color, MotorController motor) {
		super("Smell");
		this.motor = motor;
		this.color = color;
		devices.add(color);
		devices.add(motor);
	}
/**
 * Stops the motor when on yellow, goes forward on any other color
 */
	@Override
	protected void action() {
		switch (color.getColorID()) {
		case Color.YELLOW:
			Doge.message(5, "Pee"); 
			motor.halt();
			// Tail wagging here
			break;
		default:
			motor.forward();
			Doge.message(5, "Seeking");
			break;
		}
	}
/**
 * Changes colorsensor to recognize colors when using this mode
 */
	@Override
	public void enable() {
		color.setMode("ColorID");
		super.enable();
	}
}