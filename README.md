#  Intuit Build Challenge 

**Technology Stack:** **Java** • **Maven** • **JUnit** • **SQLite** • **Concurrency** • **Streams**

This repository contains solutions for the Intuit Build Challenge assignments, demonstrating expertise in Java concurrency, data pipelines, and modern stream-based analytics.

---

##  Assignment 1 – Producer–Consumer Pattern 

### Overview
This assignment implements the classic **Producer–Consumer** synchronization pattern, demonstrating **concurrency**, **thread communication**, and controlled data flow between two independent components.

The program simulates a real-world data pipeline:
* **Producer Thread** → Reads orders from a **JSON** source container.
* **Shared Buffer/Queue** → Transfers one item at a time.
* **Consumer Thread** → Inserts records into a **SQLite** destination container.

Two distinct implementations are provided to fully satisfy concurrency requirements and showcase different approaches:

| Approach | Synchronization Mechanism | Key Feature |
| :--- | :--- | :--- |
| **Approach 1** | Custom `wait` & `notify` | Low-level, single-slot buffer implementation. |
| **Approach 2** | `BlockingQueue` | Modern, built-in Java concurrency utility. |

Both versions use a **poison pill** (orderId = -1) to terminate gracefully.

###  Key Features
1.  **Bounded Blocking Buffer (Two Implementations)**
    * Custom one-slot buffer using `wait`/`notify`.
    * Java `LinkedBlockingQueue` version with built-in blocking.
2.  **Safe Thread Synchronization**
    * Producer blocks when buffer is **full**.
    * Consumer blocks when buffer is **empty**.
    * Uses `synchronized`, `wait()`, and `notifyAll()` primitives.
    * Guaranteed **no race conditions** and **clean shutdown**.
3.  **Durable SQLite Database Integration**
    * Consumer writes processed orders to a local **SQLite** table.
4.  **Full JUnit Test Coverage**
    * Tests validate buffer capacity, blocking behavior, thread correctness, and DB insertion logic.

### ➤ Approach 1 — Wait & Notify (Custom Buffer)

This implementation uses low-level Java primitives to coordinate the producer and consumer. A single-slot buffer ensures controlled, sequential access.

| Command | Description |
| :--- | :--- |
| `mvn exec:java -Dexec.mainClass="org.example.MainWaitNotify"` | Run this implementation. |

**Output (Approach 1)**

| Console Logs (1) | Console Logs (2) |
| :--- | :--- |
| ![Approach 1 — Output 1](Assignment_1/Producer_consumer_Approach1_Wait_and_Notify/Screenshot%202025-11-22%20134714.png) | ![Approach 1 — Output 2](Assignment_1/Producer_consumer_Approach1_Wait_and_Notify/Screenshot%202025-11-22%20134735.png) |

### ➤ Approach 2 — BlockingQueue (Modern Concurrency)

This version uses Java’s built-in `LinkedBlockingQueue<OrderEntity>` to handle all blocking semantics automatically, making the code **cleaner, safer, and scalable**—ideal for production-grade systems.

| Command | Description |
| :--- | :--- |
| `mvn exec:java -Dexec.mainClass="org.example.MainBlockingQueue"` | Run this modern implementation. |

**Output (Approach 2)**

| Console Logs (1) | Console Logs (2) |
| :--- | :--- |
| ![Approach 2 — Output 1](https://raw.githubusercontent.com/vibzvaibhavi/Intuit_Build_Challenge/main/Assignment_1/Producer_consumer_Approach2_Blocking_Queue/Screenshot%202025-11-22%20141427.png) | ![Approach 2 — Output 2](https://raw.githubusercontent.com/vibzvaibhavi/Intuit_Build_Challenge/main/Assignment_1/Producer_consumer_Approach2_Blocking_Queue/Screenshot%202025-11-22%20141441.png) |

---

##  Assignment 2 – Sales Analytics 

### Overview
This assignment reads sales data from a **CSV** file and performs multiple analytical operations using **Java Streams**, **functional programming**, and immutable POJO design.

###  Features & Highlights
1.  **Strongly Typed Immutable POJO**
    * The `SalesRecord` POJO is designed for immutability and contains fields like Order ID, Customer, Region, Product, Quantity, Unit Price, and Date.
2.  **Advanced SalesAnalyzer Using Streams**
    * All analysis operations are executed efficiently using the Java Streams API methods:
        * `map()` and `filter()`
        * `collect()` with **`groupingBy()`**
        * `max()`, `min()`, and `sorted()`
3.  **Robust CSV Parsing**
    * The parser is designed to handle common data quality issues: **missing values**, bad formatting, and incorrect numeric fields.
4.  **Comprehensive Analysis Operations**
    * Total Sales, Region-based Grouping, Product Revenue Aggregation, Min/Max Sales, Top 3 Orders, Most Frequent Category, and Month-based Filtering.
5.  **Full JUnit Test Coverage**
    * Tests validate all complex **aggregations**, functional pipelines, boundary cases, and handling of empty datasets.

| Command | Description |
| :--- | :--- |
| `mvn exec:java -Dexec.mainClass="org.example.Main"` | Run the Sales Analytics program. |

**Output (Assignment 2)**

| Sales Analytics Console Output | JUnit Test Results |
| :--- | :--- |
| ![Sales Analytics Output](https://raw.githubusercontent.com/vibzvaibhavi/Intuit_Build_Challenge/main/Assignment_2/Sales%20Stream%20Reader/Sales_Data_Aggregation/Screenshot%202025-11-22%20142010.png) | ![Sales Analytics Tests](https://raw.githubusercontent.com/vibzvaibhavi/Intuit_Build_Challenge/main/Assignment_2/Sales%20Stream%20Reader/Sales_Data_Aggregation/Screenshot%202025-11-22%20142020.png) |

---

##  Complete Tech Stack

* **Language:** Java 17
* **Build Tool:** Maven
* **Concurrency:** Java Concurrency, `wait`/`notify`, `BlockingQueue`
* **Data Processing:** Java Streams API
* **Persistence:** SQLite (JDBC)
* **Serialization:** JSON Parsing, CSV Parsing
* **Testing:** JUnit 5

---

##  Author

**Vaibhavi Rachamalla**
* **GitHub:** https://github.com/vibzvaibhavi
