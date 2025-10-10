package com.example.employeeapi.integration;

import com.example.employeeapi.dto.EmployeeRequest;
import com.example.employeeapi.dto.EmployeeResponse;
import com.example.employeeapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class EmployeeApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private EmployeeRepository employeeRepository;

    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = restTemplateBuilder.rootUri("http://localhost:" + port).build();
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Create employee endpoint stores and returns record")
    void createEmployee_persistsRecord() {
        EmployeeRequest request = new EmployeeRequest("Ada", "Lovelace", "ada.integration@example.com", null, "Engineering");

        ResponseEntity<EmployeeResponse> response = restTemplate.postForEntity("/api/employees", request, EmployeeResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRole()).isEqualTo("Engineer");

        Long id = response.getBody().getId();
        EmployeeResponse fetched = restTemplate.getForObject("/api/employees/{id}", EmployeeResponse.class, id);

        assertThat(fetched).isNotNull();
        assertThat(fetched.getEmail()).isEqualTo("ada.integration@example.com");
    }

    @Test
    @DisplayName("List employees endpoint returns saved employees")
    void getAllEmployees_returnsSavedRecords() {
        createEmployee("Grace", "Hopper", "grace.integration@example.com", "Engineering");
        createEmployee("Katherine", "Johnson", "katherine.integration@example.com", "Engineering");

        ResponseEntity<List<EmployeeResponse>> response = restTemplate.exchange(
                "/api/employees",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);
    }

    private void createEmployee(String firstName, String lastName, String email, String department) {
        EmployeeRequest request = new EmployeeRequest(firstName, lastName, email, null, department);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmployeeRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<EmployeeResponse> response = restTemplate.exchange(
                "/api/employees",
                HttpMethod.POST,
                entity,
                EmployeeResponse.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
