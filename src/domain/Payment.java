package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Es un registro historico inmutable (no tiene setters para evitar alterar montos cobrados).
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String paymentId;
    private double amount;
    private LocalDateTime paymentDate;
    private ParkingTicket ticket;

    // Constructor: pago asociado a un tiquet cerrado
    public Payment(String paymentId, double amount, LocalDateTime paymentDate, ParkingTicket ticket) {
        if (paymentId != null && !paymentId.trim().equals("")) {
            this.paymentId = paymentId.trim().toUpperCase();
        } else {
            this.paymentId = "PAY-000";
        }

        if (amount >= 0) {
            this.amount = amount;
        } else {
            this.amount = 0.0;
        }

        if (paymentDate != null) {
            this.paymentDate = paymentDate;
        } else {
            this.paymentDate = LocalDateTime.now();
        }

        this.ticket = ticket;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public ParkingTicket getTicket() {
        return ticket;
    }

    // Genera un recibo en español para mostrar al operador al registrar la salida.
    public String buildReceipt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Vehicle vehicle = ticket.getVehicle();
        String vehicleType;
        if (vehicle instanceof Car) {
            vehicleType = "Carro";
        } else if (vehicle instanceof Motorcycle) {
            vehicleType = "Moto";
        } else {
            vehicleType = "Vehiculo";
        }

        String entryText = ticket.getEntryTime().format(formatter);
        String exitText = paymentDate.format(formatter);
        long minutes = ticket.calculateMinutes();

        String receipt = "/////////////////////////////////\n"
                + "          COMPROBANTE DE PAGO           \n"
                + "/////////////////////////////////\n"
                + "Pago ID: " + paymentId + "\n"
                + "Tiquete ID: " + ticket.getTicketId() + "\n"
                + "Vehiculo: " + vehicleType + " - Placa " + vehicle.getPlate() + "\n"
                + "Cupo utilizado: #" + ticket.getSpot().getSpotNumber() + "\n"
                + "Tarifa aplicada: " + ticket.getRate().getName() + "\n"
                + "Hora de entrada: " + entryText + "\n"
                + "Hora de salida: " + exitText + "\n"
                + "Tiempo total: " + minutes + " minutos\n"
                + "TOTAL PAGADO: $" + amount + "\n"
                + "/////////////////////////////////";

        return receipt;
    }

    // Resumen del pago 
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Pago [" + paymentId + "] | Tiquete: " + ticket.getTicketId()
                + " | Placa: " + ticket.getVehicle().getPlate()
                + " | Fecha: " + paymentDate.format(formatter)
                + " | Total: $" + amount;
    }
}
