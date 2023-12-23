/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cctmanagementsystem.config;

/**
 *
 * @author muham
 */

import java.sql.*;

public class Database {
    // JDBC connection URL for MYSQL database 
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cctmanagementsystem";
    //Database user credentials 
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    // Singleton instance of the Database class 
    private static Database instance;
    // Connection object to manage the database connection 
    private Connection connection;
    // Private constructor to restrict instantiation outside the class 
    private Database(){
        try {
            //Establishing connection to the database using JDBC
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            // Handle my SQL exceptions that may occur during connection initialization
            System.out.println("SQL EXCEPTION DATABASE CONSTRUCTOR : " + e.getMessage());
        };
    }
    // Singleton pattern to ensure only one instance of database exists
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    // Getter method to provide access to the database connection
    public Connection getConnection() {
        return connection;
    }
    
}

