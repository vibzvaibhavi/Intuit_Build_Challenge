package org.example.service;

import org.example.model.SalesRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides various analytical operations over a collection of SalesRecord objects.
 * Each method uses Java Streams to perform filtering, grouping, and aggregation.
 *
 * This class keeps all "business logic" separate from Main, so Main only handles I/O
 * and presentation of results.
 */
public class SalesAnalyzer {

    /**
     * Calculates the total revenue across all sales.
     */
    public static double getTotalSales(List<SalesRecord> records) {
        // Sum the amount of all sales using map → sum pipeline
        return records.stream()
                .mapToDouble(SalesRecord::getAmount)
                .sum();
    }

    /**
     * Groups total sales volume by region.
     * Example: {East = 2150.33, West = 1900.50}
     */
    public static Map<String, Double> getSalesByRegion(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getRegion,
                        Collectors.summingDouble(SalesRecord::getAmount)
                ));
    }

    /**
     * Computes average sale amount for each product category.
     */
    public static Map<String, Double> getAverageByCategory(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getCategory,
                        Collectors.averagingDouble(SalesRecord::getAmount)
                ));
    }

    /**
     * Returns the highest N sales sorted in descending order.
     * Used to get "Top 3" or "Top 5" type statistics.
     */
    public static List<SalesRecord> getTopNSales(List<SalesRecord> records, int n) {
        return records.stream()
                .sorted(Comparator.comparingDouble(SalesRecord::getAmount).reversed())
                .limit(n)
                .toList();
    }

    /**
     * Returns all sales strictly after the given date.
     */
    public static List<SalesRecord> getSalesAfterDate(List<SalesRecord> records, LocalDate date) {
        return records.stream()
                .filter(r -> r.getOrderDate().isAfter(date))
                .toList();
    }

    /**
     * Finds the smallest sale (by dollar value).
     */
    public static SalesRecord getMinSale(List<SalesRecord> records) {
        return records.stream()
                .min(Comparator.comparingDouble(SalesRecord::getAmount))
                .orElse(null);
    }

    /**
     * Finds the largest sale (by dollar value).
     */
    public static SalesRecord getMaxSale(List<SalesRecord> records) {
        return records.stream()
                .max(Comparator.comparingDouble(SalesRecord::getAmount))
                .orElse(null);
    }

    /**
     * Counts how many sales occurred in each region.
     * Example: {North=25, South=28, East=23, West=24}
     */
    public static Map<String, Long> getCountByRegion(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getRegion,
                        Collectors.counting()
                ));
    }

    /**
     * Aggregates total sales grouped by year-month (e.g., "2023-05").
     * A TreeMap ensures the results are naturally sorted by date.
     */
    public static Map<String, Double> getMonthlySales(List<SalesRecord> records) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getOrderDate().format(monthFormatter),
                        TreeMap::new,
                        Collectors.summingDouble(SalesRecord::getAmount)
                ));
    }

    /**
     * Determines which category appears most frequently in the dataset.
     * Returns the category name, or "No Data" if the list is empty.
     */
    public static String getMostFrequentCategory(List<SalesRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(SalesRecord::getCategory, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No Data");
    }

    /**
     * Returns all sales within the inclusive date range.
     */
    public static List<SalesRecord> getOrdersBetween(List<SalesRecord> records,
                                                     LocalDate start,
                                                     LocalDate end) {
        return records.stream()
                .filter(r ->
                        !r.getOrderDate().isBefore(start) &&
                                !r.getOrderDate().isAfter(end)
                )
                .toList();
    }
}
