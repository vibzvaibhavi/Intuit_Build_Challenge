package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws Exception {

        // Create a bounded BlockingQueue to share data between producer and consumer
        BlockingQueue<OrderEntity> queue = new LinkedBlockingQueue<>(5);

        // Define file paths for the source JSON and target SQLite database
        String jsonPath = "src/main/resources/orders.json";
        String dbPath = "orders.db";  // SQLite DB file will be created in root

        DBManager dbManager = new DBManager(dbPath);

        // Create producer and consumer threads using the shared queue and resources
        Thread producer = new Thread(new Producer(queue, jsonPath));
        Thread consumer = new Thread(new Consumer(queue, dbManager));

        // Start both threads
        producer.start();
        consumer.start();

        // Wait for both threads to complete
        producer.join();
        consumer.join();

        // Final confirmation after processing is complete
        System.out.println("All orders written to SQLite DB.");
    }
}
