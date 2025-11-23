package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Producer implements Runnable {
    private final SharedBuffer buffer;
    private final String filePath;

    // Producer needs access to shared buffer and the JSON file path
    public Producer(SharedBuffer buffer, String filePath) {
        this.buffer = buffer;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            // Read entire JSON file content
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray orders = new JSONArray(content);

            // Parse each order and add to buffer
            for (int i = 0; i < orders.length(); i++) {
                JSONObject obj = orders.getJSONObject(i);
                OrderEntity order = new OrderEntity(
                        obj.getInt("orderId"),
                        obj.getString("customerName"),
                        obj.getString("status"),
                        obj.getDouble("amount"),
                        obj.getString("orderDate")
                );
                buffer.put(order); // Send to buffer
                System.out.println("Produced: " + order);
            }

            // Add poison pill to signal consumer to stop
            buffer.put(new OrderEntity(-1, "POISON", "", 0.0, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
