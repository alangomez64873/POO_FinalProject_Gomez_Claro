package domain;

import java.io.Serializable;

// Representa un cupo fisico dentro del parqueadero.
public class ParkingSpot implements Serializable {

    private static final long serialVersionUID = 1L;

    private int spotNumber;
    private boolean occupied;

    // Constructor
    public ParkingSpot(int spotNumber) {
        if (spotNumber > 0) {
            this.spotNumber = spotNumber;
        } else {
            this.spotNumber = 1;
        }
        this.occupied = false;
    }

    // Devuelve el numero del cupo.
    public int getSpotNumber() {
        return spotNumber;
    }

    // Devuelve true si el cupo esta ocupado o false si esta libre.
    public boolean isOccupied() {
        return occupied;
    }

    // Marca el cupo como ocupado cuando entra un vehiculo.
    public void occupy() {
        if (!occupied) {
            occupied = true;
        }
    }

    // Libera el cupo cuando sale el vehiculo.
    public void release() {
        if (occupied) {
            occupied = false;
        }
    }

    // Muestra el estado del cupo en texto.
    @Override
    public String toString() {
        String statusText;
        if (occupied) {
            statusText = "OCUPADO";
        } else {
            statusText = "DISPONIBLE";
        }

        return "Cupo #" + spotNumber + " [" + statusText + "]";
    }
}
