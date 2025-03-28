package gui;

import utils.PDFGenerator;

import javax.swing.*;
import java.awt.*;

public class ExportToPDFPanel extends JPanel {
    private JTable employeeTable;
    private JButton exportToPDFButton;

    public ExportToPDFPanel(JTable table) {
        this.employeeTable = table;

        // Initialize the "Export to PDF" button
        exportToPDFButton = new JButton("Export to PDF");

        // Add action listener for the button
        exportToPDFButton.addActionListener(e -> exportTableToPDF());

        // Configure layout
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(exportToPDFButton);
    }

    // Method to handle PDF export functionality
    private void exportTableToPDF() {
        if (employeeTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "The table is empty. Please load data before exporting.",
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Call the utility method to generate the PDF
        try {
            PDFGenerator.generatePDF(employeeTable);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "An error occurred while exporting the PDF: " + e.getMessage(),
                    "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
