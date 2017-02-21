package controllers;

import lejos.utility.Delay;

// TODO: implements Runnable
public abstract class Controller extends Thread {
	protected boolean alive = true;
	protected boolean enabled = false;
	protected int interval = 10;

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
				Delay.msDelay(interval);
			}
		}
	}
}