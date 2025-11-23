package org.example.utils;

import org.example.model.SalesRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for reading and parsing sales data from a CSV file.
 * Each row in the CSV file is converted into a SalesRecord object.
 *
 * Expected CSV format:
 * OrderID,CustomerName,Region,Category,Amount,OrderDate
 */
public class CSVReader {

    /**
     * Reads the CSV file at the given path and parses it into a list of SalesRecord objects.
     *
     * @param path Path to the CSV file (e.g., "data/sales_data_large.csv")
     * @return List of parsed sales records
     * @throws Exception if the file can't be read or parsed
     */
    public static List<SalesRecord> readSalesData(String path) throws Exception {
        List<SalesRecord> records = new ArrayList<>();

        // Try-with-resources ensures the reader is closed automatically
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            br.readLine(); // Skip header row

            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");

                // Assumes the CSV format is consistent and values are clean
                SalesRecord record = new SalesRecord(
                        Integer.parseInt(p[0]),   // OrderID
                        p[1],                     // CustomerName
                        p[2],                     // Region
                        p[3],                     // Category
                        Double.parseDouble(p[4]), // Amount
                        LocalDate.parse(p[5])     // OrderDate (ISO format: yyyy-MM-dd)
                );

                records.add(record);
            }
        }

        return records;
    }
}
