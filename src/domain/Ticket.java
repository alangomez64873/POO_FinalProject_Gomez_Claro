package domain;

import java.time.LocalDateTime;
import java.time.Duration;

public class Ticket {
    private String number;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private boolean active;

    private Vehicle vehicle;
    private Spot spot;
    private Rate rate;
    private Employee employee;
    private Payment payment;

    public Ticket(String number, Vehicle vehicle, Spot spot, Rate rate, Employee employee) {
        this.number = number;
        this.vehicle = vehicle;
        this.spot = spot;
        this.rate = rate;
        this.employee = employee;
        this.entryTime = LocalDateTime.now();
        this.active = true; 
    }

    public String getNumber() { return number; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public boolean isActive() { return active; }
    
    public Vehicle getVehicle() { return vehicle; }
    public Spot getSpot() { return spot; }
    public Rate getRate() { return rate; }
    public Employee getEmployee() { return employee; }
    public Payment getPayment() { return payment; }

    public boolean isOpen() {
        return active && exitTime == null;
    }

    public double calculateValue(LocalDateTime exitTime) {
        if (entryTime == null || exitTime == null || exitTime.isBefore(entryTime)) {
            return 0.0;
        }
        long hours = Duration.between(entryTime, exitTime).toHours();
        if (hours == 0) hours = 1; 
        return hours * rate.getRatePerHour() * vehicle.calculateRateFactor();
    }

    public Payment close(LocalDateTime exitTime) {
        if (!isOpen()) {
            throw new IllegalStateException("El ticket ya está cerrado o anulado.");
        }
        this.exitTime = exitTime;
        this.active = false;
        double calculatedValue = calculateValue(exitTime);
        
        this.payment = new Payment("REC-" + this.number, calculatedValue, exitTime);
        return this.payment;
    }

    public void cancel() {
        this.active = false;
    }
}
