package com.example.employeeapi.service;

import com.example.employeeapi.dto.EmployeeRequest;
import com.example.employeeapi.dto.EmployeeResponse;
import com.example.employeeapi.exception.EmployeeAlreadyExistsException;
import com.example.employeeapi.exception.EmployeeNotFoundException;
import com.example.employeeapi.model.Employee;
import com.example.employeeapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Map<String, String> defaultRoles;
    private final int maxResultCount;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               @Value("#{${employee.api.department-default-roles}}") Map<String, String> defaultRoles,
                               @Value("${employee.api.max-result-count:200}") int maxResultCount) {
        this.employeeRepository = employeeRepository;
        this.defaultRoles = normalizeKeys(defaultRoles);
        this.maxResultCount = maxResultCount;
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        employeeRepository.findByEmailIgnoreCase(request.getEmail())
                .ifPresent(employee -> {
                    throw new EmployeeAlreadyExistsException(request.getEmail());
                });

        Employee employee = new Employee(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                resolveRole(request.getRole(), request.getDepartment()),
                request.getDepartment()
        );

        Employee saved = employeeRepository.save(employee);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC, "lastName", "firstName"))
                .stream()
                .limit(maxResultCount)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return mapToResponse(employee);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setDepartment(request.getDepartment());
        existing.setRole(resolveRole(request.getRole(), request.getDepartment()));

        Employee updated = employeeRepository.save(existing);
        return mapToResponse(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.delete(existing);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getRole(),
                employee.getDepartment()
        );
    }

    private String resolveRole(String requestedRole, String department) {
        if (StringUtils.hasText(requestedRole)) {
            return requestedRole;
        }

        if (!StringUtils.hasText(department)) {
            return defaultRoles.getOrDefault("DEFAULT", "Employee");
        }

        String key = department.toUpperCase(Locale.ROOT);
        return defaultRoles.getOrDefault(key, defaultRoles.getOrDefault("DEFAULT", "Employee"));
    }

    private Map<String, String> normalizeKeys(Map<String, String> source) {
        if (source == null) {
            return Collections.emptyMap();
        }
        Map<String, String> normalized = new HashMap<>();
        source.forEach((key, value) -> {
            if (key != null) {
                normalized.put(key.toUpperCase(Locale.ROOT), value);
            }
        });
        if (!normalized.containsKey("DEFAULT")) {
            normalized.put("DEFAULT", "Employee");
        }
        return normalized;
    }
}
