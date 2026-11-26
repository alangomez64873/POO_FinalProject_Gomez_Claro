# Titulo del proyecto 
Smart Parking System

# Integrantes
Alan Gómez Ortiz
Código: 0000064873

Valeria Alexandra Claro Vargas
Código: 0000064015

# Descripción general del sistema
Aplicación para el personal de un parqueadero que necesita conocer la ocupación durante la jornada y cobrar al finalizar cada estancia. El sistema vincula cada ingreso con su salida para que el operador pueda consultar los cupos disponibles y revisar la actividad del parqueadero.

# Instrucciones para ejecutar
# Ejemplo de entrada/salida
# Diagrama de clases

```mermaid
classDiagram
    class ParkingLot {
        -String name
        -ArrayList~ParkingSpot~ spots
        -ArrayList~Vehicle~ vehicles
        -ArrayList~Rate~ rates
        -ArrayList~ParkingTicket~ tickets
        -ArrayList~Payment~ payments
        -OccupancyAdvisor advisor
        +ParkingLot(String name, int totalSpots)
        +registerVehicle(Vehicle vehicle) boolean
        +findVehicleByPlate(String plate) Vehicle
        +updateVehicleColor(String plate, String color) boolean
        +deleteOrDeactivateVehicle(String plate) String
        +registerRate(Rate rate) boolean
        +findRateById(String rateId) Rate
        +updateRate(String rateId, String name, double hourlyRate) boolean
        +deleteOrDeactivateRate(String rateId) String
        +registerEntry(String plate, String rateId) ParkingTicket
        +registerExit(String plate) Payment
        +findActiveTicketByPlate(String plate) ParkingTicket
        +listAvailableSpots() ArrayList~ParkingSpot~
        +listOccupiedSpots() ArrayList~ParkingSpot~
        +filterTickets(LocalDateTime start, LocalDateTime end, String status) ArrayList~ParkingTicket~
        +calculateRevenue(LocalDateTime start, LocalDateTime end) double
        +checkOccupancyAlert() String
    }

    class Vehicle {
        <<abstract>>
        -String plate
        -String color
        -boolean active
        +Vehicle(String plate, String color)
        +activate() void
        +deactivate() void
        +calculateFee(long minutes, Rate rate) double*
    }

    class Car {
        +Car(String plate, String color)
        +calculateFee(long minutes, Rate rate) double
    }

    class Motorcycle {
        +Motorcycle(String plate, String color)
        +calculateFee(long minutes, Rate rate) double
    }

    class ParkingSpot {
        -int spotNumber
        -boolean occupied
        +ParkingSpot(int spotNumber)
        +occupy() void
        +release() void
    }

    class Rate {
        -String rateId
        -String name
        -double hourlyRate
        -boolean active
        +Rate(String rateId, String name, double hourlyRate)
        +activate() void
        +deactivate() void
        +calculateBaseFee(long minutes) double
    }

    class ParkingTicket {
        -String ticketId
        -LocalDateTime entryTime
        -LocalDateTime exitTime
        -boolean active
        -Vehicle vehicle
        -ParkingSpot spot
        -Rate rate
        +ParkingTicket(String ticketId, Vehicle vehicle, ParkingSpot spot, Rate rate, LocalDateTime entryTime)
        +calculateMinutes() long
        +close(LocalDateTime exitTime) double
    }

    class Payment {
        -String paymentId
        -double amount
        -LocalDateTime paymentDate
        -ParkingTicket ticket
        +Payment(String paymentId, double amount, LocalDateTime paymentDate, ParkingTicket ticket)
        +buildReceipt() String
    }

    class OccupancyAdvisor {
        -double alertThreshold
        +OccupancyAdvisor(double alertThreshold)
        +calculateOccupancyPercentage(ArrayList~ParkingSpot~ spots) double
        +evaluateOccupancy(ArrayList~ParkingSpot~ spots) String
    }

    Vehicle <|-- Car
    Vehicle <|-- Motorcycle

    ParkingLot "1" *-- "1..*" ParkingSpot
    ParkingLot "1" *-- "0..*" ParkingTicket
    ParkingLot "1" *-- "0..*" Payment
    ParkingLot "1" *-- "1" OccupancyAdvisor
    ParkingLot "1" --> "0..*" Vehicle
    ParkingLot "1" --> "0..*" Rate

    ParkingTicket "0..*" --> "1" Vehicle
    ParkingTicket "0..*" --> "1" ParkingSpot
    ParkingTicket "0..*" --> "1" Rate
    Payment "0..1" --> "1" ParkingTicket
```

