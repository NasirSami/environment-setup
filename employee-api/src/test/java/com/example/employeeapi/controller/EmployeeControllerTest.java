package com.example.employeeapi.controller;

import com.example.employeeapi.dto.EmployeeRequest;
import com.example.employeeapi.dto.EmployeeResponse;
import com.example.employeeapi.exception.GlobalExceptionHandler;
import com.example.employeeapi.exception.EmployeeNotFoundException;
import com.example.employeeapi.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
@Import(GlobalExceptionHandler.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    @DisplayName("POST /api/employees creates a new employee")
    void createEmployee_returnsCreated() throws Exception {
        EmployeeRequest request = new EmployeeRequest("Ada", "Lovelace", "ada@example.com", "Engineer", "Engineering");
        EmployeeResponse response = new EmployeeResponse(1L, "Ada", "Lovelace", "ada@example.com", "Engineer", "Engineering");

        when(employeeService.createEmployee(any(EmployeeRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Ada")));
    }

    @Test
    @DisplayName("GET /api/employees/{id} returns employee")
    void getEmployeeById_returnsEmployee() throws Exception {
        EmployeeResponse response = new EmployeeResponse(2L, "Grace", "Hopper", "grace@example.com", "Engineer", "Engineering");
        when(employeeService.getEmployeeById(2L)).thenReturn(response);

        mockMvc.perform(get("/api/employees/{id}", 2L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is("grace@example.com")));
    }

    @Test
    @DisplayName("GET /api/employees/{id} returns 404 when missing")
    void getEmployeeById_notFound() throws Exception {
        when(employeeService.getEmployeeById(99L)).thenThrow(new EmployeeNotFoundException(99L));

        mockMvc.perform(get("/api/employees/{id}", 99L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(404)));
    }

    @Test
    @DisplayName("GET /api/employees returns list of employees")
    void getAllEmployees_returnsList() throws Exception {
        List<EmployeeResponse> responses = List.of(
                new EmployeeResponse(1L, "Ada", "Lovelace", "ada@example.com", "Engineer", "Engineering"),
                new EmployeeResponse(2L, "Grace", "Hopper", "grace@example.com", "Engineer", "Engineering")
        );
        when(employeeService.getAllEmployees()).thenReturn(responses);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Ada")));
    }

    @Test
    @DisplayName("PUT /api/employees/{id} updates an employee")
    void updateEmployee_returnsUpdatedEmployee() throws Exception {
        EmployeeRequest request = new EmployeeRequest("Ada", "Byron", "ada@example.com", "Lead", "Engineering");
        EmployeeResponse response = new EmployeeResponse(1L, "Ada", "Byron", "ada@example.com", "Lead", "Engineering");
        when(employeeService.updateEmployee(eq(1L), any(EmployeeRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/employees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Byron")))
                .andExpect(jsonPath("$.role", is("Lead")));
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} removes an employee")
    void deleteEmployee_removesEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(3L);

        mockMvc.perform(delete("/api/employees/{id}", 3L))
                .andExpect(status().isNoContent());
    }
}
