package domain;

public class Spot {
    private int code;
    private int floor;
    private boolean occupied;

    public Spot(int code, int floor) {
        this.code = code;
        this.floor = floor;
        this.occupied = false;
    }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public int getFloor() { return floor; }
    public void setFloor(int floor) { this.floor = floor; }

    public boolean isOccupied() { return occupied; }

    // Métodos adicionales de negocio según el diagrama
    public boolean isAvailable() { 
        return !occupied; 
    }
    
    public void occupy() { 
        this.occupied = true; 
    }
    
    public void free() { 
        this.occupied = false; 
    }
}