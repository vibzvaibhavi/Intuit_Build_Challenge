package org.example;

import org.junit.jupiter.api.*;
import java.sql.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DBManager to verify SQLite operations.
 */
public class DBManagerTest {

    static DBManager db;

    // Setup the DB once before all tests
    @BeforeAll
    public static void init() throws Exception {
        db = new DBManager("test_orders.db");
        db.clearTable();
    }

    /**
     * Tests that inserting an order correctly stores it in the database.
     */
    @Test
    public void testInsertOrder() throws Exception {
        OrderEntity o = new OrderEntity(999, "TestUser", "TESTING", 123.45, "2025-01-01");
        db.insertOrder(o);

        // Query directly to verify insert
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test_orders.db");
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM orders WHERE orderId = ?");
        ps.setInt(1, 999);
        ResultSet rs = ps.executeQuery();

        assertTrue(rs.next());
        assertEquals("TestUser", rs.getString("customerName"));
        assertEquals(123.45, rs.getDouble("amount"));
    }
}
