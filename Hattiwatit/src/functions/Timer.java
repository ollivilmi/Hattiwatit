package functions;
import controllers.devices.DeviceController;
import lejos.utility.Delay;

public class Timer extends DeviceController {
	private int timer;
	/**
	 * This thread alternates int values 1-2 with 
	 * a delay of 1 second to keep the program responsive
	 */
	public Timer ()  {
		this.timer = 1;
	}
	@Override
	protected void action() {
		while (timer <3) {
		Delay.msDelay(2000);
		timer++;
		}
		if (timer >= 3) {
			timer = 1;
		}
	}
	/**
	 * 
	 * @return Returns 1-2, changes every second
	 */
	public int getTimer() {
		return this.timer;
	}
	@Override
	public void cleanUp() {
	}
}
