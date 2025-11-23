package org.example;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DBManager to verify SQLite operations.
 */
public class DBManagerTest {

    static DBManager db;

    // Initialize database once before all tests run
    @BeforeAll
    public static void init() throws Exception {
        db = new DBManager("test_orders.db");
        db.clearTable(); // Ensure the test DB starts empty
    }

    /**
     * Tests that inserting an order into the database using DBManager
     * actually creates a row in the SQLite 'orders' table with the
     * correct values stored.
     */
    @Test
    public void testInsertOrder() throws Exception {
        // Create test order
        OrderEntity o = new OrderEntity(999, "TestUser", "TESTING", 123.45, "2025-01-01");
        db.insertOrder(o);

        // Validate insertion by querying SQLite directly
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test_orders.db");
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders WHERE orderId = ?");
        ps.setInt(1, 999);
        ResultSet rs = ps.executeQuery();

        assertTrue(rs.next()); // Ensure row exists
        assertEquals("TestUser", rs.getString("customerName"));
        assertEquals(123.45, rs.getDouble("amount"));
    }
}
