package controllers;

abstract class DeviceController extends Thread {
    private boolean enabled = false,
   				 alive = true;
    
    abstract void action();
    
    abstract void cleanUp();
    
    public void enable() {
   	 this.enabled = true;
    }
    
    public void disable() {
   	 this.enabled = false;
    }
    
    public void run() {
   	 while (this.alive) {
   		 if (this.enabled) {
   			 this.action();
   		 }
   	 }
   	 
   	 this.cleanUp();
    }
    
    public void terminate() {
   	 this.alive = false;
    }
}
