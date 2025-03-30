package gui;

import model.Department;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepartmentDialog {
    private final JDialog dialog;
    private final JTextField nameField;
    private final JTextField locationField;
    private final JTextField budgetField;
    private final Department department;
    private boolean confirmed;

    public DepartmentDialog(JFrame parent) {
        this(parent, new Department());
    }

    public DepartmentDialog(JFrame parent, Department department) {
        this.department = department;
        this.dialog = new JDialog(parent, department.getId() == 0 ? "Add Department" : "Edit Department", true);
        this.nameField = new JTextField(department.getName(), 20);
        this.locationField = new JTextField(department.getLocation(), 20);
        this.budgetField = new JTextField(department.getBudget() == 0 ? "" : String.valueOf(department.getBudget()), 20);
        this.confirmed = false;

        initialize();
    }

    private void initialize() {
        setupComponents();
        setupLayout();
        setupListeners();
    }

    private void setupComponents() {
        // Additional component configuration if needed
        budgetField.setHorizontalAlignment(JTextField.RIGHT);
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        mainPanel.add(new JLabel("Name:"));
        mainPanel.add(nameField);
        mainPanel.add(new JLabel("Location:"));
        mainPanel.add(locationField);
        mainPanel.add(new JLabel("Budget:"));
        mainPanel.add(budgetField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this::handleSave);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.setVisible(false));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(mainPanel, BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getParent());
    }

    private void setupListeners() {
        // Add any additional listeners here
    }

    private void handleSave(ActionEvent e) {
        if (validateInput()) {
            confirmed = true;
            updateDepartmentFromFields();
            dialog.setVisible(false);
        }
    }

    private boolean validateInput() {
        try {
            Double.parseDouble(budgetField.getText());
            return !nameField.getText().trim().isEmpty();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dialog,
                    "Please enter a valid number for budget",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void updateDepartmentFromFields() {
        department.setName(nameField.getText().trim());
        department.setLocation(locationField.getText().trim());
        department.setBudget(Double.parseDouble(budgetField.getText()));
    }

    public boolean showDialog() {
        dialog.setVisible(true);
        return confirmed;
    }

    public Department getDepartment() {
        return department;
    }
}