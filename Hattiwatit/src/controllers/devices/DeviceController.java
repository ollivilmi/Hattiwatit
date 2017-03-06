package controllers.devices;

import controllers.Controller;

/**
 * 
 * Controller for the devices.
 *
 */
public abstract class DeviceController extends Controller {
	/**
	 * Method to run just before terminating the controller.
	 */
	protected abstract void cleanUp();

	@Override
	public void run() {
		super.run();
		cleanUp();
	}
}
