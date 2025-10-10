package com.example.employeeapi.repository;

import com.example.employeeapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmailIgnoreCase(String email);
}
