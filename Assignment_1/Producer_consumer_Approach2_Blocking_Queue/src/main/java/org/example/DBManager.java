package org.example;

import java.sql.*;

/**
 * DBManager handles all SQLite database operations:
 * - connecting to the DB
 * - creating the orders table
 * - inserting orders
 * - clearing data for clean test runs
 * - fetching orders for validation
 */
public class DBManager {

    private Connection conn;

    // Establish connection and ensure table exists
    public DBManager(String dbFile) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        createTableIfNotExists();
        clearTable(); // Clear existing data on startup
    }

    // Create the orders table if it does not already exist
    private void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                "orderId INTEGER PRIMARY KEY, " +
                "customerName TEXT, " +
                "status TEXT, " +
                "amount REAL, " +
                "orderDate TEXT)";
        conn.createStatement().execute(sql);
    }

    // Insert a single order record into the database
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

    // Deletes all rows from the table (used to ensure clean runs)
    public void clearTable() throws SQLException {
        String sql = "DELETE FROM orders";
        conn.createStatement().executeUpdate(sql);
        System.out.println("Existing orders cleared.");
    }

    // Fetch a single order by ID (used in tests)
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
        return null;
    }
}
