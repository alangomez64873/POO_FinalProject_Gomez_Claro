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
1. Abrir la carpeta raíz del proyecto en Visual Studio Code.
2. En el explorador de archivos de la izquierda, navegar a la ruta `src/ui/` y abrir el archivo `Main.java`.
3. Hacer clic en el botón de "Play" (Run Java) ubicado en la esquina superior derecha del editor.
4. El programa se iniciará y podrá interactuar con el menú principal directamente desde la consola del editor.
# Ejemplo de entrada y salida

```text
==========================================
   Bienvenido a Smart Parking Centro
==========================================

--- Menú Principal ---
1. Registrar Ingreso de Vehículo
2. Registrar Salida (Cobro)
3. Consultar Ocupación del Parqueadero
4. Salir
Seleccione una opción: 1
Ingrese la placa: AMF876
Ingrese la marca: Audi
Ingrese el color: Blanco
¿Es (1) Carro o (2) Moto?: 1

Ingreso registrado exitosamente.
Número de Ticket: TKT-1
Espacio asignado: 1

--- Menú Principal ---
1. Registrar Ingreso de Vehículo
2. Registrar Salida (Cobro)
3. Consultar Ocupación del Parqueadero
4. Salir
Seleccione una opción: 2
Ingrese el número de ticket (ej. TKT-1): TKT-1

--- Recibo de Pago ---
Recibo No: REC-TKT-1
Total a Pagar: $5000.0
Fecha y Hora: 2026-11-19T16:43:31.704432
¡Gracias por su visita!

--- Menú Principal ---
1. Registrar Ingreso de Vehículo
2. Registrar Salida (Cobro)
3. Consultar Ocupación del Parqueadero
4. Salir
Seleccione una opción: 1
Ingrese la placa: MGF876
Ingrese la marca: AKT
Ingrese el color: Azul
¿Es (1) Carro o (2) Moto?: 2

Ingreso registrado exitosamente.
Número de Ticket: TKT-2
Espacio asignado: 1

--- Menú Principal ---
1. Registrar Ingreso de Vehículo
2. Registrar Salida (Cobro)
3. Consultar Ocupación del Parqueadero
4. Salir
Seleccione una opción: 1
Ingrese la placa: ANF967
Ingrese la marca: Toyota
Ingrese el color: Negro
¿Es (1) Carro o (2) Moto?: 1

Ingreso registrado exitosamente.
Número de Ticket: TKT-3
Espacio asignado: 2

--- Menú Principal ---
1. Registrar Ingreso de Vehículo
2. Registrar Salida (Cobro)
3. Consultar Ocupación del Parqueadero
4. Salir
Seleccione una opción: 2
Ingrese el número de ticket (ej. TKT-1): TKT-3

--- Recibo de Pago ---
Recibo No: REC-TKT-3
Total a Pagar: $5000.0
Fecha y Hora: 2026-11-19T16:44:20.294433200
¡Gracias por su visita!

--- Menú Principal ---
1. Registrar Ingreso de Vehículo
2. Registrar Salida (Cobro)
3. Consultar Ocupación del Parqueadero
4. Salir
Seleccione una opción: 3
Ocupación actual: 10,00%

--- Menú Principal ---
1. Registrar Ingreso de Vehículo
2. Registrar Salida (Cobro)
3. Consultar Ocupación del Parqueadero
4. Salir
Seleccione una opción: salir
Exception in thread "main" java.util.InputMismatchException
        at java.base/java.util.Scanner.throwFor(Unknown Source)
        at java.base/java.util.Scanner.next(Unknown Source)
        at java.base/java.util.Scanner.nextInt(Unknown Source)
        at java.base/java.util.Scanner.nextInt(Unknown Source)
        at ui.Main.main(Main.java:35)
PS C:\Users\Alan Gomez\Desktop\POO FINAL_PROJECT>
```
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