package controllers;

// TODO: implements Runnable
public abstract class Controller extends Thread {
	protected boolean alive = true;
	protected boolean enabled = false;

	protected abstract void action();

	public void terminate() {
		this.alive = false;
	}

	public void enable() {
		this.enabled = true;
	}

	public void disable() {
		this.enabled = false;
	}

	@Override
	public void run() {
		while (this.alive) {
			if (this.enabled) {
				this.action();
			}
		}
	}
}