package gui.TableModel;

import model.Employee;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {
    protected final List<Employee> employees = new ArrayList<>();
    private final String[] columnNames = {"ID", "Name", "Email", "Department", "Salary", "Join Date"};

    public void setEmployees(List<Employee> employees) {
        this.employees.clear();
        this.employees.addAll(employees);
        fireTableDataChanged(); // Notify listeners after data changes
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee emp = employees.get(rowIndex);
        switch (columnIndex) {
            case 0: return emp.getId();
            case 1: return emp.getName();
            case 2: return emp.getEmail();
            case 3: return emp.getDepartment();
            case 4: return emp.getSalary();
            case 5: return emp.getJoinDate();
            default: return null;
        }
    }

    public Employee getEmployeeAt(int row) {
        return employees.get(row);
    }

    // Additional utility methods
    public void addEmployee(Employee employee) {
        employees.add(employee);
        fireTableRowsInserted(employees.size() - 1, employees.size() - 1);
    }

    public void updateEmployee(int row, Employee employee) {
        employees.set(row, employee);
        fireTableRowsUpdated(row, row);
    }

    public void removeEmployee(int row) {
        employees.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void clear() {
        int size = employees.size();
        employees.clear();
        fireTableRowsDeleted(0, Math.max(0, size - 1));
    }
}