package domain;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Parking {
    private String name;
    private String address;

    private List<Spot> spots;
    private List<Employee> employees;
    private List<Rate> rates;
    private List<Vehicle> vehicles;
    private List<Ticket> tickets;

    public Parking(String name, String address) {
        this.name = name;
        this.address = address;
        this.spots = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.rates = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.tickets = new ArrayList<>();
    }

    // Métodos 
    public Ticket registerEntry(Vehicle vehicle, Spot spot, Rate rate, Employee employee) {
        if (!spot.isAvailable()) {
            throw new IllegalStateException("El espacio ya está ocupado.");
        }
        
        spot.occupy(); // Se ocupa el puesto
        vehicle.setHasActiveTicket(true); // Se marca el vehículo
        
        // Generación de número de ticket secuencial
        String ticketNumber = "TKT-" + (tickets.size() + 1);
        Ticket newTicket = new Ticket(ticketNumber, vehicle, spot, rate, employee);
        
        // Se agrega a las listas del parqueadero
        tickets.add(newTicket);
        if (!vehicles.contains(vehicle)) {
            vehicles.add(vehicle);
        }
        
        return newTicket;
    }

    public Payment registerExit(Ticket ticket) {
        if (!tickets.contains(ticket) || !ticket.isOpen()) {
            throw new IllegalArgumentException("El ticket es inválido o ya fue cerrado.");
        }
        
        // Se libera el espacio y el vehículo
        ticket.getSpot().free();
        ticket.getVehicle().setHasActiveTicket(false);
        
        // Se cierra el ticket generando el pago
        return ticket.close(LocalDateTime.now());
    }

    public double calculateOccupancyPercentage() {
        if (spots.isEmpty()) return 0.0;
        
        long occupiedCount = spots.stream().filter(Spot::isOccupied).count();
        return ((double) occupiedCount / spots.size()) * 100.0;
    }

    public Spot recommendSpot() {
        // Busca y retorna el primer espacio disponible
        return spots.stream()
                    .filter(Spot::isAvailable)
                    .findFirst()
                    .orElse(null);
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    
    public void addSpot(Spot spot) { this.spots.add(spot); }
    public void addEmployee(Employee employee) { this.employees.add(employee); }
    public void addRate(Rate rate) { this.rates.add(rate); }
    
    public List<Spot> getSpots() { return spots; }
    public List<Ticket> getTickets() { return tickets; }
}