# Student Result Management System (Mini Project)

This is a console-based Java application that demonstrates basic CRUD (Create, Read, Update, Delete) operations by integrating Java with a **MySQL database** via **JDBC**.

## Project Structure

  * Student.java: Defines the entity class for student records.
  * Result.java: Defines the entity class for student results.
  * DatabaseManager.java: Handles all database connection and JDBC operations (CRUD).
  * StudentResultManagementSystem.java: Contains the main application logic and the menu-driven console interface.

-----

## Prerequisites

1.  **Java Development Kit (JDK):** Version 8 or higher.
2.  **MySQL Database:** An instance of MySQL server running.
3.  **MySQL JDBC Driver (Connector/J):** You must download the .jar file for the MySQL JDBC driver and add it to your project's classpath.

-----

## Database Setup

You need to create the database and the required tables. The DatabaseManager.java file assumes the following structure and credentials:

### 1. Database and User Credentials

The DatabaseManager.java uses these default values. **You must update these in DatabaseManager.java if your credentials are different.**

| Property | Value (Default) |
| :--- | :--- |
| **URL** | jdbc:mysql://localhost:3306/student_db |
| **Username** | oot |
| **Password** | password |

### 2. SQL Schema

Execute the following SQL commands in your MySQL console or client to set up the necessary database and table:

`sql
-- 1. Create the database
CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

-- 2. Create the main Students table
CREATE TABLE IF NOT EXISTS Students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT
);

-- 3. Create the Results table (Linked by student_id)
CREATE TABLE IF NOT EXISTS Results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    subject VARCHAR(50) NOT NULL,
    marks DOUBLE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES Students(student_id)
);
`

-----

## How to Compile and Run

1.  **Save Files:** Save all .java files into a single directory.
2.  **Add JDBC Driver:** Ensure the MySQL Connector/J JAR is accessible.
3.  **Compile:**
    `ash
    javac -cp [path/to/mysql-connector-java.jar]:. *.java
    `
    *(Note: Use ; instead of : on Windows for the classpath separator)*
4.  **Run:**
    `ash
    java -cp [path/to/mysql-connector-java.jar]:. StudentResultManagementSystem
    `
    The application will launch the console menu.
