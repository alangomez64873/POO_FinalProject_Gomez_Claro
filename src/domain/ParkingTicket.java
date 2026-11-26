package domain;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Representa un tiquete de ingreso y salida de un vehiculo en el parqueadero.
public class ParkingTicket implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ticketId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private boolean active;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private Rate rate;

    // Constructor: crea un tiquete activo y marca el cupo asignado como ocupado.
    public ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot spot, Rate rate, LocalDateTime entryTime) {
        if (ticketId != null && !ticketId.trim().equals("")) {
            this.ticketId = ticketId.trim().toUpperCase();
        } else {
            this.ticketId = "TKT-000";
        }

        this.vehicle = vehicle;
        this.spot = spot;
        this.rate = rate;

        if (entryTime != null) {
            this.entryTime = entryTime;
        } else {
            this.entryTime = LocalDateTime.now();
        }

        this.exitTime = null;
        this.active = true;

        if (this.spot != null) {
            this.spot.occupy();
        }
    }

    public String getTicketId() {
        return ticketId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    // null mientras el vehiculo sigue adentro
    public LocalDateTime getExitTime() {
        return exitTime;
    }

    // True: sigue adentro, False: Salió
    public boolean isActive() {
        return active;
    }

    // Devuelve el vehiculo asociado al tiquete.
    public Vehicle getVehicle() {
        return vehicle;
    }

    // Devuelve el cupo fisico asignado al tiquete.
    public ParkingSpot getSpot() {
        return spot;
    }

    // Devuelve la tarifa aplicada al tiquete.
    public Rate getRate() {
        return rate;
    }

    // Calcula cuantos minutos han pasado entre la entrada y la salida (o hasta
    // ahora si sigue activo).
    public long calculateMinutes() {
        LocalDateTime end;
        if (exitTime != null) {
            end = exitTime;
        } else {
            end = LocalDateTime.now();
        }

        long minutes = Duration.between(entryTime, end).toMinutes();
        if (minutes < 0) {
            return 0;
        } else {
            return minutes;
        }
    }

    // Cierra el tiquete al registrar la salida, libera el cupo y calcula el valor a
    // pagar.
    public double close(LocalDateTime exitTime) {
        if (!active) {
            return 0.0;
        }

        if (exitTime != null) {
            this.exitTime = exitTime;
        } else {
            this.exitTime = LocalDateTime.now();
        }

        long minutes = calculateMinutes();

        // Aqui se aplica POLIMORFISMO: vehicle es de tipo Vehicle, pero Java ejecuta
        // el calculateFee de Car o de Motorcycle segun el objeto real que tenga
        // guardado.
        double fee = 0.0;
        if (vehicle != null && rate != null) {
            fee = vehicle.calculateFee(minutes, rate);
        }

        if (spot != null) {
            spot.release();
        }

        this.active = false;
        return fee;
    }

    // Devuelve los datos del tiquete en un texto para leer en pantalla.
    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String salida = "En curso";
        if (exitTime != null) {
            salida = exitTime.format(formato);
        }

        String estado = "CERRADO";
        if (active) {
            estado = "ACTIVO";
        }

        return "Tiquete [" + ticketId + "] | Placa: " + vehicle.getPlate()
                + " | Cupo #" + spot.getSpotNumber()
                + " | Entrada: " + entryTime.format(formato)
                + " | Salida: " + salida
                + " | Estado: " + estado;
    }
}
