package domain;

public class Vehicle {
    private String plate;
    private String make;
    private String color;
    private boolean hasActiveTicket;

    public Vehicle(String plate, String make, String color) {
        this.plate = plate;
        this.make = make;
        this.color = color;
        this.hasActiveTicket = false;
    }

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public boolean hasActiveTicket() { return hasActiveTicket; }
    public void setHasActiveTicket(boolean hasActiveTicket) { this.hasActiveTicket = hasActiveTicket; }

    public double calculateRateFactor() {
        return 1.0;
    }
}