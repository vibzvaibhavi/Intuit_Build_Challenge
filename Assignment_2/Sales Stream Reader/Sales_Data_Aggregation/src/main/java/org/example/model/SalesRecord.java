package org.example.model;

import java.time.LocalDate;

/**
 * Represents a single sales transaction record parsed from the CSV file.
 * Contains basic customer, region, category, amount, and order date info.
 */
public class SalesRecord {

    private int orderId;
    private String customerName;
    private String region;
    private String category;
    private double amount;
    private LocalDate orderDate;

    /**
     * Constructs a SalesRecord from the parsed CSV row.
     *
     * @param orderId        Unique ID for the sale
     * @param customerName   Name of the buyer
     * @param region         Region of the sale (e.g., East, West)
     * @param category       Product category (e.g., Electronics, Furniture)
     * @param amount         Sale amount in USD
     * @param orderDate      Date the order was placed
     */
    public SalesRecord(int orderId,
                       String customerName,
                       String region,
                       String category,
                       double amount,
                       LocalDate orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.region = region;
        this.category = category;
        this.amount = amount;
        this.orderDate = orderDate;
    }

    // Standard getters

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRegion() {
        return region;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Returns a simple string representation of the record.
     * Useful for logging or console display.
     */
    @Override
    public String toString() {
        return String.format(
                "%d | %s | %s | %s | %.2f | %s",
                orderId, customerName, region, category, amount, orderDate
        );
    }
}
