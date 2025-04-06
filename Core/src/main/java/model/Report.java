package model;
import java.time.LocalDate;

public class Report {
    private final int id;
    private final String reportType;
    private final LocalDate generatedDate;
    private final int employeeCount;
    private final double totalSalary;
    private final String department;
    private final double metric1;
    private final double metric2;
    private final double metric3;
    private final String employeeName;
    private final String employeeEmail;

    // Unified constructor
    public Report(int id, String reportType, LocalDate generatedDate,
                  int employeeCount, double totalSalary, String department,
                  Double metric1, Double metric2, Double metric3,
                  String employeeName, String employeeEmail) {
        this.id = id;
        this.reportType = reportType;
        this.generatedDate = generatedDate;
        this.employeeCount = employeeCount;
        this.totalSalary = totalSalary;
        this.department = department;
        this.metric1 = metric1 != null ? metric1 : 0;
        this.metric2 = metric2 != null ? metric2 : 0;
        this.metric3 = metric3 != null ? metric3 : 0;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
    }

    // Factory methods
    public static Report createSalaryReport(int id, LocalDate generatedDate,
                                            int employeeCount, double totalSalary,
                                            String department, double avgSalary,
                                            double minSalary, double maxSalary) {
        return new Report(id, "Salary Report", generatedDate, employeeCount,
                totalSalary, department, avgSalary, minSalary, maxSalary,
                null, null);
    }

    public static Report createNewHireReport(int id, LocalDate generatedDate,
                                             double salary, String department,
                                             String employeeName, String employeeEmail) {
        return new Report(id, "New Hires Report", generatedDate, 1,
                salary, department, null, null, null,
                employeeName, employeeEmail);
    }

    public static Report createDepartmentAnalysis(LocalDate generatedDate,
                                                  int employeeCount, double totalSalary,
                                                  String department, double budget,
                                                  double utilization) {
        return new Report(0, "Department Analysis", generatedDate, employeeCount,
                totalSalary, department, budget, utilization, null,
                null, null);
    }

    public static Report createMonthlyReport(LocalDate generatedDate,
                                             int employeeCount, double totalSalary,
                                             String department) {
        return new Report(0, "Monthly Report", generatedDate, employeeCount,
                totalSalary, department, null, null, null,
                null, null);
    }

    // Getters
    public int getId() { return id; }
    public String getReportType() { return reportType; }
    public LocalDate getGeneratedDate() { return generatedDate; }
    public int getEmployeeCount() { return employeeCount; }
    public double getTotalSalary() { return totalSalary; }
    public String getDepartment() { return department; }
    public double getAvgSalary() { return metric1; }
    public double getMinSalary() { return metric2; }
    public double getMaxSalary() { return metric3; }
    public double getBudget() { return metric1; }
    public double getUtilization() { return metric2; }
    public String getEmployeeName() { return employeeName; }
    public String getEmployeeEmail() { return employeeEmail; }
}