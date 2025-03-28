package models;

import java.util.Date;

public class Employee {
    private int id;
    private String name;
    private String position;
    private double salary;
    private Date hireDate;

    public Employee(int id, String name, String position, double salary, Date hireDate) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    public Date getHireDate() {
        return hireDate;
    }
}
