package elevatorSimulator;

public class Customer {
	
	//variables

	int currentFloor;	// stores customer's current floor
	int desiredFloor;	// stores customer's desired floor
	boolean state;		// true: customer wants to use elevator; false: customer doesn't need an elevator
	boolean inElevator;	// true: customer is inside the elevator; false: customer is outside the elevator
	
	//constructor methods
	void setCurrentFloor(int origin) {currentFloor =  origin;}
	void setDesiredFloor(int destination) {desiredFloor = destination;}
	void setCustomerState() {
		if (currentFloor == desiredFloor) {state = false;} 
		else {state = true;} }
	
	//constructor
	public Customer (int origin, int destination){

		setCurrentFloor(origin);
		setDesiredFloor(destination);
		setCustomerState();
	}


}
