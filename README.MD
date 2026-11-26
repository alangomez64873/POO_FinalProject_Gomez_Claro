# Titulo del proyecto 
Smart Parking System

# Integrantes
Alan Gómez Ortiz
Código: 0000064873

Valeria Alexandra Claro Vargas
Código: 0000064015

# Descripción general del sistema
Aplicación para el personal de un parqueadero que necesita conocer la ocupación durante la jornada y cobrar al finalizar cada estancia. El sistema vincula cada ingreso con su salida para que el operador pueda consultar los cupos disponibles y revisar la actividad del parqueadero.

## Entidades administrables (CRUD) y reglas del negocio
- **Vehículos (`Vehicle` / `Car` / `Motorcycle`):**
  - Permite registrar, buscar por placa exacta y listar.
  - **Restricción de eliminación:** Solo se puede eliminar físicamente si el vehículo no tiene tiquetes asociados. Si ya cuenta con historial de movimientos, se desactiva (`active = false`) para conservar la trazabilidad y no puede volver a ingresar mientras esté inactivo. Tampoco se permite eliminar ni desactivar mientras tenga un ingreso activo.
- **Tarifas (`Rate`):**
  - Permite registrar, buscar por ID, listar y modificar el nombre y valor por hora.
  - **Regla de modificación:** Los cambios en el valor por hora aplican a nuevos ingresos y a tiquetes que aún sigan activos al momento de calcular la salida. Los pagos ya generados conservan su monto original.
  - **Restricción de eliminación:** Solo se elimina si no está asociada a ningún tiquete; si tiene historial, se desactiva (`active = false`).
- **Registros históricos (`ParkingTicket` y `Payment`):**
  - No permiten edición ni eliminación libre. Los tiquetes solo pasan de estado activo a cerrado al registrar una salida válida, generando un pago inmutable que conserva el historial financiero.

# Instrucciones para ejecutar
1. Abrir la carpeta raíz del proyecto en Visual Studio Code.
2. En el explorador de archivos de la izquierda, navegar a la ruta `src/ui/` y abrir el archivo `Main.java`.
3. Hacer clic en el botón de "Play" (Run Java) ubicado en la esquina superior derecha del editor.
4. El programa se iniciará y podrá interactuar con el menú principal directamente desde la consola del editor.



# Ejemplo de entrada/salida
### Primera ejecución (registro, ingreso, salida, desactivación, filtros, CSV y guardado)
```text
Parqueadero nuevo iniciado (5 cupos, tarifas base: T1 Carro / T2 Moto).

=== PARQUEADERO CENTRAL ===
1. Registrar vehiculo
2. Listar vehiculos
3. Buscar vehiculo por placa
4. Eliminar o desactivar vehiculo
5. Registrar tarifa
6. Listar tarifas
7. Modificar tarifa
8. Eliminar o desactivar tarifa
9. Registrar ingreso
10. Registrar salida y cobrar
11. Ver cupos y alerta de ocupacion
12. Filtrar movimientos (tiquetes)
13. Ver ingresos por fechas
14. Exportar pagos a CSV
0. Guardar y salir
Opcion: 6
[T1] Tarifa Base Carro - $3000.0/hora (ACTIVA)
[T2] Tarifa Base Moto - $1500.0/hora (ACTIVA)

Opcion: 1
Tipo (1 = Carro, 2 = Moto): 1
Placa: ABC123
Color: Rojo
Vehiculo registrado.

Opcion: 1
Tipo (1 = Carro, 2 = Moto): 2
Placa: XYZ98A
Color: Negra
Vehiculo registrado.

Opcion: 9
Placa: ABC123
ID tarifa (T1 = Carro, T2 = Moto): T1
Ingreso registrado: Tiquete [TKT-1] | Placa: ABC123 | Cupo #1 | Entrada: 2026-11-26 14:16 | Salida: En curso | Estado: ACTIVO
ESTADO NORMAL: Ocupacion actual del 20.0%. Hay suficiente disponibilidad.

Opcion: 9
Placa: XYZ98A
ID tarifa (T1 = Carro, T2 = Moto): T2
Ingreso registrado: Tiquete [TKT-2] | Placa: XYZ98A | Cupo #2 | Entrada: 2026-11-26 14:17 | Salida: En curso | Estado: ACTIVO
ESTADO NORMAL: Ocupacion actual del 40.0%. Hay suficiente disponibilidad.

Opcion: 3
Placa: ABC123
Carro | Placa: ABC123 | Color: Rojo | Estado: ACTIVO
Estado: DENTRO (Cupo #1)

Opcion: 11
Libres: [Cupo #3 [DISPONIBLE], Cupo #4 [DISPONIBLE], Cupo #5 [DISPONIBLE]]
Ocupados: [Cupo #1 [OCUPADO], Cupo #2 [OCUPADO]]
ESTADO NORMAL: Ocupacion actual del 40.0%. Hay suficiente disponibilidad.

Opcion: 10
Placa: ABC123
/////////////////////////////////
          COMPROBANTE DE PAGO
/////////////////////////////////
Pago ID: PAY-1
Tiquete ID: TKT-1
Vehiculo: Carro - Placa ABC123
Cupo utilizado: #1
Tarifa aplicada: Tarifa Base Carro
Hora de entrada: 2026-11-26 14:16
Hora de salida: 2026-11-26 14:17
Tiempo total: 1 minutos
TOTAL PAGADO: $3000.0
/////////////////////////////////

Opcion: 4
Placa: ABC123
El vehiculo tiene historial, por lo tanto fue DESACTIVADO.

Opcion: 12
Fecha inicial (AAAA-MM-DD o Enter para omitir):
Fecha final (AAAA-MM-DD o Enter para omitir):
Estado (ACTIVO, CERRADO o TODOS): TODOS
Tiquete [TKT-1] | Placa: ABC123 | Cupo #1 | Entrada: 2026-11-26 14:16 | Salida: 2026-11-26 14:17 | Estado: CERRADO
Tiquete [TKT-2] | Placa: XYZ98A | Cupo #2 | Entrada: 2026-11-26 14:17 | Salida: En curso | Estado: ACTIVO

Opcion: 13
Fecha inicial (AAAA-MM-DD o Enter para omitir):
Fecha final (AAAA-MM-DD o Enter para omitir):
Total recaudado: $3000.0

Opcion: 14
Exportado en storage/payments_report.csv

Opcion: 0
Datos guardados. Hasta pronto.
```
## Segunda ejecución (verificación de persistencia)
```text
Datos cargados correctamente.

Opcion: 2
Carro | Placa: ABC123 | Color: Rojo | Estado: INACTIVO
Moto | Placa: XYZ98A | Color: Negra | Estado: ACTIVO

Opcion: 0
Datos guardados. Hasta pronto.```



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

