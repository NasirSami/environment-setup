package com.example.employeeapi.service;

import com.example.employeeapi.dto.EmployeeRequest;
import com.example.employeeapi.dto.EmployeeResponse;
import com.example.employeeapi.exception.EmployeeAlreadyExistsException;
import com.example.employeeapi.exception.EmployeeNotFoundException;
import com.example.employeeapi.model.Employee;
import com.example.employeeapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        Map<String, String> defaults = Map.of("DEFAULT", "Employee", "ENGINEERING", "Engineer");
        employeeService = new EmployeeServiceImpl(employeeRepository, defaults, 2);
    }

    @Test
    @DisplayName("createEmployee assigns default role when not provided")
    void createEmployee_usesDefaultRoleWhenMissing() {
        EmployeeRequest request = new EmployeeRequest("Ada", "Lovelace", "ada@example.com", null, "Engineering");

        when(employeeRepository.findByEmailIgnoreCase("ada@example.com")).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee saved = invocation.getArgument(0);
            ReflectionTestUtils.setField(saved, "id", 1L);
            return saved;
        });

        EmployeeResponse response = employeeService.createEmployee(request);

        assertThat(response.getRole()).isEqualTo("Engineer");
        assertThat(response.getId()).isEqualTo(1L);

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(employeeCaptor.capture());
        assertThat(employeeCaptor.getValue().getEmail()).isEqualTo("ada@example.com");
    }

    @Test
    @DisplayName("createEmployee raises error when email already exists")
    void createEmployee_duplicateEmailThrowsException() {
        EmployeeRequest request = new EmployeeRequest("Grace", "Hopper", "grace@example.com", "Engineer", "Engineering");
        when(employeeRepository.findByEmailIgnoreCase("grace@example.com"))
                .thenReturn(Optional.of(new Employee("Grace", "Hopper", "grace@example.com", "Engineer", "Engineering")));

        assertThatThrownBy(() -> employeeService.createEmployee(request))
                .isInstanceOf(EmployeeAlreadyExistsException.class)
                .hasMessageContaining("grace@example.com");
    }

    @Test
    @DisplayName("getAllEmployees limits results based on configuration")
    void getAllEmployees_appliesResultLimit() {
        Employee first = new Employee("Alan", "Turing", "alan@example.com", "Researcher", "Engineering");
        Employee second = new Employee("Katherine", "Johnson", "katherine@example.com", "Analyst", "Engineering");
        Employee third = new Employee("Margaret", "Hamilton", "margaret@example.com", "Lead", "Engineering");
        ReflectionTestUtils.setField(first, "id", 1L);
        ReflectionTestUtils.setField(second, "id", 2L);
        ReflectionTestUtils.setField(third, "id", 3L);

        when(employeeRepository.findAll(any(Sort.class))).thenReturn(List.of(first, second, third));

        List<EmployeeResponse> responses = employeeService.getAllEmployees();

        assertThat(responses).hasSize(2);
    }

    @Test
    @DisplayName("getEmployeeById returns employee when present")
    void getEmployeeById_returnsEmployee() {
        Employee stored = new Employee("Linus", "Torvalds", "linus@example.com", "Architect", "Engineering");
        ReflectionTestUtils.setField(stored, "id", 42L);
        when(employeeRepository.findById(42L)).thenReturn(Optional.of(stored));

        EmployeeResponse response = employeeService.getEmployeeById(42L);

        assertThat(response.getEmail()).isEqualTo("linus@example.com");
    }

    @Test
    @DisplayName("getEmployeeById throws when employee not found")
    void getEmployeeById_missingEmployeeThrows() {
        when(employeeRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(100L))
                .isInstanceOf(EmployeeNotFoundException.class);
    }

    @Test
    @DisplayName("updateEmployee applies new names and default role")
    void updateEmployee_updatesEmployee() {
        Employee stored = new Employee("Linus", "Torvalds", "linus@example.com", "Architect", "Engineering");
        ReflectionTestUtils.setField(stored, "id", 7L);
        when(employeeRepository.findById(7L)).thenReturn(Optional.of(stored));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmployeeRequest request = new EmployeeRequest("Linus", "Torvalds", "linus@example.com", null, "Engineering");

        EmployeeResponse response = employeeService.updateEmployee(7L, request);

        assertThat(response.getRole()).isEqualTo("Engineer");
        assertThat(response.getFirstName()).isEqualTo("Linus");
    }

    @Test
    @DisplayName("deleteEmployee removes employee when present")
    void deleteEmployee_removesEmployee() {
        Employee stored = new Employee("Barbara", "Liskov", "barbara@example.com", "Professor", "Research");
        when(employeeRepository.findById(9L)).thenReturn(Optional.of(stored));
        doNothing().when(employeeRepository).delete(stored);

        employeeService.deleteEmployee(9L);

        verify(employeeRepository).delete(stored);
    }
}
