package org.example;

public class Consumer implements Runnable {
    private final SharedBuffer buffer;
    private final DBManager dbManager;

    // Consumer needs access to the shared buffer and DB manager to function
    public Consumer(SharedBuffer buffer, DBManager dbManager) {
        this.buffer = buffer;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Take an order from the buffer (waits if empty)
                OrderEntity order = buffer.take();

                // Special condition to stop consuming
                if (order.orderId == -1) break;

                // Insert the consumed order into the database
                dbManager.insertOrder(order);
                System.out.println("Consumed & inserted: " + order);
            }
        } catch (Exception e) {
            // Log any unexpected issues during consumption
            e.printStackTrace();
        }
    }
}
