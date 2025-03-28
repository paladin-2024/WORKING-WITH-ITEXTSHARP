package utils;

import models.Employee;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelUtil extends AbstractTableModel {
    private final List<Employee> employees;
    private final String[] columnNames = {"ID", "Name", "Position", "Salary", "Hire Date"};

    public TableModelUtil(List<Employee> employees) {
        this.employees = employees;
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        switch (columnIndex) {
            case 0: return employee.getId();
            case 1: return employee.getName();
            case 2: return employee.getPosition();
            case 3: return employee.getSalary();
            case 4: return employee.getHireDate();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
