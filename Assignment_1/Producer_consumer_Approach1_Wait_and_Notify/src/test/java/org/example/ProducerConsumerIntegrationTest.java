package org.example;

import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Full integration test using SharedBuffer, Producer, and Consumer.
 */
public class ProducerConsumerIntegrationTest {

    @Test
    public void testProducerConsumerIntegration() throws Exception {
        //Write test JSON input
        String filePath = "src/test/resources/full_flow.json";
        Files.createDirectories(Path.of("src/test/resources"));

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("""
                [
                  {"orderId":303,"customerName":"EndToEnd","status":"DONE","amount":99.0,"orderDate":"2025-03-03"}
                ]
            """);
        }

        // Setup shared buffer and DB
        SharedBuffer buffer = new SharedBuffer();
        DBManager db = new DBManager("test_integration.db");
        db.clearTable();

        //Run Producer and Consumer in separate threads
        Thread producerThread = new Thread(new Producer(buffer, filePath));
        Thread consumerThread = new Thread(new Consumer(buffer, db));

        producerThread.start();
        consumerThread.start();

        // Wait for both threads to finish
        producerThread.join();
        consumerThread.join();

        //  Assert DB insertion worked
        OrderEntity result = db.fetchOrderById(303);
        assertNotNull(result);
        assertEquals("EndToEnd", result.customerName);
        assertEquals("DONE", result.status);
        assertEquals(99.0, result.amount);

        System.out.println("✅ Producer-Consumer integration test passed.");
    }
}
