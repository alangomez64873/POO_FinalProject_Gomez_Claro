package domain;

import java.io.Serializable;

// Esta clase representa una tarifa de cobro por hora en el parqueadero.
// Implementa Serializable para que Java pueda guardar este objeto en un archivo.
public class Rate implements Serializable {

    // Numero de version que usa Java al guardar y leer el archivo serializado.
    private static final long serialVersionUID = 1L;

    private String rateId;
    private String name;
    private double hourlyRate;
    private boolean active;

    // Constructor: crea una nueva tarifa con los datos iniciales validados.
    public Rate(String rateId, String name, double hourlyRate) {
        if (rateId != null && !rateId.trim().equals("")) {
            this.rateId = rateId.trim().toUpperCase();
        } else {
            this.rateId = "SIN-ID";
        }

        if (name != null && !name.trim().equals("")) {
            this.name = name.trim();
        } else {
            this.name = "Tarifa sin nombre";
        }

        if (hourlyRate > 0) {
            this.hourlyRate = hourlyRate;
        } else {
            this.hourlyRate = 1000.0;
        }

        this.active = true;
    }

    public String getRateId() {
        return rateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().equals("")) {
            this.name = name.trim();
        }
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate > 0) {
            this.hourlyRate = hourlyRate;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    // Calcula el cobro base segun los minutos que estuvo el vehiculo.
    // Siempre cobra minimo 1 hora, y si sobra algun minuto adicional cobra la hora siguiente.
    public double calculateBaseFee(long minutes) {
        long hours = minutes / 60;
        long extraMinutes = minutes % 60;

        if (minutes <= 0) {
            hours = 1;
        } else if (extraMinutes > 0) {
            hours = hours + 1;
        }

        return hours * hourlyRate;
    }

    // Convierte la tarifa a un texto para mostrar en pantalla.
    @Override
    public String toString() {
        String statusText;
        if (active) {
            statusText = "ACTIVA";
        } else {
            statusText = "INACTIVA";
        }

        return "[" + rateId + "] " + name + " - $" + hourlyRate + "/hora (" + statusText + ")";
    }
}