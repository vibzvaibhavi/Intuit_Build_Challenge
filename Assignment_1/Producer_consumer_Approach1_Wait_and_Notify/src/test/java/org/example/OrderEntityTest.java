package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OrderEntity class.
 */
public class OrderEntityTest {

    /**
     * Verifies that the constructor correctly assigns all fields.
     */
    @Test
    public void testConstructorSetsFields() {
        OrderEntity o = new OrderEntity(10, "Alice", "NEW", 50.5, "2025-01-01");
        assertEquals(10, o.orderId);
        assertEquals("Alice", o.customerName);
        assertEquals("NEW", o.status);
        assertEquals(50.5, o.amount);
        assertEquals("2025-01-01", o.orderDate);
    }

    /**
     * Verifies that toString() returns the expected formatted string.
     */
    @Test
    public void testToStringFormat() {
        OrderEntity o = new OrderEntity(1, "Raj", "PENDING", 99.99, "2025-11-20");
        String expected = "1 | Raj | PENDING | $99.99 | 2025-11-20";
        assertEquals(expected, o.toString());
    }

    /**
     * Tests how the entity handles empty/zero values.
     */
    @Test
    public void testEdgeCaseValues() {
        OrderEntity o = new OrderEntity(0, "", "", 0.0, "");
        assertEquals(0, o.orderId);
        assertEquals("", o.customerName);
        assertEquals("", o.status);
        assertEquals(0.0, o.amount);
        assertEquals("", o.orderDate);
    }
}
