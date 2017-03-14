package functions;
import controllers.devices.DeviceController;
import lejos.utility.Delay;

public class Timer extends DeviceController {
	private int timer, interval;
	public Timer ()  {
		this.timer = 1;
		this.interval = 2000;
	}
	/**
	 * Alternates int values 1 and 2 by using an interval
	 * (Default 2 seconds)
	 */
	@Override
	protected void action() {
		while (timer <3) {
		Delay.msDelay(interval);
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
	/**
	 * Change the interval of the timer
	 * @param interval milliseconds
	 */
	public void changeTimer(int interval) {
		this.interval = interval;
	}
}
