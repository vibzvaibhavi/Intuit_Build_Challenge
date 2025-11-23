package org.example;

/**
 * Represents a single order with fields for ID, customer name, status, amount, and date.
 */
public class OrderEntity {
    public int orderId;
    public String customerName;
    public String status;
    public double amount;
    public String orderDate;

    // Constructor to initialize an order with all relevant fields
    public OrderEntity(int orderId, String customerName, String status, double amount, String orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.status = status;
        this.amount = amount;
        this.orderDate = orderDate;
    }

    // Returns a formatted string representation of the order (used for logging)
    @Override
    public String toString() {
        return orderId + " | " + customerName + " | " + status + " | $" + amount + " | " + orderDate;
    }
}
