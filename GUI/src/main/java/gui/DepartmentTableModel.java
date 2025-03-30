package gui;

import model.Department;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DepartmentTableModel extends AbstractTableModel {
    private final List<Department> departments = new ArrayList<>();
    private final String[] columnNames = {"ID", "Name", "Location", "Budget"};

    public void setDepartments(List<Department> departments) {
        this.departments.clear();
        this.departments.addAll(departments);
        fireTableDataChanged(); // Notify table to refresh
    }

    @Override
    public int getRowCount() {
        return departments.size();
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
        Department dept = departments.get(rowIndex);
        switch (columnIndex) {
            case 0: return dept.getId();
            case 1: return dept.getName();
            case 2: return dept.getLocation();
            case 3: return dept.getBudget();
            default: return null;
        }
    }

    public Department getDepartmentAt(int row) {
        return departments.get(row);
    }

    // CRUD Operations with proper notifications
    public void addDepartment(Department department) {
        departments.add(department);
        fireTableRowsInserted(departments.size() - 1, departments.size() - 1);
    }

    public void updateDepartment(int row, Department department) {
        departments.set(row, department);
        fireTableRowsUpdated(row, row);
    }

    public void removeDepartment(int row) {
        departments.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void clearAll() {
        int lastIndex = Math.max(0, departments.size() - 1);
        departments.clear();
        fireTableRowsDeleted(0, lastIndex);
    }

    // For cell editing support
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex > 0; // Only allow editing non-ID columns
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Department dept = departments.get(rowIndex);
        switch (columnIndex) {
            case 1: dept.setName((String)value); break;
            case 2: dept.setLocation((String)value); break;
            case 3: dept.setBudget(Double.parseDouble(value.toString())); break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}