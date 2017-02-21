package controllers;

// TODO: implements Runnable
public abstract class Controller extends Thread {
	protected boolean alive = true;
	protected boolean enabled = false;

	protected abstract void action();

	public void terminate() {
		alive = false;
	}

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	@Override
	public void run() {
		while (alive) {
			if (enabled) {
				action();
			}
		}
	}
}