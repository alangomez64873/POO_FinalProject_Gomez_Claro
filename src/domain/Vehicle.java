package domain;

import java.io.Serializable;

// Clase abstracta base para cualquier vehiculo del parqueadero.
// Es abstracta porque no debe existir un "vehiculo generico", solo carros o motos.
public abstract class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String plate;
    private String color;
    private boolean active;

    // Constructor
    public Vehicle(String plate, String color) {
        if (plate != null && !plate.trim().equals("")) {
            this.plate = plate.trim().toUpperCase();
        } else {
            this.plate = "SIN-PLACA";
        }

        if (color != null && !color.trim().equals("")) {
            this.color = color.trim();
        } else {
            this.color = "Sin color";
        }

        this.active = true;
    }

    public String getPlate() {
        return plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color != null && !color.trim().equals("")) {
            this.color = color.trim();
        }
    }

    public boolean isActive() {
        return active;
    }

    // Activa el vehiculo para que pueda volver a ingresar.
    public void activate() {
        this.active = true;
    }

    // Desactiva el vehiculo cuando ya tiene historial y no se puede borrar.
    public void deactivate() {
        this.active = false;
    }

    // Metodo abstracto (polimorfismo): cada clase hija decide como calcular su cobro.
    public abstract double calculateFee(long minutes, Rate rate);

    // Devuelve los datos comunes del vehiculo en texto para imprimir en pantalla.
    @Override
    public String toString() {
        String statusText;
        if (active) {
            statusText = "ACTIVO";
        } else {
            statusText = "INACTIVO";
        }

        return "Placa: " + plate + " | Color: " + color + " | Estado: " + statusText;
    }
}
