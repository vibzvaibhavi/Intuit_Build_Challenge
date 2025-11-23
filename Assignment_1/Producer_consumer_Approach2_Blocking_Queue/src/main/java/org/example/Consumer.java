package org.example;

import java.util.concurrent.BlockingQueue;

/**
 * Consumer reads orders from the shared queue and stores them in the database.
 * Stops when it receives a poison pill (orderId = -1).
 */
public class Consumer implements Runnable {

    private final BlockingQueue<OrderEntity> queue;
    private final DBManager dbManager;

    // Initialize with shared queue and DB manager
    public Consumer(BlockingQueue<OrderEntity> queue, DBManager dbManager) {
        this.queue = queue;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try {
            while (true) {
                OrderEntity order = queue.take();

                // Poison pill signals the end of data
                if (order.orderId == -1) break;

                dbManager.insertOrder(order);
                System.out.println("Consumed & inserted: " + order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
