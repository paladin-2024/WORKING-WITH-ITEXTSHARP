package gui.TableModel;

import model.Report;
import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportTableModel extends AbstractTableModel {
    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM yyyy");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private List<Report> reports = new ArrayList<>();
    private String[] columns = new String[0];

    public void setReports(List<Report> reports) {
        this.reports = reports;
        columns = !reports.isEmpty() ? determineColumns(reports.get(0)) : new String[0];
        fireTableStructureChanged();
    }

    private String[] determineColumns(Report report) {
        return switch (report.getReportType()) {
            case "Salary Report" -> new String[]{"Department", "Employees", "Total Salary",
                    "Avg Salary", "Min Salary", "Max Salary"};
            case "New Hires Report" -> new String[]{"Name", "Email", "Department",
                    "Join Date", "Salary"};
            case "Department Analysis" -> new String[]{"Department", "Employees",
                    "Total Salary", "Budget",
                    "Utilization %"};
            case "Monthly Report" -> new String[]{"Month", "Department", "Employees",
                    "Total Salary"};
            default -> new String[]{"Data"};
        };
    }

    @Override public int getRowCount() { return reports.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int column) { return columns[column]; }

    @Override
    public Object getValueAt(int row, int column) {
        Report report = reports.get(row);
        return switch (report.getReportType()) {
            case "Salary Report" -> getSalaryValue(report, column);
            case "New Hires Report" -> getNewHireValue(report, column);
            case "Department Analysis" -> getAnalysisValue(report, column);
            case "Monthly Report" -> getMonthlyValue(report, column);
            default -> null;
        };
    }

    private Object getSalaryValue(Report r, int col) {
        return switch (col) {
            case 0 -> r.getDepartment();
            case 1 -> r.getEmployeeCount();
            case 2 -> formatCurrency(r.getTotalSalary());
            case 3 -> formatCurrency(r.getAvgSalary());
            case 4 -> formatCurrency(r.getMinSalary());
            case 5 -> formatCurrency(r.getMaxSalary());
            default -> null;
        };
    }

    private Object getNewHireValue(Report r, int col) {
        return switch (col) {
            case 0 -> r.getEmployeeName();
            case 1 -> r.getEmployeeEmail();
            case 2 -> r.getDepartment();
            case 3 -> r.getGeneratedDate().format(dateFormatter);
            case 4 -> formatCurrency(r.getTotalSalary());
            default -> null;
        };
    }

    private Object getAnalysisValue(Report r, int col) {
        return switch (col) {
            case 0 -> r.getDepartment();
            case 1 -> r.getEmployeeCount();
            case 2 -> formatCurrency(r.getTotalSalary());
            case 3 -> formatCurrency(r.getBudget());
            case 4 -> String.format("%.2f%%", r.getUtilization());
            default -> null;
        };
    }

    private Object getMonthlyValue(Report r, int col) {
        return switch (col) {
            case 0 -> r.getGeneratedDate().format(monthFormatter);
            case 1 -> r.getDepartment();
            case 2 -> r.getEmployeeCount();
            case 3 -> formatCurrency(r.getTotalSalary());
            default -> null;
        };
    }

    private String formatCurrency(double amount) {
        return String.format("$%,.2f", amount);
    }
}