package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTable employeeTable;

    public MainFrame() {
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Initialize the JTable
        employeeTable = new JTable();

        // Create panels for Load and Export functionalities
        LoadDataPanel loadDataPanel = new LoadDataPanel(employeeTable);
        ExportToPDFPanel exportToPDFPanel = new ExportToPDFPanel(employeeTable);

        // Add components to the main frame
        setLayout(new BorderLayout());
        add(new JScrollPane(employeeTable), BorderLayout.CENTER); // Table in the center
        add(loadDataPanel, BorderLayout.NORTH); // Load Data Panel at the top
        add(exportToPDFPanel, BorderLayout.SOUTH); // Export to PDF Panel at the bottom
    }

}
