package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OrderEntity class.
 */
public class OrderEntityTest {

    /**
     * Verifies that the overridden toString() method in OrderEntity
     * returns the expected formatted string representation.
     */
    @Test
    public void testToString() {
        OrderEntity o = new OrderEntity(1, "Raj", "PENDING", 99.99, "2025-11-20");
        String expected = "1 | Raj | PENDING | $99.99 | 2025-11-20";
        assertEquals(expected, o.toString());
    }
}
