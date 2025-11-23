package org.example;

public class SharedBuffer {
    private OrderEntity order;
    private boolean available = false;

    // Producer puts an order into the buffer
    public synchronized void put(OrderEntity order) throws InterruptedException {
        while (available) {
            wait(); // Wait until the previous item is consumed
        }
        this.order = order;
        available = true;
        notifyAll(); // Notify consumer that data is available
    }

    // Consumer takes an order from the buffer
    public synchronized OrderEntity take() throws InterruptedException {
        while (!available) {
            wait(); // Wait until an item is available to consume
        }
        available = false;
        notifyAll(); // Notify producer that space is free
        return order;
    }
}
