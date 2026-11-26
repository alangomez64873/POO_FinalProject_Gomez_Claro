package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

// Clase central del dominio: administra cupos, vehiculos, tarifas, tiquetes y pagos.
public class ParkingLot implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private ArrayList<ParkingSpot> spots;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Rate> rates;
    private ArrayList<ParkingTicket> tickets;
    private ArrayList<Payment> payments;
    private OccupancyAdvisor advisor;

    // Constructor: inicializa las listas, el asesor (alerta al 80%) y crea los cupos fisicos.
    public ParkingLot(String name, int totalSpots) {
        this.name = name;
        this.spots = new ArrayList<ParkingSpot>();
        this.vehicles = new ArrayList<Vehicle>();
        this.rates = new ArrayList<Rate>();
        this.tickets = new ArrayList<ParkingTicket>();
        this.payments = new ArrayList<Payment>();
        this.advisor = new OccupancyAdvisor(80.0);

        for (int i = 1; i <= totalSpots; i++) {
            this.spots.add(new ParkingSpot(i));
        }
    }

    // GETTERS

    public String getName() {
        return name;
    }

    public ArrayList<ParkingSpot> getSpots() {
        return spots;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public ArrayList<Rate> getRates() {
        return rates;
    }

    public ArrayList<ParkingTicket> getTickets() {
        return tickets;
    }

    public ArrayList<Payment> getPayments() {
        return payments;
    }

    public OccupancyAdvisor getAdvisor() {
        return advisor;
    }

    // CRUD DE VEHICULOS
    // Registra un vehiculo si su placa no existe todavia.
    public boolean registerVehicle(Vehicle vehicle) {
        if (findVehicleByPlate(vehicle.getPlate()) != null) {
            return false;
        }
        vehicles.add(vehicle);
        return true;
    }

    // Busca un vehiculo por coincidencia exacta de placa.
    public Vehicle findVehicleByPlate(String plate) {
        String searchPlate = plate.trim().toUpperCase();
        for (int i = 0; i < vehicles.size(); i++) {
            Vehicle current = vehicles.get(i);
            if (current.getPlate().equals(searchPlate)) {
                return current;
            }
        }
        return null;
    }

    // Elimina un vehiculo si no tiene tiquetes; si tiene historial, solo lo desactiva.
    public String deleteOrDeactivateVehicle(String plate) {
        Vehicle vehicle = findVehicleByPlate(plate);
        if (vehicle == null) {
            return "No existe un vehiculo con esa placa.";
        }
        if (findActiveTicketByPlate(vehicle.getPlate()) != null) {
            return "No se puede eliminar ni desactivar: el vehiculo esta dentro del parqueadero.";
        }

        boolean hasHistory = false;
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getVehicle().getPlate().equals(vehicle.getPlate())) {
                hasHistory = true;
            }
        }

        if (hasHistory) {
            vehicle.deactivate();
            return "El vehiculo tiene historial, por lo tanto fue fdesactivado.";
        } else {
            vehicles.remove(vehicle);
            return "El vehiculo no tenia historial y fue eliminado.";
        }
    }

    // CRUD DE TARIFAS

    // Registra una tarifa si su ID no esta repetido.
    public boolean registerRate(Rate rate) {
        if (findRateById(rate.getRateId()) != null) {
            return false;
        }
        rates.add(rate);
        return true;
    }

    // Busca una tarifa por su ID exacto.
    public Rate findRateById(String rateId) {
        String searchId = rateId.trim().toUpperCase();
        for (int i = 0; i < rates.size(); i++) {
            Rate current = rates.get(i);
            if (current.getRateId().equals(searchId)) {
                return current;
            }
        }
        return null;
    }

    // Modifica el nombre y valor por hora de una tarifa.
    public boolean updateRate(String rateId, String name, double hourlyRate) {
        Rate rate = findRateById(rateId);
        if (rate == null || hourlyRate <= 0) {
            return false;
        }
        rate.setName(name);
        rate.setHourlyRate(hourlyRate);
        return true;
    }

    // Elimina una tarifa si nunca fue usada; si tiene historial, solo la desactiva.
    public String deleteOrDeactivateRate(String rateId) {
        Rate rate = findRateById(rateId);
        if (rate == null) {
            return "No existe una tarifa con ese ID.";
        }

        boolean hasHistory = false;
        for (int i = 0; i < tickets.size(); i++) {
            if (tickets.get(i).getRate().getRateId().equals(rate.getRateId())) {
                hasHistory = true;
            }
        }

        if (hasHistory) {
            rate.deactivate();
            return "La tarifa tiene historial, por lo tanto fue desactivada.";
        } else {
            rates.remove(rate);
            return "La tarifa no tenia historial y fue eliminada.";
        }
    }

    // INGRESOS Y SALIDAS

    // Registra el ingreso de un vehiculo asignando el primer cupo libre.
    public ParkingTicket registerEntry(String plate, String rateId) {
        Vehicle vehicle = findVehicleByPlate(plate);
        if (vehicle == null || !vehicle.isActive()) {
            return null;
        }
        if (findActiveTicketByPlate(vehicle.getPlate()) != null) {
            return null;
        }

        Rate rate = findRateById(rateId);
        if (rate == null || !rate.isActive()) {
            return null;
        }

        ArrayList<ParkingSpot> availableSpots = listAvailableSpots();
        if (availableSpots.isEmpty()) {
            return null;
        }

        ParkingSpot assignedSpot = availableSpots.get(0);
        String ticketId = "TKT-" + (tickets.size() + 1);
        ParkingTicket newTicket = new ParkingTicket(ticketId, vehicle, assignedSpot, rate, LocalDateTime.now());
        tickets.add(newTicket);
        return newTicket;
    }

    // Registra la salida, cierra el tiquete, libera el cupo y guarda el pago.
    public Payment registerExit(String plate) {
        ParkingTicket activeTicket = findActiveTicketByPlate(plate);
        if (activeTicket == null) {
            return null;
        }

        LocalDateTime exitTime = LocalDateTime.now();
        double amountToPay = activeTicket.close(exitTime);

        String paymentId = "PAY-" + (payments.size() + 1);
        Payment newPayment = new Payment(paymentId, amountToPay, exitTime, activeTicket);
        payments.add(newPayment);
        return newPayment;
    }

    // Busca si una placa tiene actualmente un tiquete activo.
    public ParkingTicket findActiveTicketByPlate(String plate) {
        String searchPlate = plate.trim().toUpperCase();
        for (int i = 0; i < tickets.size(); i++) {
            ParkingTicket current = tickets.get(i);
            if (current.isActive() && current.getVehicle().getPlate().equals(searchPlate)) {
                return current;
            }
        }
        return null;
    }

    // CONSULTAS, FILTROS Y REPORTES

    // Devuelve la lista de cupos libres.
    public ArrayList<ParkingSpot> listAvailableSpots() {
        ArrayList<ParkingSpot> result = new ArrayList<ParkingSpot>();
        for (int i = 0; i < spots.size(); i++) {
            if (!spots.get(i).isOccupied()) {
                result.add(spots.get(i));
            }
        }
        return result;
    }

    // Devuelve la lista de cupos ocupados.
    public ArrayList<ParkingSpot> listOccupiedSpots() {
        ArrayList<ParkingSpot> result = new ArrayList<ParkingSpot>();
        for (int i = 0; i < spots.size(); i++) {
            if (spots.get(i).isOccupied()) {
                result.add(spots.get(i));
            }
        }
        return result;
    }

    // Filtra tiquetes por rango de fechas y por estado ("ACTIVO", "CERRADO" o "TODOS").
    public ArrayList<ParkingTicket> filterTickets(LocalDateTime start, LocalDateTime end, String status) {
        ArrayList<ParkingTicket> result = new ArrayList<ParkingTicket>();
        String filterStatus = status.trim().toUpperCase();

        for (int i = 0; i < tickets.size(); i++) {
            ParkingTicket current = tickets.get(i);
            LocalDateTime entry = current.getEntryTime();

            boolean matchesDate = true;
            if (start != null && entry.isBefore(start)) {
                matchesDate = false;
            }
            if (end != null && entry.isAfter(end)) {
                matchesDate = false;
            }

            boolean matchesStatus = false;
            if (filterStatus.equals("TODOS")) {
                matchesStatus = true;
            } else if (filterStatus.equals("ACTIVO") && current.isActive()) {
                matchesStatus = true;
            } else if (filterStatus.equals("CERRADO") && !current.isActive()) {
                matchesStatus = true;
            }

            if (matchesDate && matchesStatus) {
                result.add(current);
            }
        }
        return result;
    }

    // Calcula el total recaudado en un rango de fechas.
    public double calculateRevenue(LocalDateTime start, LocalDateTime end) {
        double total = 0.0;
        for (int i = 0; i < payments.size(); i++) {
            Payment current = payments.get(i);
            LocalDateTime date = current.getPaymentDate();

            boolean matchesDate = true;
            if (start != null && date.isBefore(start)) {
                matchesDate = false;
            }
            if (end != null && date.isAfter(end)) {
                matchesDate = false;
            }

            if (matchesDate) {
                total = total + current.getAmount();
            }
        }
        return total;
    }

    // Consulta la alerta de ocupacion al componente de apoyo a decisiones.
    public String checkOccupancyAlert() {
        return advisor.evaluateOccupancy(spots);
    }
}
