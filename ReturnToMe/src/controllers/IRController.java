package controllers;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class IRController extends DeviceController {

    private EV3IRSensor irSensor;
    private SensorMode sensorMode;
    private int mode,
   			 channel,
   			 interval = 20,
   			 command;
    private float[] distanceSamples,
   				 seekSamples;
    
    public IRController(Port port, int mode, int channel) {
   	 this.irSensor = new EV3IRSensor(port);
   	 this.irSensor.setCurrentMode(mode);
   	 this.sensorMode = this.irSensor.getMode(mode);
   	 this.mode = mode;
   	 this.channel = channel;
   	 this.distanceSamples = new float[this.irSensor.getDistanceMode().sampleSize()];
   	 this.seekSamples = new float[this.irSensor.getSeekMode().sampleSize()];
    }
    
    public IRController(Port port, int mode) {
   	 this(port, mode, 0);
    }
    
    public IRController(Port port) {
   	 this(port, 0, 0);
    }
    
    public void setMode(int mode) {
   	 this.irSensor.setCurrentMode(mode);
   	 this.sensorMode = this.irSensor.getMode(mode);
   	 this.mode = mode;
    }
    
    public int getMode() {
   	 return this.mode;
    }
    
    public int getCommand() {
   	 return command;
    }

    public float getDistance() {
   	 float ret = Float.POSITIVE_INFINITY;
   	 
   	 switch (mode) {
   	 case 0:
   		 ret = this.distanceSamples[0];
   		 break;
   	 case 1:
   		 ret = this.seekSamples[1];
   		 break;
   	 }
   	 
   	 return ret;
    }
    
    public float getBearing() {
   	 float ret = Float.POSITIVE_INFINITY;
   	 
   	 if (mode == 1) {
   		 ret = this.seekSamples[0];
   	 }
   	 
   	 return ret;
    }
    
    @Override
    void action() {
   	 switch (this.mode) {
   	 case 0:
   		 this.sensorMode.fetchSample(distanceSamples, 0);
   		 break;
   	 case 1:
   		 this.sensorMode.fetchSample(seekSamples, 0);
   		 break;
   	 }
   	 
   	 // this leads to inaccurate samples
   	 //this.command = this.irSensor.getRemoteCommand(channel);

   	 Delay.msDelay(this.interval);
    }

    @Override
    void cleanUp() {
   	 this.irSensor.close();
    }

}

