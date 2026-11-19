package ui;

import data.DataInitializer;
import domain.Car;
import domain.Employee;
import domain.Motorcycle;
import domain.Parking;
import domain.Payment;
import domain.Rate;
import domain.Spot;
import domain.Ticket;
import domain.Vehicle;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicialización datos de prueba
        Parking parking = DataInitializer.loadParkingData();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("==========================================");
        System.out.println("   Bienvenido a " + parking.getName());
        System.out.println("==========================================");

        while (!exit) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Registrar Ingreso de Vehículo");
            System.out.println("2. Registrar Salida (Cobro)");
            System.out.println("3. Consultar Ocupación del Parqueadero");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            int option = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            if (option == 1) {
                registerEntry(scanner, parking);
            } else if (option == 2) {
                registerExit(scanner, parking);
            } else if (option == 3) {
                System.out.printf("Ocupación actual: %.2f%%\n", parking.calculateOccupancyPercentage());
            } else if (option == 4) {
                exit = true;
                System.out.println("Cerrando el sistema... ¡Hasta luego!");
            } else {
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
        scanner.close();
    }

    private static void registerEntry(Scanner scanner, Parking parking) {
        Spot spot = parking.recommendSpot();
        if (spot == null) {
            System.out.println("Lo sentimos, no hay espacios disponibles.");
            return;
        }

        System.out.print("Ingrese la placa: ");
        String plate = scanner.nextLine();
        System.out.print("Ingrese la marca: ");
        String make = scanner.nextLine();
        System.out.print("Ingrese el color: ");
        String color = scanner.nextLine();
        
        System.out.print("¿Es (1) Carro o (2) Moto?: ");
        int type = scanner.nextInt();
        scanner.nextLine();

        Vehicle vehicle;
        Rate activeRate;
        if (type == 1) {
            vehicle = new Car(plate, make, color, 4);
            activeRate = new Rate("Tarifa Automóvil", 5000);
        } else {
            vehicle = new Motorcycle(plate, make, color, 150);
            activeRate = new Rate("Tarifa Motocicleta", 2000);
        }

        Employee activeEmployee = new Employee("EMP-001", "Alan Gómez", true);

        try {
            Ticket ticket = parking.registerEntry(vehicle, spot, activeRate, activeEmployee);
            System.out.println("\nIngreso registrado exitosamente.");
            System.out.println("Número de Ticket: " + ticket.getNumber());
            System.out.println("Espacio asignado: " + spot.getCode());
        } catch (Exception e) {
            System.out.println("Error al registrar ingreso: " + e.getMessage());
        }
    }

    private static void registerExit(Scanner scanner, Parking parking) {
        System.out.print("Ingrese el número de ticket (ej. TKT-1): ");
        String ticketNumber = scanner.nextLine();

        Ticket activeTicket = null;
        for (Ticket t : parking.getTickets()) {
            if (t.getNumber().equalsIgnoreCase(ticketNumber) && t.isOpen()) {
                activeTicket = t;
                break;
            }
        }

        if (activeTicket == null) {
            System.out.println("Ticket no encontrado o ya se encuentra cerrado.");
            return;
        }

        try {
            Payment payment = parking.registerExit(activeTicket);
            System.out.println("\n--- Recibo de Pago ---");
            System.out.println("Recibo No: " + payment.getReceiptNumber());
            System.out.println("Total a Pagar: $" + payment.getValue());
            System.out.println("Fecha y Hora: " + payment.getDateTime());
            System.out.println("¡Gracias por su visita!");
        } catch (Exception e) {
            System.out.println("Error al procesar la salida: " + e.getMessage());
        }
    }
}
