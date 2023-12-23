/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cctmanagementsystem.models;

import java.sql.*;
import java.util.Scanner;

import cctmanagementsystem.config.Database;

/**
 *
 * @author Asad
 */
public class User {
     // EStablishing a connection to the database 
    private final static Connection connection = Database.getInstance().getConnection();
    private static User instance;
    //Getting input from user
    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        
        return value;
    }
    // function to create a self user account
    public void createSelfUserAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*********** Create User Account ***********");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        username = "ooc2023_" + username;
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        if (checkUsernameExists(connection, username)) {
            System.out.println("Username already exists. Please choose a different username.");
            
            return;
        }
        // Insert the newly created user into the database
        String insertQuery = "INSERT INTO users (username, password, name, surname, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, surname);
            preparedStatement.setString(5, email);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User account created successfully.");
            } else {
                System.out.println("Failed to create user account.");
            }
            
        } catch (SQLException e) {
            System.out.println("ERROR : " + e.getMessage());
        }

    }
     // function to login into for the user in the database 
    public boolean loginAsUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if there is a match
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR => loginAsUser : " + e.getMessage());
            return false;
        }
    }
 private void modifyUserProfile(String username) {
        Scanner scanner = new Scanner(System.in);
        // get the information of the user who logged in
        String selectQuery = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("*********** User Profile ***********");
                System.out.printf("%-15s %-15s %-15s %-15s %-15s%n",
                        "Username", "Password", "Name", "Surname", "Email");
                System.out.println("--------------------------------------");
                String currentUsername = resultSet.getString("username");
                String currentPassword = resultSet.getString("password");
                String currentName = resultSet.getString("name");
                String currentSurname = resultSet.getString("surname");
                String currentEmail = resultSet.getString("email");
                System.out.printf("%-15s %-15s %-15s %-15s %-15s%n", currentUsername, currentPassword, currentName,
                        currentSurname, currentEmail);
                // edit user profile
                boolean exitModify = false;
                do {
                    System.out.println("\nSelect option to modify:");
                    System.out.println("1) Username");
                    System.out.println("2) Password");
                    System.out.println("3) Name");
                    System.out.println("4) Surname");
                    System.out.println("5) Email");
                    System.out.println("6) Exit");
                    System.out.print("Enter your choice: ");
                    int modifyOption = scanner.nextInt();
                    switch (modifyOption) {
                        case 1:
                            System.out.print("Enter new username: ");
                            String newUsername = scanner.next();
                            updateUserField(connection, username, "username", newUsername);
                            
                            break;
                        case 2:
                            System.out.print("Enter new password: ");
                            String newPassword = scanner.next();
                            updateUserField(connection, username, "password", newPassword);
                            
                            break;
                        case 3:
                            System.out.print("Enter new name: ");
                            String newName = scanner.next();
                            updateUserField(connection, username, "name", newName);
                            
                            break;
                        case 4:
                            System.out.print("Enter new surname: ");
                            String newSurname = scanner.next();
                            updateUserField(connection, username, "surname", newSurname);
                            
                            break;
                        case 5:
                            System.out.print("Enter new email: ");
                            String newEmail = scanner.next();
                            updateUserField(connection, username, "email", newEmail);
                            
                            break;
                        case 6:
                            exitModify = true;
                            System.out.println("Exiting profile modification.");
                            
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                } while (!exitModify);
            } else {
                System.out.println("Admin not found.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR : " + e.getMessage());
        }

    }
 // function to display the information of user
    private static void viewProfile(String username) {
        String selectQuery = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("*********** User Profile ***********");
                System.out.printf("%-15s %-15s %-15s %-15s %-15s%n",
                        "Username", "Password", "Name", "Surname", "Email");
                System.out.println("--------------------------------------");
                String currentUsername = resultSet.getString("username");
                String currentPassword = resultSet.getString("password");
                String currentName = resultSet.getString("name");
                String currentSurname = resultSet.getString("surname");
                String currentEmail = resultSet.getString("email");

                System.out.printf("%-15s %-15s %-15s %-15s %-15s%n",
                        currentUsername, currentPassword, currentName, currentSurname, currentEmail);
            } else {
                System.out.println("Admin not found.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
     public void displayUserMenu(String username) {
        // Display the user menu
        int userChoice;
        do {
            System.out.println("*********** Welcome, " + username + " ***********");
            System.out.println("1) View Profile");
            System.out.println("2) Modify Profile");
            System.out.println("3) Calculate Tax");
            System.out.println("4) Exit");
            System.out.print("Enter your choice: ");
            // Add logic for admin menu options here
            userChoice = User.getInstance().getUserChoice();
            switch (userChoice) {
                case 1:
                    viewProfile(username); // store the log in the database table
                    logUserOperation(username, "Logged In");
                    break;
                case 2:
                    modifyUserProfile(username); // store the log in the database
                    logUserOperation(username, "Edited profile");
                    break;
                case 3:
                    // calculate and store the tax
                    calculateAndStoreTax(username);
                    logUserOperation(username, "Tax Calculated");
                    break;
                case 4:
                    System.out.println("Exiting User Menu!");
                    System.out.println("------------------------------------------------------");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (userChoice != 4);
    }
     // calculate the income Tax
    private static double calculateIncomeTax(double grossIncome, double taxCredits) {
        double taxRate = 0.20;
        return grossIncome * taxRate - taxCredits;
    }

    // calculate the USC
    private static double calculateUSC(double grossIncome) {
        double uscRate = 0.04;
        return grossIncome * uscRate;
    }

    // calculate the PRSI
    private static double calculatePRSI(double grossIncome) {
        double prsiRate = 0.02;
        return grossIncome * prsiRate;
    }
    // function to calculate Tax and store into the database
    private void calculateAndStoreTax(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*********** Tax Calculation ***********");
        // Get user's gross income and tax credits
        System.out.print("Enter Gross Income: ");
        double grossIncome = scanner.nextDouble();
        System.out.print("Enter Tax Credits: ");
        double taxCredits = scanner.nextDouble();
        // Perform tax calculations (Income tax/PAYE, USC, and PRSI)
        double incomeTax = calculateIncomeTax(grossIncome, taxCredits);
        double usc = calculateUSC(grossIncome);
        double prsi = calculatePRSI(grossIncome);
        // Calculate total tax owed
        double totalTaxOwed = incomeTax + usc + prsi;
        // Display the calculated tax details
        System.out.println("\n*********** Tax Details ***********");
        System.out.println("User name: " + username);
        System.out.println("Gross Income: " + grossIncome);
        System.out.println("Tax Credits: " + taxCredits);
        System.out.println("Income Tax (PAYE): " + incomeTax);
        System.out.println("USC: " + usc);
        System.out.println("PRSI: " + prsi);
        System.out.println("Total Tax Owed: " + totalTaxOwed);
        // Store the tax information in the database
        storeTaxInformation(username, grossIncome, taxCredits, incomeTax, usc, prsi, totalTaxOwed);
        
    }
    // store the clculated tax information into the database
    private static void storeTaxInformation(String username, double grossIncome, double taxCredits, double incomeTax,
            double usc, double prsi, double totalTaxOwed) {
        String insertQuery = "INSERT INTO tax_audit (username, gross_income, tax_credits, income_tax, usc, prsi, total_tax_owed) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setDouble(2, grossIncome);
            preparedStatement.setDouble(3, taxCredits);
            preparedStatement.setDouble(4, incomeTax);
            preparedStatement.setDouble(5, usc);
            preparedStatement.setDouble(6, prsi);
            preparedStatement.setDouble(7, totalTaxOwed);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Tax information stored successfully for auditing and debugging.");
            } else {
                System.out.println("Failed to store tax information.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    // function to check the user from the database
    private static boolean checkUsernameExists(Connection connection, String username) {
        String checkQuery = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkQuery)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if the username exists
        } catch (SQLException e) {
            System.out.println("ERROR : " + e.getMessage());
            return false;
        }
    }
    // function to store the log information into the database
    private void logUserOperation(String username, String operation) {
        String insertQuery = "INSERT INTO user_operations (username, operation, date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, operation);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR => logUserOperation : " + e.getMessage());
        }

    }



    
}
