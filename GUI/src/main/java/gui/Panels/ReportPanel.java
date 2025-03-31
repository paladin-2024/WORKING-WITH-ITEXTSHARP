package gui.Panels;

import database.DAO.ReportDAO;
import gui.TableModel.ReportTableModel;
import model.Report;
import util.PDFExporter;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ReportPanel {
    private final JPanel panel;
    private final ReportTableModel tableModel;
    private final JComboBox<String> reportTypeCombo;
    private final JButton loadReportsBtn;
    private final JButton exportPdfBtn;

    public ReportPanel() {
        panel = new JPanel(new BorderLayout(10, 10));
        tableModel = new ReportTableModel();
        reportTypeCombo = new JComboBox<>(new String[]{
                "Salary Report", "New Hires Report",
                "Department Analysis", "Monthly Report"
        });
        loadReportsBtn = createButton("Load Report", this::loadReports);
        exportPdfBtn = createButton("Export PDF", this::exportReport);
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        controlPanel.add(new JLabel("Select Report Type:"));
        controlPanel.add(reportTypeCombo);
        controlPanel.add(loadReportsBtn);
        controlPanel.add(exportPdfBtn);

        JTable reportTable = new JTable(tableModel);
        reportTable.setAutoCreateRowSorter(true);
        reportTable.setFillsViewportHeight(true);

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportTable), BorderLayout.CENTER);
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void loadReports() {
        String reportType = (String) reportTypeCombo.getSelectedItem();
        try {
            List<Report> reports = switch (reportType) {
                case "Salary Report" -> new ReportDAO().generateSalaryReport();
                case "New Hires Report" -> new ReportDAO().generateNewHiresReport();
                case "Department Analysis" -> new ReportDAO().generateDepartmentAnalysis();
                case "Monthly Report" -> new ReportDAO().generateMonthlyReport();
                default -> List.of();
            };
            tableModel.setReports(reports);
            if (reports.isEmpty()) {
                showMessage("No data found for selected report", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            showMessage("Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportReport() {
        if (tableModel.getRowCount() == 0) {
            showMessage("No data to export", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String title = (String) reportTypeCombo.getSelectedItem();
        PDFExporter.exportTableToPDF(new JTable(tableModel), title);
    }

    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(panel, message, title, type);
    }

    public JPanel getPanel() {
        return panel;
    }
}