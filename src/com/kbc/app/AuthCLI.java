package com.kbc.app;

import com.kbc.model.User;
import com.kbc.util.Database;
import java.util.Scanner;

public class AuthCLI {
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        displayWelcomeMessage();
        handleAuthentication();
    }

    private void displayWelcomeMessage() {
        System.out.println("==========================================");
        System.out.println("    Welcome to the Quiz Application!     ");
        System.out.println("==========================================");
        System.out.println("Please choose an option:");
        System.out.println("1. Sign In");
        System.out.println("2. Register");
        System.out.println("==========================================");
    }

    private User user;

    private User handleAuthentication() {
        while (true) {
            System.out.print("Enter your choice (1 or 2): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    User u = handleSignIn();
                    if(u!=null){
                        this.user = u;
                        // break;
                        return u;
                    }
                }
                case "2" -> {
                    User u = handleRegistration();
                    if(u!=null){
                        this.user=u;
                        // break;
                        return u;
                    }
                }
                default -> System.out.println("Invalid choice! Please enter 1 or 2.");
            }
        }
    }

    private User handleSignIn() {
        System.out.println("\n=== Sign In ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        
        if (!Database.userExists(username)) {
            System.out.println("User does not exist! Please register first.");
            return null;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        User u = Database.authenticateUser(username, password);
        if (u == null) {
            System.out.println("Invalid password! Please try again.");
        }
        return u;
    }

    private User handleRegistration() {
        System.out.println("\n=== Registration ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        if (Database.userExists(username)) {
            System.out.println("Username already exists! Please choose a different username.");
            return null;
        }

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (!Database.registerUser(username, password, email)) {
            System.out.println("Registration failed! Please try again.");
            return null;
        } 

        System.out.println("\nRegistration successful! Please sign in to continue.");
        User u = handleSignIn();
        return u;
    }

    public User getUser(){
        return user;
    }
}