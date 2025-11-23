package org.example;

import java.sql.*;

public class DBManager {
    private final Connection conn;

    // Initialize DB connection and setup the table
    public DBManager(String dbFile) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        createTableIfNotExists();
        clearTable(); // Clear any old data on startup
    }

    // Creates the orders table if it doesn't already exist
    private void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                "orderId INTEGER PRIMARY KEY, " +
                "customerName TEXT, " +
                "status TEXT, " +
                "amount REAL, " +
                "orderDate TEXT)";
        conn.createStatement().execute(sql);
    }

    // Insert one order into the table
    public void insertOrder(OrderEntity o) throws SQLException {
        String sql = "INSERT INTO orders (orderId, customerName, status, amount, orderDate) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, o.orderId);
        ps.setString(2, o.customerName);
        ps.setString(3, o.status);
        ps.setDouble(4, o.amount);
        ps.setString(5, o.orderDate);
        ps.executeUpdate();
    }

    // Deletes all existing rows from the orders table
    public void clearTable() throws SQLException {
        conn.createStatement().executeUpdate("DELETE FROM orders");
        System.out.println("Existing orders cleared.");
    }

    // Retrieve a single order by its ID
    public OrderEntity fetchOrderById(int orderId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE orderId = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new OrderEntity(
                    rs.getInt("orderId"),
                    rs.getString("customerName"),
                    rs.getString("status"),
                    rs.getDouble("amount"),
                    rs.getString("orderDate")
            );
        }
        return null; // No order found with given ID
    }
}
