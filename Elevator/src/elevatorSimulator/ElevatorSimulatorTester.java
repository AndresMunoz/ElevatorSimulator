package elevatorSimulator;

import java.io.*;


public class ElevatorSimulatorTester {

	public static void main(String[] args) throws IOException {


		String sourcePath = "testfile.txt";	//path to elevator test file
		int floors = 8;						//number of floors
		
		System.out.println("");
		System.out.println("Welcome to Simple Elevator Simulator");
		System.out.println("------------------------------------");
		System.out.println("");
		System.out.println("(FIRST SIMULATION): source file -testfile.txt- , number of floors -8-");
		System.out.println("");
		
		//run strategy 1 (default)
		Building building1 = new Building(floors,1,sourcePath);
		building1.operateElevatorDefault();
		
		//print results first strategy
		System.out.println("");
		System.out.println("-Results-  Stops Required: -" + building1.elevator.elevatorLOG.size());

		
		//run strategy 2 (optimized)
		System.out.println("");
		System.out.println("");
		System.out.println("(SECOND SIMULATION): source file -testfile.txt- , number of floors -8-");
		System.out.println("");
		Building building2 = new Building(floors,2,sourcePath);
		building2.operateElevatorDefault();
		
		//print results second strategy
		System.out.println("");
		System.out.println("-Results-  Stops Required: -" + building2.elevator.elevatorLOG.size());
		
		//compare efficiency
		System.out.println("");
		System.out.println("");
		System.out.println("STRATEGIES EFFICIENCY:");
		System.out.println("");
		
		
		System.out.println("First Strategy  " + building1.elevator.elevatorLOG.size() + " stops" );
		System.out.println("Second Strategy " + building2.elevator.elevatorLOG.size() + " stops" );
		System.out.println("");
		System.out.println("");
		System.out.println("------end of simulation-----");
		
	}

}
