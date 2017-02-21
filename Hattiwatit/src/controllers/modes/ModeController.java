package controllers.modes;

import java.util.ArrayList;

import controllers.Controller;
import controllers.devices.DeviceController;

public abstract class ModeController extends Controller {
	private String modeName;
	protected ArrayList<DeviceController> devices = new ArrayList<DeviceController>();

	public ModeController(String modeName) {
		this.modeName = modeName;
	}

	@Override
	public void disable() {
		super.disable();
		for (Controller dc : devices) {
			dc.disable();
		}
	}

	@Override
	public void enable() {
		super.enable();
		for (Controller dc : devices) {
			dc.enable();
		}
	}

	public String getModeName() {
		return modeName;
	}
}
