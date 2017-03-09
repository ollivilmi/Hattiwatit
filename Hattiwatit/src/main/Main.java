package main;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;

public class Main {
	public static void main(String[] args) {
		Doge doge = new Doge(SensorPort.S3, SensorPort.S2, MotorPort.D, MotorPort.A, SensorPort.S1, MotorPort.B);
		doge.start();
	}
}