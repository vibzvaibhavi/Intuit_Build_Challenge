package org.example;

import org.example.model.SalesRecord;
import org.example.service.SalesAnalyzer;
import org.example.utils.CSVReader;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {

    private static void printHeader(String title) {
        System.out.println("");
        System.out.println(title);
        System.out.println("====================================");
    }

    public static void main(String[] args) {
        String path = "src/main/resources/sales_data_large.csv";
        List<SalesRecord> records;

        try {
            records = CSVReader.readSalesData(path);
        } catch (Exception e) {
            System.err.println("Failed to load data from: " + path);
            e.printStackTrace();
            return;
        }

        if (records.isEmpty()) {
            System.out.println("No sales records found. Exiting.");
            return;
        }

        printHeader("Total Sales");
        System.out.printf("Total Sales: $%.2f%n%n", SalesAnalyzer.getTotalSales(records));

        printHeader("Min and Max Sale");
        System.out.println("Minimum Sale: " + SalesAnalyzer.getMinSale(records));
        System.out.println("Maximum Sale: " + SalesAnalyzer.getMaxSale(records));

        printHeader("Sales by Region");
        Map<String, Double> regionTotals = SalesAnalyzer.getSalesByRegion(records);
        regionTotals.forEach((region, total) ->
                System.out.printf("%-10s : $%.2f%n", region, total)
        );

        printHeader("Average Sale by Category");
        SalesAnalyzer.getAverageByCategory(records)
                .forEach((cat, avg) ->
                        System.out.printf("%-12s : $%.2f%n", cat, avg));

        printHeader("Monthly Sales (2023)");
        SalesAnalyzer.getMonthlySales(records)
                .forEach((month, total) ->
                        System.out.printf("%s : $%.2f%n", month, total));

        printHeader("Order Count by Region");
        SalesAnalyzer.getCountByRegion(records)
                .forEach((region, count) ->
                        System.out.printf("%-10s : %d orders%n", region, count));

        printHeader("Most Frequent Category");
        System.out.println("Most Popular: " + SalesAnalyzer.getMostFrequentCategory(records));

        printHeader("Top 3 Highest Sales");
        SalesAnalyzer.getTopNSales(records, 3)
                .forEach(System.out::println);

        printHeader("Orders from June 2023");
        SalesAnalyzer.getOrdersBetween(records,
                        LocalDate.of(2023, 6, 1),
                        LocalDate.of(2023, 6, 30))
                .forEach(System.out::println);

        printHeader("Analysis Complete");
        System.out.println("Analyzed " + records.size() + " sales records.");
    }
}
