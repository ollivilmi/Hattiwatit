package controllers.modes;

import java.util.ArrayList;

import controllers.Controller;
import controllers.devices.DeviceController;

/**
 * Controller for the modes.
 *
 */
public abstract class ModeController extends Controller {
	/**
	 * Controller to control the execution of modes.
	 */
	private String modeName;

	/**
	 * A list of devices required by the mode.
	 */
	protected ArrayList<DeviceController> devices = new ArrayList<DeviceController>();

	/**
	 * @param modeName
	 *            The name of the mode
	 */
	public ModeController(String modeName) {
		this.modeName = modeName;
	}

	/**
	 * Disables the mode's devices.
	 */
	@Override
	public void disable() {
		super.disable();
		for (Controller dc : devices) {
			dc.disable();
		}
	}

	/**
	 * Enables the mode's devices.
	 */
	@Override
	public void enable() {
		super.enable();
		for (Controller dc : devices) {
			dc.enable();
		}
	}

	/**
	 * @return The current mode's name.
	 */
	public String getModeName() {
		return modeName;
	}
}
