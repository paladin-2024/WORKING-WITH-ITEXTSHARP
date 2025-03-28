package utils;

import javax.swing.*;

public class ExceptionHandler {
    // Centralized method for handling exceptions
    public static void handle(Exception e) {
        // Log the exception (optional)
        System.err.println("An error occurred: " + e.getMessage());
        e.printStackTrace();

        // Display error message to the user
        JOptionPane.showMessageDialog(null,
                "Error: " + e.getMessage(),
                "Application Error",
                JOptionPane.ERROR_MESSAGE);
    }

    // Overloaded method for custom error messages
    public static void handle(String errorMessage) {
        // Log the custom error message (optional)
        System.err.println("An error occurred: " + errorMessage);

        // Display error message to the user
        JOptionPane.showMessageDialog(null,
                "Error: " + errorMessage,
                "Application Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
