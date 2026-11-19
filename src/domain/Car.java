package domain;

public class Car extends Vehicle {
    private int numberOfDoors;

    public Car(String plate, String make, String color, int numberOfDoors) {
        super(plate, make, color);
        this.numberOfDoors = numberOfDoors;
    }

    public int getNumberOfDoors() { return numberOfDoors; }
    public void setNumberOfDoors(int numberOfDoors) { this.numberOfDoors = numberOfDoors; }

    @Override
    public double calculateRateFactor() {
        return 1.0; 
    }
}