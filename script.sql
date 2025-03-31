CREATE DATABASE IF NOT EXISTS employee_management;
USE employee_management;

CREATE TABLE departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    budget DECIMAL(15,2)
);

CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    department VARCHAR(100),
    salary DECIMAL(10,2),
    join_date DATE
);

INSERT INTO departments (name, location, budget) VALUES
('Administration', 'Main Building', 5000000.00),
('IT', 'Technical Building', 3000000.00),
('Finance', 'East Wing Building', 4500000.00),
('Human Resources', 'West Wing Building', 3500000.00),
('Marketing', 'Commercial Building', 2500000.00);

INSERT INTO employees (name, email, department, salary, join_date) VALUES
('Caleb Nzabb', 'cnzabb@paladin.cd', 'IT', 250000.00, '2024-12-15'),
('Ikuzo', 'ikuzo@paladin.cd', 'Administration', 250000.00, '2025-04-01');

CREATE TABLE reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    report_type VARCHAR(50) NOT NULL,
    generated_date DATE NOT NULL,
    employee_count INT NOT NULL,
    total_salary DECIMAL(15,2) NOT NULL
);

