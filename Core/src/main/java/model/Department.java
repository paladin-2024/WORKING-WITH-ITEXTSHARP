package model;

public class Department {
    private int id;
    private String name;
    private String location;
    private double budget;

    // Constructors
    public Department() {}

    public Department(String name, String location, double budget) {
        this.name = name;
        this.location = location;
        this.budget = budget;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + ", location=" + location + ", budget=" + budget + "]";
    }
}