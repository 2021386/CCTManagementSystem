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
     // function to modify the admin profile
    private static void modifyAdminProfile() {
        Scanner scanner = new Scanner(System.in);
        // Select the admin information from the admin table 
        String selectQuery = "SELECT * FROM admin WHERE username = 'CCT'";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectQuery);
            if (resultSet.next()) {
                // display the information of admin
                System.out.println("*********** Admin Profile ***********");
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
                // Loop for editing profile
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
                            updateAdminField(connection, "username", newUsername);
                            
                            break;
                        case 2:
                            System.out.print("Enter new password: ");
                            String newPassword = scanner.next();
                            updateAdminField(connection, "password", newPassword);
                            
                            break;
                        case 3:
                            System.out.print("Enter new name: ");
                            String newName = scanner.next();
                            updateAdminField(connection, "name", newName);
                            
                            break;
                        case 4:
                            System.out.print("Enter new surname: ");
                            String newSurname = scanner.next();
                            updateAdminField(connection, "surname", newSurname);
                            
                            break;
                        case 5:
                            System.out.print("Enter new email: ");
                            String newEmail = scanner.next();
                            updateAdminField(connection, "email", newEmail);
                            
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

    
}
