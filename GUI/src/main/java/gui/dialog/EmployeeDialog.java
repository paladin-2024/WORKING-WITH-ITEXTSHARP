package gui.dialog;

import model.Employee;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeDialog extends JDialog {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField departmentField;
    private JTextField salaryField;
    private JTextField joinDateField;
    private boolean confirmed = false;
    private Employee employee;

    public EmployeeDialog(JFrame parent) {
        this(parent, new Employee());
    }

    public EmployeeDialog(JFrame parent, Employee employee) {
        super(parent, employee.getId() == 0 ? "Add Employee" : "Edit Employee", true);
        this.employee = employee;
        initComponents();
        setupLayout();
    }

    private void initComponents() {
        nameField = new JTextField(employee.getName(), 20);
        emailField = new JTextField(employee.getEmail(), 20);
        departmentField = new JTextField(employee.getDepartment(), 20);
        salaryField = new JTextField(employee.getSalary() == 0 ? "" : String.valueOf(employee.getSalary()), 20);
        joinDateField = new JTextField(employee.getJoinDate() == null ?
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()) :
                new SimpleDateFormat("yyyy-MM-dd").format(employee.getJoinDate()), 20);
    }

    private void setupLayout() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Department:"));
        panel.add(departmentField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);
        panel.add(new JLabel("Join Date (YYYY-MM-DD):"));
        panel.add(joinDateField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            if (validateInput()) {
                confirmed = true;
                setVisible(false);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getParent());
    }

    private boolean validateInput() {
        try {
            employee.setName(nameField.getText());
            employee.setEmail(emailField.getText());
            employee.setDepartment(departmentField.getText());
            employee.setSalary(Double.parseDouble(salaryField.getText()));
            employee.setJoinDate(java.sql.Date.valueOf(joinDateField.getText()));
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number for salary",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter date in YYYY-MM-DD format",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean showDialog() {
        setVisible(true);
        return confirmed;
    }

    public Employee getEmployee() {
        return employee;
    }
}