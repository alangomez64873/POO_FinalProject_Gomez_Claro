package domain;

public class Motorcycle extends Vehicle {

    private static final long serialVersionUID = 1L;

    // las motos pagan el 50% de la tarifa base.
    private static final double MOTORCYCLE_RATE_FACTOR = 0.5;

    // Constructor
    public Motorcycle(String plate, String color) {
        super(plate, color);
    }

    // Calcula el cobro para una moto: paga el 50% de la tarifa base.
    @Override
    public double calculateFee(long minutes, Rate rate) {
        if (rate == null) {
            return 0.0;
        } else {
            double baseFee = rate.calculateBaseFee(minutes);
            return baseFee * MOTORCYCLE_RATE_FACTOR;
        }
    }

    // Agrega la palabra "Moto" antes de los datos que construye Vehicle.
    @Override
    public String toString() {
        return "Moto | " + super.toString();
    }
}
