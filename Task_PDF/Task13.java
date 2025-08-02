// Base class Vehicle
class Vehicle {
    protected String make;
    protected String model;
    protected int year;
    
    // Constructor
    public Vehicle(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }
    
    // Getters
    public String getMake() {
        return make;
    }
    
    public String getModel() {
        return model;
    }
    
    public int getYear() {
        return year;
    }
    
    // Setters
    public void setMake(String make) {
        this.make = make;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
}

// Derived class Car that extends Vehicle
class Car extends Vehicle {
    private int numberOfDoors;
    
    // Constructor
    public Car(String make, String model, int year, int numberOfDoors) {
        super(make, model, year);
        this.numberOfDoors = numberOfDoors;
    }
    
    // Getter for numberOfDoors
    public int getNumberOfDoors() {
        return numberOfDoors;
    }
    
    // Setter for numberOfDoors
    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }
    
    // Method to display car details
    public void displayDetails() {
        System.out.println("Car Details:");
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Number of Doors: " + numberOfDoors);
    }
}

// Main class to test the implementation
public class Task13 {
    public static void main(String[] args) {
        // Create an instance of the Car class and set values for its attributes
        Car myCar = new Car("Toyota", "Camry", 2022, 4);
        
        // Display the car details
        myCar.displayDetails();
    }
}