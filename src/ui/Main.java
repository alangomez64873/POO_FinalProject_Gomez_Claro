package ui;

import data.CsvExporter;
import data.ParkingStorage;
import domain.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

// Punto de entrada y menu en consola del parqueadero.
public class Main {

    public static void main(String[] args) {
        ParkingStorage storage = new ParkingStorage();
        CsvExporter csvExporter = new CsvExporter();
        Scanner scanner = new Scanner(System.in);

        // Carga los datos guardados o inicia un parqueadero nuevo de 5 cupos con tarifas base.
        ParkingLot parkingLot = storage.load();
        if (parkingLot != null) {
            System.out.println("Datos cargados correctamente.");
        } else {
            parkingLot = new ParkingLot("Parqueadero Central", 5);
            parkingLot.registerRate(new Rate("T1", "Tarifa Base Carro", 3000));
            parkingLot.registerRate(new Rate("T2", "Tarifa Base Moto", 1500));
            System.out.println("Parqueadero nuevo iniciado (5 cupos, tarifas base: T1 Carro / T2 Moto).");
        }

        int option = -1;

        // Ciclo principal del menu: se repite hasta que el usuario elija 0.
        while (option != 0) {
            System.out.println("\n=== " + parkingLot.getName().toUpperCase() + " ===");
            System.out.println("1. Registrar vehiculo");
            System.out.println("2. Listar vehiculos");
            System.out.println("3. Buscar vehiculo por placa");
            System.out.println("4. Eliminar o desactivar vehiculo");
            System.out.println("5. Registrar tarifa");
            System.out.println("6. Listar tarifas");
            System.out.println("7. Modificar tarifa");
            System.out.println("8. Eliminar o desactivar tarifa");
            System.out.println("9. Registrar ingreso");
            System.out.println("10. Registrar salida y cobrar");
            System.out.println("11. Ver cupos y alerta de ocupacion");
            System.out.println("12. Filtrar movimientos (tiquetes)");
            System.out.println("13. Ver ingresos por fechas");
            System.out.println("14. Exportar pagos a CSV");
            System.out.println("0. Guardar y salir");
            System.out.print("Opcion: ");

            try {
                option = Integer.parseInt(scanner.nextLine().trim());

                if (option == 1) {
                    System.out.print("Tipo (1 = Carro, 2 = Moto): ");
                    int type = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Placa: ");
                    String plate = scanner.nextLine().trim();
                    System.out.print("Color: ");
                    String color = scanner.nextLine().trim();

                    Vehicle vehicle = null;
                    if (!plate.equals("") && !color.equals("")) {
                        if (type == 1) {
                            vehicle = new Car(plate, color);
                        } else if (type == 2) {
                            vehicle = new Motorcycle(plate, color);
                        }
                    }

                    if (vehicle != null && parkingLot.registerVehicle(vehicle)) {
                        System.out.println("Vehiculo registrado.");
                    } else {
                        System.out.println("No se pudo registrar (datos invalidos o placa repetida).");
                    }

                } else if (option == 2) {
                    ArrayList<Vehicle> list = parkingLot.getVehicles();
                    if (list.isEmpty()) {
                        System.out.println("No hay vehiculos registrados.");
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i));
                        }
                    }

                } else if (option == 3) {
                    System.out.print("Placa: ");
                    String plate = scanner.nextLine().trim();
                    Vehicle vehicle = parkingLot.findVehicleByPlate(plate);

                    if (vehicle == null) {
                        System.out.println("Vehiculo no encontrado.");
                    } else {
                        System.out.println(vehicle);
                        ParkingTicket active = parkingLot.findActiveTicketByPlate(plate);
                        if (active != null) {
                            System.out.println("Estado: DENTRO (Cupo #" + active.getSpot().getSpotNumber() + ")");
                        } else {
                            System.out.println("Estado: FUERA.");
                        }
                    }

                } else if (option == 4) {
                    System.out.print("Placa: ");
                    String plate = scanner.nextLine().trim();
                    System.out.println(parkingLot.deleteOrDeactivateVehicle(plate));

                } else if (option == 5) {
                    System.out.print("ID (ej. T3): ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Valor/hora: ");
                    double price = Double.parseDouble(scanner.nextLine().trim());

                    if (!id.equals("") && !name.equals("") && price > 0 && parkingLot.registerRate(new Rate(id, name, price))) {
                        System.out.println("Tarifa registrada.");
                    } else {
                        System.out.println("No se pudo registrar la tarifa.");
                    }

                } else if (option == 6) {
                    ArrayList<Rate> list = parkingLot.getRates();
                    if (list.isEmpty()) {
                        System.out.println("No hay tarifas registradas.");
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i));
                        }
                    }

                } else if (option == 7) {
                    System.out.print("ID tarifa: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Nuevo nombre: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Nuevo valor/hora: ");
                    double price = Double.parseDouble(scanner.nextLine().trim());

                    if (!name.equals("") && parkingLot.updateRate(id, name, price)) {
                        System.out.println("Tarifa actualizada.");
                    } else {
                        System.out.println("No se pudo actualizar la tarifa.");
                    }

                } else if (option == 8) {
                    System.out.print("ID tarifa: ");
                    String id = scanner.nextLine().trim();
                    System.out.println(parkingLot.deleteOrDeactivateRate(id));

                } else if (option == 9) {
                    System.out.print("Placa: ");
                    String plate = scanner.nextLine().trim();
                    System.out.print("ID tarifa (T1 = Carro, T2 = Moto): ");
                    String rateId = scanner.nextLine().trim();

                    ParkingTicket ticket = parkingLot.registerEntry(plate, rateId);
                    if (ticket != null) {
                        System.out.println("Ingreso registrado: " + ticket);
                        System.out.println(parkingLot.checkOccupancyAlert());
                    } else {
                        System.out.println("No se pudo registrar ingreso (revise placa, tarifa, estado o cupos).");
                    }

                } else if (option == 10) {
                    System.out.print("Placa: ");
                    String plate = scanner.nextLine().trim();
                    Payment payment = parkingLot.registerExit(plate);

                    if (payment != null) {
                        System.out.println(payment.buildReceipt());
                    } else {
                        System.out.println("El vehiculo no tiene un ingreso activo.");
                    }

                } else if (option == 11) {
                    System.out.println("Libres: " + parkingLot.listAvailableSpots());
                    System.out.println("Ocupados: " + parkingLot.listOccupiedSpots());
                    System.out.println(parkingLot.checkOccupancyAlert());

                } else if (option == 12) {
                    System.out.print("Fecha inicial (AAAA-MM-DD o Enter para omitir): ");
                    String startText = scanner.nextLine().trim();
                    LocalDateTime start = null;
                    if (!startText.equals("")) {
                        start = LocalDate.parse(startText).atStartOfDay();
                    }

                    System.out.print("Fecha final (AAAA-MM-DD o Enter para omitir): ");
                    String endText = scanner.nextLine().trim();
                    LocalDateTime end = null;
                    if (!endText.equals("")) {
                        end = LocalDate.parse(endText).atTime(23, 59, 59);
                    }

                    System.out.print("Estado (ACTIVO, CERRADO o TODOS): ");
                    String status = scanner.nextLine().trim();

                    ArrayList<ParkingTicket> results = parkingLot.filterTickets(start, end, status);
                    if (results.isEmpty()) {
                        System.out.println("No se encontraron movimientos.");
                    } else {
                        for (int i = 0; i < results.size(); i++) {
                            System.out.println(results.get(i));
                        }
                    }

                } else if (option == 13) {
                    System.out.print("Fecha inicial (AAAA-MM-DD o Enter para omitir): ");
                    String startText = scanner.nextLine().trim();
                    LocalDateTime start = null;
                    if (!startText.equals("")) {
                        start = LocalDate.parse(startText).atStartOfDay();
                    }

                    System.out.print("Fecha final (AAAA-MM-DD o Enter para omitir): ");
                    String endText = scanner.nextLine().trim();
                    LocalDateTime end = null;
                    if (!endText.equals("")) {
                        end = LocalDate.parse(endText).atTime(23, 59, 59);
                    }

                    System.out.println("Total recaudado: $" + parkingLot.calculateRevenue(start, end));

                } else if (option == 14) {
                    if (csvExporter.exportPayments(parkingLot.getPayments())) {
                        System.out.println("Exportado en storage/payments_report.csv");
                    } else {
                        System.out.println("Error al exportar CSV.");
                    }

                } else if (option == 0) {
                    if (storage.save(parkingLot)) {
                        System.out.println("Datos guardados. Hasta pronto.");
                    } else {
                        System.out.println("Error al guardar los datos.");
                    }

                } else {
                    System.out.println("Opcion no valida.");
                }

            } catch (Exception e) {
                System.out.println("Error: ingrese un dato valido.");
            }
        }

        scanner.close();
    }
}