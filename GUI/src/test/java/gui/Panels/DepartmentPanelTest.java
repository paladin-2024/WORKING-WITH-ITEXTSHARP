package gui.Panels;

import database.DAO.DepartmentDAO;
import gui.TableModel.DepartmentTableModel;
import model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepartmentPanelTest {

    private DepartmentPanel departmentPanel;
    private DepartmentDAO mockDepartmentDAO;
    private DepartmentTableModel mockTableModel;
    private JTable mockTable;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockDepartmentDAO = mock(DepartmentDAO.class);
        mockTableModel = mock(DepartmentTableModel.class);
        mockTable = mock(JTable.class);

        // Create panel with mocked dependencies
        departmentPanel = new DepartmentPanel() {
            @Override
            DepartmentDAO createDepartmentDAO() {
                return mockDepartmentDAO;
            }

            @Override
            DepartmentTableModel createTableModel() {
                return mockTableModel;
            }

            @Override
            JTable createTable(DepartmentTableModel model) {
                return mockTable;
            }
        };
    }

    @Test
    void testLoadDataSuccess() throws SQLException {
        // Setup
        List<Department> testDepartments = Arrays.asList(
                new Department(1, "IT", "Building A", 100000),
                new Department(2, "HR", "Building B", 50000)
        );
        when(mockDepartmentDAO.getAllDepartments()).thenReturn(testDepartments);

        // Execute
        departmentPanel.loadData();

        // Verify
        verify(mockDepartmentDAO).getAllDepartments();
        verify(mockTableModel).setDepartments(testDepartments);
        verify(mockTableModel).fireTableDataChanged();
    }

    @Test
    void testLoadDataFailure() throws SQLException {
        // Setup
        when(mockDepartmentDAO.getAllDepartments()).thenThrow(new SQLException("Connection failed"));

        // Execute
        departmentPanel.loadData();

        // Verify
        verify(mockDepartmentDAO).getAllDepartments();
        verify(mockTableModel, never()).setDepartments(any());
    }

    @Test
    void testAddDepartmentSuccess() throws SQLException {
        // Setup
        Department testDept = new Department(1, "IT", "Building A", 100000);
        when(mockDepartmentDAO.addDepartment(any(Department.class))).thenReturn(true);

        // Execute
        departmentPanel.addDepartment();

        // Verify
        verify(mockDepartmentDAO).addDepartment(any(Department.class));
        verify(mockDepartmentDAO, times(1)).addDepartment(any(Department.class));
    }

    @Test
    void testEditDepartmentNoSelection() {
        // Setup
        when(mockTable.getSelectedRow()).thenReturn(-1);

        // Execute
        departmentPanel.editDepartment();

        // Verify
        verify(mockDepartmentDAO, never()).updateDepartment(any());
    }

    @Test
    void testDeleteDepartmentConfirmationNo() {
        // Setup
        when(mockTable.getSelectedRow()).thenReturn(0);
        when(mockTableModel.getDepartmentAt(0)).thenReturn(new Department(1, "IT", "Building A", 100000));

        // Mock "NO" response to confirmation dialog
        departmentPanel = Mockito.spy(departmentPanel);
        doReturn(JOptionPane.NO_OPTION).when(departmentPanel).showConfirmDialog(anyString(), anyString());

        // Execute
        departmentPanel.deleteDepartment();

        // Verify
        verify(mockDepartmentDAO, never()).deleteDepartment(anyInt());
    }

    @Test
    void testDeleteDepartmentSuccess() throws SQLException {
        // Setup
        Department testDept = new Department(1, "IT", "Building A", 100000);
        when(mockTable.getSelectedRow()).thenReturn(0);
        when(mockTableModel.getDepartmentAt(0)).thenReturn(testDept);
        when(mockDepartmentDAO.deleteDepartment(1)).thenReturn(true);

        // Mock "YES" response to confirmation dialog
        departmentPanel = Mockito.spy(departmentPanel);
        doReturn(JOptionPane.YES_OPTION).when(departmentPanel).showConfirmDialog(anyString(), anyString());

        // Execute
        departmentPanel.deleteDepartment();

        // Verify
        verify(mockDepartmentDAO).deleteDepartment(1);
    }

    // Helper methods for the panel to allow dependency injection
    static class TestableDepartmentPanel extends DepartmentPanel {
        private final DepartmentDAO departmentDAO;
        private final DepartmentTableModel tableModel;
        private final JTable table;

        public TestableDepartmentPanel(DepartmentDAO dao, DepartmentTableModel model, JTable table) {
            this.departmentDAO = dao;
            this.tableModel = model;
            this.table = table;
        }

        @Override
        DepartmentDAO createDepartmentDAO() {
            return departmentDAO;
        }

        @Override
        DepartmentTableModel createTableModel() {
            return tableModel;
        }

        @Override
        JTable createTable(DepartmentTableModel model) {
            return table;
        }
    }
}