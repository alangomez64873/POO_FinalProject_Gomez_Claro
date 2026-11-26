package domain;

import java.io.Serializable;
import java.util.ArrayList;

// analiza la ocupacion del parqueadero: Avisa cuando el parqueadero se aproxima a su capacidad máxima
public class OccupancyAdvisor implements Serializable {

    private static final long serialVersionUID = 1L;

    private double alertThreshold;

    // Constructor: recibe el porcentaje limite para lanzar advertencia (80.0).
    public OccupancyAdvisor(double alertThreshold) {
        if (alertThreshold > 0 && alertThreshold <= 100) {
            this.alertThreshold = alertThreshold;
        } else {
            this.alertThreshold = 80.0;
        }
    }

    // Devuelve el porcentaje limite configurado para alertas.
    public double getAlertThreshold() {
        return alertThreshold;
    }

    // Calcula el porcentaje actual de cupos ocupados (de 0.0 a 100.0).
    public double calculateOccupancyPercentage(ArrayList<ParkingSpot> spots) {
        if (spots == null || spots.isEmpty()) {
            return 0.0;
        }

        int occupiedCount = 0;
        for (int i = 0; i < spots.size(); i++) {
            ParkingSpot currentSpot = spots.get(i);
            if (currentSpot.isOccupied()) {
                occupiedCount = occupiedCount + 1;
            }
        }

        // Multiplicar por 100.0 convierte el calculo a decimal (double) automaticamente.
        double percentage = (occupiedCount * 100.0) / spots.size();
        return percentage;
    }

    // Evaluar ocupación y dar recomendación al empleado
    public String evaluateOccupancy(ArrayList<ParkingSpot> spots) {
        double percentage = calculateOccupancyPercentage(spots);

        if (percentage >= 100.0) {
            return "ALERTA: El parqueadero esta lleno (100.0% de ocupacion). No permita mas ingresos hasta liberar un cupo.";
        } else if (percentage >= alertThreshold) {
            return "ADVERTENCIA: Ocupacion alta (" + percentage + "%). El parqueadero se acerca a su capacidad maxima.";
        } else {
            return "ESTADO NORMAL: Ocupacion actual del " + percentage + "%. Hay suficiente disponibilidad.";
        }
    }
}