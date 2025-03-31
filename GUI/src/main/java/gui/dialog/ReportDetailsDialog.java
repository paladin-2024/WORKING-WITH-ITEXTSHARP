package gui.dialog;

import model.Report;
import javax.swing.*;
import java.awt.*;

public class ReportDetailsDialog extends JDialog {
    public ReportDetailsDialog(Report report) {
        setTitle("Report Details - " + report.getReportType());
        setSize(400, 300);
        setModal(true);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Report ID:"));
        panel.add(new JLabel(String.valueOf(report.getId())));
        panel.add(new JLabel("Report Type:"));
        panel.add(new JLabel(report.getReportType()));
        panel.add(new JLabel("Generated Date:"));
        panel.add(new JLabel(report.getGeneratedDate().toString()));
        panel.add(new JLabel("Employee Count:"));
        panel.add(new JLabel(String.valueOf(report.getEmployeeCount())));
        panel.add(new JLabel("Total Salary:"));
        panel.add(new JLabel(String.format("$%,.2f", report.getTotalSalary())));

        add(panel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}