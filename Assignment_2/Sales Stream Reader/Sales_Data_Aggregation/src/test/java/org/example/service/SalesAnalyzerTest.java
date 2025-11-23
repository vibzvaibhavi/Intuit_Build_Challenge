package org.example.service;

import org.example.model.SalesRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SalesAnalyzerTest {

    private static List<SalesRecord> sampleRecords;

    @BeforeAll
    public static void setup() {
        // Predefine a small list of records to test against
        sampleRecords = Arrays.asList(
                new SalesRecord(1, "Alice", "North", "Electronics", 500.0, LocalDate.of(2023, 1, 10)),
                new SalesRecord(2, "Bob", "South", "Furniture", 300.0, LocalDate.of(2023, 2, 5)),
                new SalesRecord(3, "Carol", "North", "Electronics", 700.0, LocalDate.of(2023, 3, 15)),
                new SalesRecord(4, "Dave", "East", "Clothing", 150.0, LocalDate.of(2023, 1, 20)),
                new SalesRecord(5, "Eve", "South", "Furniture", 450.0, LocalDate.of(2023, 4, 10))
        );
    }

    @Test
    public void testTotalSales() {
        // Total amount across all sales
        double total = SalesAnalyzer.getTotalSales(sampleRecords);
        assertEquals(2100.0, total, 0.001);
    }

    @Test
    public void testSalesByRegion() {
        // Total sales grouped by region
        Map<String, Double> regionSales = SalesAnalyzer.getSalesByRegion(sampleRecords);
        assertEquals(1200.0, regionSales.get("North"), 0.001);
        assertEquals(750.0, regionSales.get("South"), 0.001);
        assertEquals(150.0, regionSales.get("East"), 0.001);
    }

    @Test
    public void testAverageByCategory() {
        // Average sale amount per product category
        Map<String, Double> avgByCat = SalesAnalyzer.getAverageByCategory(sampleRecords);
        assertEquals(600.0, avgByCat.get("Electronics"), 0.001);
        assertEquals(375.0, avgByCat.get("Furniture"), 0.001);
        assertEquals(150.0, avgByCat.get("Clothing"), 0.001);
    }

    @Test
    public void testTopNSales() {
        // Top 2 sales by amount
        List<SalesRecord> top = SalesAnalyzer.getTopNSales(sampleRecords, 2);
        assertEquals(700.0, top.get(0).getAmount());
        assertEquals(500.0, top.get(1).getAmount());
    }

    @Test
    public void testSalesAfterDate() {
        // Records that occurred strictly after Feb 1, 2023
        List<SalesRecord> recent = SalesAnalyzer.getSalesAfterDate(sampleRecords, LocalDate.of(2023, 2, 1));
        assertEquals(3, recent.size());
    }

    @Test
    public void testMinSale() {
        // Minimum sale record by amount
        SalesRecord min = SalesAnalyzer.getMinSale(sampleRecords);
        assertNotNull(min);
        assertEquals(150.0, min.getAmount(), 0.001);
    }

    @Test
    public void testMaxSale() {
        // Maximum sale record by amount
        SalesRecord max = SalesAnalyzer.getMaxSale(sampleRecords);
        assertNotNull(max);
        assertEquals(700.0, max.getAmount(), 0.001);
    }

    @Test
    public void testCountByRegion() {
        // Number of sales entries per region
        Map<String, Long> count = SalesAnalyzer.getCountByRegion(sampleRecords);
        assertEquals(2L, count.get("North"));
        assertEquals(2L, count.get("South"));
        assertEquals(1L, count.get("East"));
    }

    @Test
    public void testMonthlySales() {
        // Aggregated totals per month in yyyy-MM format
        Map<String, Double> monthly = SalesAnalyzer.getMonthlySales(sampleRecords);
        assertEquals(650.0, monthly.get("2023-01"), 0.001); // Alice + Dave
        assertEquals(300.0, monthly.get("2023-02"), 0.001);
        assertEquals(700.0, monthly.get("2023-03"), 0.001);
        assertEquals(450.0, monthly.get("2023-04"), 0.001);
    }

    @Test
    public void testMostFrequentCategory() {
        // Category with the highest number of sales
        String mostCommon = SalesAnalyzer.getMostFrequentCategory(sampleRecords);
        assertEquals("Electronics", mostCommon); // appears twice
    }

    @Test
    public void testOrdersBetween() {
        // Orders that occurred between Jan 15 and Mar 31 (inclusive)
        List<SalesRecord> filtered = SalesAnalyzer.getOrdersBetween(
                sampleRecords,
                LocalDate.of(2023, 1, 15),
                LocalDate.of(2023, 3, 31)
        );
        assertEquals(3, filtered.size()); // Bob, Carol, Dave
    }
}
