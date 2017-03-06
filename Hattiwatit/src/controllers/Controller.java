package controllers;

import lejos.utility.Delay;

/**
 * Generic controller class, which is meant to be run in its own thread. Its job
 * is to constantly run {@link #action()} in an interval specified by
 * {@link #interval}. Initially it starts in disabled state, which means it
 * doesn't do anything yet. Calling {@link #enable()} enables and calling
 * {@link #disable()} disables a Controller while its thread keeps running.
 * Calling {@link #terminate()} stops a Controller and its thread.
 */
public abstract class Controller implements Runnable {
	/**
	 * Is the Controller alive
	 */
	protected boolean alive = true;

	/**
	 * Is the Controller enabled
	 */
	protected boolean enabled = false;

	/**
	 * Time between loops in milliseconds
	 */
	protected int interval = 10;

	/**
	 * The action to be run
	 */
	protected abstract void action();

	/**
	 * Terminates the Controller
	 */
	public void terminate() {
		alive = false;
	}

	/**
	 * Enable the controller
	 */
	public void enable() {
		enabled = true;
	}

	/**
	 * Disable the Controller
	 */
	public void disable() {
		enabled = false;
	}

	/**
	 * Runs when the Controller's thread is started
	 */
	@Override
	public void run() {
		while (alive) {
			if (enabled) {
				try {
					action();
					Delay.msDelay(interval);
				} catch (RuntimeException e) {
					// empty
				}
			}
		}
	}
}