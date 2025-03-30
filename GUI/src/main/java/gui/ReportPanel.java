package gui;


import database.EmployeeDAO;
import model.Employee;
import util.PDFExporter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class ReportPanel {
    private JPanel panel;
    private JTable employeeTable;
    private JButton loadDataBtn;
    private JButton exportPdfBtn;

    public ReportPanel() {
        initComponents();
        setupLayout();
        setupListeners();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initComponents() {
        panel = new JPanel();
        employeeTable = new JTable();
        loadDataBtn = new JButton("Load Data");
        exportPdfBtn = new JButton("Export to PDF");
    }

    private void setupLayout() {
        panel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(loadDataBtn);
        buttonPanel.add(exportPdfBtn);

        panel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        loadDataBtn.addActionListener(e -> {
            try {
                loadData();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        exportPdfBtn.addActionListener(e -> exportToPDF());
    }

    private void loadData() throws SQLException {
        EmployeeDAO dao = new EmployeeDAO();
        List<Employee> employees = dao.getAllEmployees();

        EmployeeTableModel model = new EmployeeTableModel();
        employeeTable.setModel(model);
    }

    private void exportToPDF() {
        if (employeeTable.getModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(panel,
                    "No data to export. Please load data first.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            PDFExporter.exportTableToPDF(employeeTable, "Employee Report");
            JOptionPane.showMessageDialog(panel,
                    "PDF exported successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel,
                    "Error exporting PDF: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}