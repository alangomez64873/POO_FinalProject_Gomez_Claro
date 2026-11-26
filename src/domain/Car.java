package domain;

public class Car extends Vehicle {

    private static final long serialVersionUID = 1L;

    // Constructor
    public Car(String plate, String color) {
        super(plate, color);
    }

    // Calcula el cobro para un carro: paga el 100% de la tarifa base.
    @Override
    public double calculateFee(long minutes, Rate rate) {
        if (rate == null) {
            return 0.0;
        } else {
            return rate.calculateBaseFee(minutes);
        }
    }

    // Agrega la palabra "Carro" antes de los datos que construye Vehicle.
    @Override
    public String toString() {
        return "Carro | " + super.toString();
    }
}
