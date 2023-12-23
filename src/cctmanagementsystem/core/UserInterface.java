/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cctmanagementsystem.core;

import java.util.Scanner;
import cctmanagementsystem.models.*;

/**
 *
 * @author muham
 */
public class UserInterface {
    // Method to draw the main application menu 
    public static void drawApp() {
        int choice;
        do {
            System.out.println("*********** CCT Management System ***********");
            System.out.println("1) Login as Admin");
            System.out.println("2) Login as User");
            System.out.println("3) Create a Self User Account");
            System.out.println("4) Exit");
            System.out.print("Enter your choice: ");
            // Get the user's choice from the console  input 
            choice = User.getInstance().getUserChoice();
            // Switch statement to handle different user choices
            switch (choice) {
                case 1:
                    // Check if admin login is successfull 
                    if (Admin.getInstance().loginAsAdmin()) {
                        // Display admin menu
                        Admin.getInstance().displayAdminMenu();
                    } else {
                        System.out.println("Wrong username or password. Please login again.");
                    }
                    break;
                case 2:
                    // take username and password for user login 
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    // Check if the user login is successfull
                    if (User.getInstance().loginAsUser(username, password)) {
                        // Display user menu
                        User.getInstance().displayUserMenu(username);
                    } else {
                        System.out.println("Wrong username or password. Please login again.");
                    }
                    break;
                case 3:
                    // create a self user account
                    System.out.println("Create a Self User Acoount");
                    User.getInstance().createSelfUserAccount();
                case 4:
                    System.out.println("Exiting CCT Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
            // Continue loop until the user choose to exit 
        } while (choice != 4);
    }

}

    

