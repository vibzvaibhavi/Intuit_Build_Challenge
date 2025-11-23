package org.example;

public class OrderEntity {
    public int orderId;
    public String customerName;
    public String status;
    public double amount;
    public String orderDate;

    // Basic constructor to initialize all fields
    public OrderEntity(int orderId, String customerName, String status, double amount, String orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.status = status;
        this.amount = amount;
        this.orderDate = orderDate;
    }

    // Custom string representation for logging/debugging
    @Override
    public String toString() {
        return orderId + " | " + customerName + " | " + status + " | $" + amount + " | " + orderDate;
    }
}
