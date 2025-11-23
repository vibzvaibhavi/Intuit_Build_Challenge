package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        // Shared buffer for communication between producer and consumer
        SharedBuffer buffer = new SharedBuffer();

        // File paths
        String jsonPath = "src/main/resources/orders.json";
        String dbPath = "orders.db";

        // Initialize DB connection and setup
        DBManager dbManager = new DBManager(dbPath);

        // Create and start producer and consumer threads
        Thread producer = new Thread(new Producer(buffer, jsonPath));
        Thread consumer = new Thread(new Consumer(buffer, dbManager));

        producer.start();
        consumer.start();

        // Wait for both threads to finish
        producer.join();
        consumer.join();

        System.out.println("All orders written to DB.");
    }
}
