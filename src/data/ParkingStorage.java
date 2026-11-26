package data;

import domain.ParkingLot;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// Guarda y carga todo el parqueadero en un archivo serializado (.dat).
public class ParkingStorage {

    private static final String FILE_PATH = "storage/parking.dat";

    // Guarda el objeto ParkingLot completo en el archivo.
    public boolean save(ParkingLot parkingLot) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
            out.writeObject(parkingLot);
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Recupera el objeto ParkingLot guardado (devuelve null si aun no existe archivo).
    public ParkingLot load() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH));
            // (ParkingLot) le indica a Java que el objeto leido es de tipo ParkingLot.
            ParkingLot parkingLot = (ParkingLot) in.readObject();
            in.close();
            return parkingLot;
        } catch (Exception e) {
            return null;
        }
    }
}