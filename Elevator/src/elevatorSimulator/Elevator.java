package elevatorSimulator;

import java.io.*;
import java.util.*;


public class Elevator {

	//--class variables--
	int currentFloor; 										// stores elevator's current floor
	int floors;												// stores building's floors
	boolean currentDirection = true;						// false: elevator is going down;  true: elevator is going up
	int passengersOnElevator;								// passengers currently using the elevator
	int passengerNeedElevator;								// total passengers that have requested elevator
	int nextFloorUp;										// stores next floor to visit on up direction
	int nextFloorDown;										// stores next floor to visit on down direction
	int strategy;											// stores elevator strategy.  1:default, 2:optimized
	int highestFloor;
	List<Customer> passengers = new ArrayList<>();			// stores all customers inside the building
	List<String> elevatorLOG = new ArrayList<>();			// stores elevator activity log
	
	
	//--class methods for constructor--
	void setCurrentFloor (int currentFloor)					//set's elevator's current floor
	{this.currentFloor = currentFloor;}
	void setCurrentDirrection (boolean currentDirection)	//set's elevator's direction
	{this.currentDirection = currentDirection;}
	void setFloors (int floors)								//set's elevator's floor number
	{this.floors = floors;}
	void setPassengersOnElevator()							//counts current passengers in elevator
	{
		int count = 0;
		for (Customer customer : passengers) 
		{
			if (customer.inElevator == true) {count++;}
		}
		passengersOnElevator = count;
	}
	void setNeedsElevator() 								//determines what passengers need to use elevator to reach destination
	{
		int count = 0;
		for (Customer customer : passengers) 
		{
			if (customer.state == true) {count++;}
		}
		passengerNeedElevator = count;
	}
	void setElevatorStrategy(int strategy)					//set's elevator strategy
	{this.strategy = strategy;}
	
	//--class methods--
	void loadPassengersFromFile(String path) throws IOException    //loads customers into elevator reading a local text file
	{
		BufferedReader in = new BufferedReader(new FileReader(path)); 
		List<String> textLines = new ArrayList<>();
		while (in.ready()){textLines.add(in.readLine());}
		for (String text : textLines) 									//create customers based on list and load them into building
		{
			StringTokenizer tokenizer = new StringTokenizer(text,",");  
			int origin = Integer.parseInt((tokenizer.nextToken())); 
			int destination = Integer.parseInt((tokenizer.nextToken()));
			Customer customer = new Customer (origin, destination);
			this.passengers.add(customer);
		}
		in.close();
		setPassengersOnElevator();
		setNeedsElevator();
	}
	void loadPassenger(Customer customer)
	{
		passengers.add(customer);
		setPassengersOnElevator();
		setNeedsElevator();
		
	}
	int openDoor()											//drops off passengers when reaching a new floor
	{ 
		int getoffCount = 0; 					//stores number of passengers getting of the elevator at visited floor
		for (Customer customer : passengers)	//Looks at all customers in elevator to find who reached destination. if so, it changes its state to false meaning it no longer needs an elevator
		{ 
			if (customer.inElevator == true && this.currentFloor == customer.desiredFloor ) 
			{	
				customer.inElevator = false;//Has left elevator
				customer.currentFloor = this.currentFloor;//Reached desired floor
				customer.state = false;//Doesn't need elevator anymore
				getoffCount++;	
			}
		}
	return getoffCount;//return number of passengers getting of the elevator at visited floor
	}
	int closeDoor()											//pick up passengers needing elevator
	{
		int getinCount = 0;//count passengers loading the elevator at visited floors
		for (Customer customer : passengers) //looks for customers who need to take the elevator
		{
			if (this.currentDirection == true && this.currentFloor < customer.desiredFloor  && customer.state == true && this.currentFloor == customer.currentFloor && customer.inElevator == false)
			{
				customer.inElevator = true;//customer is now in elevator
				getinCount++;
			}
			else if (this.currentDirection == false && this.currentFloor > customer.desiredFloor  && customer.state == true && this.currentFloor == customer.currentFloor && customer.inElevator == false)
			{
				customer.inElevator = true;//customer is now in elevator
				getinCount++;
			}
		}
		return getinCount;	//return number of passengers pick up by the elevator at visited floor
	
	}
	void moveToNextFloor()									//controls the elevator increasing/decreasing  a floor according to its direction. 
	{
		if (this.strategy == 1) 
		{
			if (currentDirection == true && currentFloor == this.floors) 
			{	
				currentDirection = false;
				currentFloor = currentFloor - 1;
			}
			else if (currentDirection == true) {currentFloor++;}
			else if (currentDirection == false && currentFloor == 0) 
			{
				currentDirection = true;
				currentFloor++;
			}
			else {currentFloor = currentFloor - 1;}
		}
		
		else if (this.strategy == 2)
		{
			int nextFloor;
			if (this.currentDirection == true)
			{
				nextFloor = this.floors;

				for (Customer customer : passengers)
				{
					int currentFloorUP = customer.currentFloor;
					int desiredFloorUP = customer.desiredFloor;
					int isPositive = currentFloorUP - desiredFloorUP;
					int selectedFloorUp;
					if (isPositive >= 0){selectedFloorUp = desiredFloorUP;}
					else {selectedFloorUp = currentFloorUP;}
					if (selectedFloorUp < nextFloor && selectedFloorUp > this.currentFloor){nextFloor = selectedFloorUp;}

					
				}
				this.currentFloor = nextFloor;
			}
			else 
			{
				nextFloor = 1;
				for (Customer customer : passengers)
				{
					int currentFloorDown = customer.currentFloor;
					int desiredFloorDown = customer.desiredFloor;
					int isPositive = currentFloorDown - desiredFloorDown;
					int selectedFloorDown;
					if (isPositive >= 0){selectedFloorDown = currentFloorDown;}
					else {selectedFloorDown = currentFloorDown;}
					if (selectedFloorDown > nextFloor && selectedFloorDown < this.currentFloor){nextFloor = selectedFloorDown;}

				}
				this.currentFloor = nextFloor;
			}
				
			if (currentDirection == true && currentFloor == this.floors) 
			{	
				currentDirection = false;
			}
			else if (currentDirection == false && currentFloor == 0) 
			{
				currentDirection = true;
			}

		}
		
		
	}
	void printElevatorLog()									//prints elevator's log
	{
		System.out.println("     | in|out|");
		for (String floorvisit : this.elevatorLOG) {System.out.println(floorvisit);}	
	}
	void operateDefaultStrategy()							//operates elevator
	{
		int out = openDoor();
		int in = closeDoor();
		setPassengersOnElevator();
		setNeedsElevator();
		writeLog(in, out);
		moveToNextFloor();

	}
	void writeLog(int in, int out)							//writes elevator log
	{
		String feedback;//stores elevator activity at visited floor
		feedback = "| F" + String.valueOf(currentFloor) + " | " + String.valueOf(in) + " | " + String.valueOf(out) + " |  inside: " + String.valueOf(passengersOnElevator) + "  |";
		elevatorLOG.add(feedback);
		//System.out.println(feedback);
	}
	
	
	//--constructor--
	public Elevator(int currentFloor, boolean currentDirection, int floors, int strategy, String sourcePath) throws IOException
	{
		setCurrentFloor(currentFloor);
		setCurrentDirrection(currentDirection);
		setFloors(floors);
		setElevatorStrategy(strategy);
		loadPassengersFromFile(sourcePath);


	}
	
}
