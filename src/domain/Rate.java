package domain;

public class Rate {
    private String name;
    private double ratePerHour;

    public Rate(String name, double ratePerHour) {
        this.name = name;
        this.ratePerHour = ratePerHour;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getRatePerHour() { return ratePerHour; }
    public void setRatePerHour(double ratePerHour) { this.ratePerHour = ratePerHour; }
}