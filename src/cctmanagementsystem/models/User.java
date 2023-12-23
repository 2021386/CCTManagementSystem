/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cctmanagementsystem.models;

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

    
}
