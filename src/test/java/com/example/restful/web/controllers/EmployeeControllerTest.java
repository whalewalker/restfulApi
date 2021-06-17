package com.example.restful.web.controllers;

import com.example.restful.data.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:db/insert.sql")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    Employee employee;

    ObjectMapper mapper;
    String jsonObject;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        employee = new Employee();
        employee.setFirstName("Adex");
        employee.setLastName("Whale");
        employee.setRole("SE");

        mapper = new ObjectMapper();
        jsonObject = mapper.writeValueAsString(employee);
    }

    @Test
    void getAllEmployeeTest() throws Exception {
        mockMvc.perform(get("/employees")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void saveNewEmployeeTest() throws Exception {
        mockMvc.perform(post("/employee").contentType("application/json").content(jsonObject)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    void getEmployeeById() throws Exception {
        mockMvc.perform(get("/employee/12")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteEmployeeByIdTest() throws Exception {
        mockMvc.perform(delete("/employee/12")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateEmployeeBYIdTest() throws Exception {
        mockMvc.perform(patch("/employee/12").contentType("application/json").content(jsonObject)).andDo(print()).andExpect(status().isOk());
    }
}