package domain;

public class Motorcycle extends Vehicle {
    private int engineDisplacement;

    public Motorcycle(String plate, String make, String color, int engineDisplacement) {
        super(plate, make, color);
        this.engineDisplacement = engineDisplacement;
    }

    public int getEngineDisplacement() { return engineDisplacement; }
    public void setEngineDisplacement(int engineDisplacement) { this.engineDisplacement = engineDisplacement; }

    @Override
    public double calculateRateFactor() {
        return 0.7; // Factor diferencial para motos
    }
}