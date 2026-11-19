package data;

import domain.Employee;
import domain.Parking;
import domain.Rate;
import domain.Spot;

public class DataInitializer {
    
    public static Parking loadParkingData() {
        // Creación del parqueadero
        Parking parking = new Parking("Smart Parking Centro", "Calle Principal #123");

        // Creación tarifas base 
        Rate carRate = new Rate("Tarifa Automóvil", 5000.0);
        Rate motoRate = new Rate("Tarifa Motocicleta", 2000.0);
        parking.addRate(carRate);
        parking.addRate(motoRate);

        // Creación de los empleados 
        Employee admin = new Employee("EMP-001", "Alan Gómez", true);
        Employee operator = new Employee("EMP-002", "Valeria Claro", false);
        parking.addEmployee(admin);
        parking.addEmployee(operator);

        // 10 espacios de parqueo en el piso 1
        for (int i = 1; i <= 10; i++) {
            parking.addSpot(new Spot(i, 1));
        }

        return parking;
    }
}
