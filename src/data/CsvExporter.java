package data;

import domain.Payment;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

// Exporta el reporte de pagos a un archivo CSV (separado por comas).
public class CsvExporter {

    private static final String FILE_PATH = "storage/payments_report.csv";

    // Escribe los pagos en el archivo .csv con sus encabezados de columna.
    public boolean exportPayments(ArrayList<Payment> payments) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH));
            writer.println("PagoID,TiqueteID,Placa,Fecha,Total");

            for (int i = 0; i < payments.size(); i++) {
                Payment p = payments.get(i);
                writer.println(p.getPaymentId() + ","
                        + p.getTicket().getTicketId() + ","
                        + p.getTicket().getVehicle().getPlate() + ","
                        + p.getPaymentDate() + ","
                        + p.getAmount());
            }

            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}