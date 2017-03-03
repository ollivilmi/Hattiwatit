package controllers;

import lejos.utility.Delay;

public abstract class Controller implements Runnable {
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
				try {
					action();
					Delay.msDelay(interval);
				} catch (RuntimeException e) {
					//empty
				}
			}
		}
	}
}