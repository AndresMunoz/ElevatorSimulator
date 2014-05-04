package elevatorSimulator;

import java.io.IOException;

public class Building {

	//after assignment submission
	//class variables
	int buildingFloors;
	Elevator elevator;

	//class methods
	void operateElevatorDefault ()		//elevator elevator
	{
		while (elevator.passengerNeedElevator != 0) {elevator.operateDefaultStrategy();}  //run until all passengers reach their destination
		elevator.printElevatorLog(); //print activity log
	}

	//constructor
	public Building(int buildingFloors, int elevatorStrategy, String sourcePath) throws IOException
	{
		this.buildingFloors =  buildingFloors;
		elevator = new Elevator(1, true, buildingFloors, elevatorStrategy, sourcePath);
	}

}
