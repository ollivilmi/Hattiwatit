package controllers.devices;

import controllers.Controller;

public abstract class DeviceController extends Controller {
	protected abstract void cleanUp();

	@Override
	public void run() {
		super.run();
		cleanUp();
	}
}
