# Titulo del proyecto 
Smart Parking System

# Integrantes
Alan Gómez Ortiz 
Código: 0000064873

Valeria Alexandra Claro Vargas
Código: 00000064015

# Descripción general del sistema
Aplicación para el personal de un parqueadero que necesita conocer la ocupación durante la jornada y cobrar al finalizar cada estancia. El sistema vincula cada ingreso con su salida para que el operador pueda consultar los cupos disponibles y revisar la actividad del parqueadero.

# Instrucciones para ejecutar 
# Ejemplo de entrada y salida
# Diagrama de clases 
```mermaid 
classDiagram
    Parking "1" -- "0.." Spot 
    Parking "1" --> "0..*" Employee 
    Parking "1" --> "0..*" Rate 
    Parking "1" --> "0..*" Vehicle 
    Parking "1" --> "0..*" Ticket 
    
    Ticket "1" *-- "0..1" Payment 
    Ticket "0..*" --> "1" Vehicle 
    Ticket "0..*" --> "1" Spot 
    Ticket "0..*" --> "1" Rate 
    Ticket "0..*" --> "1" Employee 

    Vehicle <|-- Car
    Vehicle <|-- Motorcycle

    class Parking {
        -name: String
        -address: String
        +registerEntry(vehicle: Vehicle, spot: Spot, rate: Rate, employee: Employee): Ticket
        +registerExit(ticket: Ticket): Payment
        +calculateOccupancyPercentage(): double
        +recommendSpot(): Spot
    }

    class Employee {
        -employeeId: String
        -name: String
        -administrator: boolean
    }

    class Spot {
        -code: int
        -floor: int
        -occupied: boolean
        +isAvailable(): boolean
        +occupy(): void
        +free(): void
    }

    class Rate {
        -name: String
        -ratePerHour: double
    }

    class Vehicle {
        -plate: String
        -make: String
        -color: String
        +calculateRateFactor(): double
        +hasActiveTicket(): boolean
    }

    class Car {
        -numberOfDoors: int
    }

    class Motorcycle {
        -engineDisplacement: int
    }

    class Ticket {
        -number: String
        -entryTime: LocalDateTime
        -exitTime: LocalDateTime
        -active: boolean
        +isOpen(): boolean
        +calculateValue(exitTime: LocalDateTime): double
        +close(exitTime: LocalDateTime): Payment
        +cancel(): void
    }

    class Payment {
        -receiptNumber: String
        -value: double
        -dateTime: LocalDateTime
    }

 ```