package main;
import values.IR;
import functions.Print;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import lejos.hardware.port.MotorPort;
import functions.Movement;
import functions.Follow;

public class Main {
	public static void main(String[] args) {
		IR sensori = new IR(SensorPort.S1);
		sensori.start();
		Print printer = new Print(sensori);	
		printer.start();
		Movement liike = new Movement(MotorPort.A, MotorPort.D, 360);
		Follow follower = new Follow(sensori, liike);
		follower.start();
		while (Button.ESCAPE.isUp()) {
			Delay.msDelay(50);
		}
		sensori.setStop();
		printer.setStop();
		follower.setStop();
	}

}
