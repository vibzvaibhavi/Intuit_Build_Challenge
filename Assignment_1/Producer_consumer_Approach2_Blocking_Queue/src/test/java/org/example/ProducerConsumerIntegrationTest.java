package org.example;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Producer and Consumer classes.
 */
public class ProducerConsumerIntegrationTest {

    /**
     * Tests if Producer correctly reads orders from JSON
     * and inserts them into the blocking queue.
     */
    @Test
    public void testProducerAddsToQueue() throws Exception {
        // Create test JSON file
        String filePath = "src/test/resources/test_orders.json";
        Files.createDirectories(Path.of("src/test/resources"));
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("[{\"orderId\":1,\"customerName\":\"Test\",\"status\":\"NEW\",\"amount\":100.0,\"orderDate\":\"2025-01-01\"}]");
        }

        // Setup blocking queue and run producer
        BlockingQueue<OrderEntity> queue = new LinkedBlockingQueue<>(5);
        Producer producer = new Producer(queue, filePath);
        producer.run(); // direct invocation for test

        // Verify that one order and one poison pill are in the queue
        assertEquals(2, queue.size());
        OrderEntity o = queue.take();
        assertEquals("Test", o.customerName);
    }

    /**
     * Tests if Consumer takes orders from the queue and
     * inserts them into the SQLite database correctly.
     */
    @Test
    public void testConsumerInsertsToDB() throws Exception {
        // Setup test DB and clear table
        BlockingQueue<OrderEntity> queue = new LinkedBlockingQueue<>(5);
        DBManager dbManager = new DBManager("test_consume.db");
        dbManager.clearTable();

        // Put sample order and poison pill in the queue
        OrderEntity testOrder = new OrderEntity(2025, "Tester", "DONE", 75.0, "2025-01-02");
        queue.put(testOrder);
        queue.put(new OrderEntity(-1, "POISON", "", 0.0, ""));

        // Run consumer directly
        Consumer consumer = new Consumer(queue, dbManager);
        consumer.run();

        // Verify the order was inserted into the DB
        OrderEntity fetched = dbManager.fetchOrderById(2025);
        assertNotNull(fetched);
        assertEquals("Tester", fetched.customerName);
        assertEquals("DONE", fetched.status);
        assertEquals(75.0, fetched.amount);
    }
}
