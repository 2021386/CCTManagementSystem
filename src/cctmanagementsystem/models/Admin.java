/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cctmanagementsystem.models;

/**
 *
 * @author muham
 */
public class Admin {
    // function for the login of Admin
    public boolean loginAsAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        
        // connect to the database
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }

    }
     // function to display the menu for admin
    public void displayAdminMenu() {
        // Display the admin menu
        int adminChoice;
        do {
            System.out.println("*********** Welcome, Admin! ***********");
            System.out.println("1) View User List");
            System.out.println("2) Modify Admin Profile");
            System.out.println("3) Remove User");
            System.out.println("4) Review User Operations");
            System.out.println("5) Exit");
            System.out.print("Enter your choice: ");
            // switch for the choice of admin
            adminChoice = User.getInstance().getUserChoice();
            switch (adminChoice) {
                case 1:
                    // Display users list
                    displayUserList();
                    break;
                case 2:
                    // Modify Admin
                    modifyAdminProfile();
                    break;
                case 3:
                    // Remove user
                    removeUser();
                    break;
                case 4:
                    viewUserOperations();
                    break;
                case 5:
                    System.out.println("Exiting Admin Menu!");
                    System.out.println("------------------------------------------------------");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (adminChoice != 5);
    }

    
}
