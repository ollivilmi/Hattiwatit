package main;
import values.IRSeeker;
import functions.Print;
import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import lejos.hardware.port.MotorPort;
import functions.Movement;
import functions.Follow;

public class Main {
	public static void main(String[] args) {
		IRSeeker sensori = new IRSeeker(SensorPort.S1);
		Print printer = new Print(sensori);	
		Movement liike = new Movement(MotorPort.A, MotorPort.D, 360);
		Follow follower = new Follow(sensori, liike);
		
		sensori.start();
		printer.start();
		follower.start();
		
		while (Button.ESCAPE.isUp()) {
			Delay.msDelay(50);
		}
		
		sensori.setStop();
		printer.setStop();
		follower.setStop();
	}

}
