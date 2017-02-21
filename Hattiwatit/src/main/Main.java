package main;
import lejos.hardware.port.SensorPort;
import lejos.hardware.port.MotorPort;
import main.Doge;

public class Main {
	public static void main(String[] args) {
		Doge doge = new Doge(SensorPort.S3, SensorPort.S2, MotorPort.D, MotorPort.A);
		doge.loopMenu();
	}
}