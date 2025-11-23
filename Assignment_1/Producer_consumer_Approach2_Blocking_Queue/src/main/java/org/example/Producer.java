package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

/**
 * Producer reads orders from a JSON file and places them into the shared BlockingQueue.
 */
public class Producer implements Runnable {

    private final BlockingQueue<OrderEntity> queue;
    private final String filePath;

    // Initialize with shared queue and the JSON file path
    public Producer(BlockingQueue<OrderEntity> queue, String filePath) {
        this.queue = queue;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            // Read and parse the JSON file containing the orders
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray orders = new JSONArray(content);

            // Convert JSON objects to OrderEntity and add them to the queue
            for (int i = 0; i < orders.length(); i++) {
                JSONObject obj = orders.getJSONObject(i);
                OrderEntity order = new OrderEntity(
                        obj.getInt("orderId"),
                        obj.getString("customerName"),
                        obj.getString("status"),
                        obj.getDouble("amount"),
                        obj.getString("orderDate")
                );

                //Adds the order into the queue
                queue.put(order);
                System.out.println("Produced: " + order);
            }

            // Poison pill to signal the consumer to stop
            queue.put(new OrderEntity(-1, "POISON", "", 0.0, ""));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
