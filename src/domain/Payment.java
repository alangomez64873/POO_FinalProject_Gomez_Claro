package domain;

import java.time.LocalDateTime;

public class Payment {
    private String receiptNumber;
    private double value;
    private LocalDateTime dateTime;

    public Payment(String receiptNumber, double value, LocalDateTime dateTime) {
        this.receiptNumber = receiptNumber;
        this.value = value;
        this.dateTime = dateTime;
    }

    public String getReceiptNumber() { return receiptNumber; }
    public double getValue() { return value; }
    public LocalDateTime getDateTime() { return dateTime; }
}
