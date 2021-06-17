package com.example.restful.web.controllers;

import com.example.restful.data.model.Employee;
import com.example.restful.services.EmployeeServiceImpl;
import com.example.restful.web.dto.EmployeeDto;
import com.example.restful.web.exceptions.EmployeeDoesNotExistException;
import com.example.restful.web.exceptions.EmployeeNullPointerException;
import com.example.restful.web.exceptions.NullIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController

public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return employeeService.findAll();
    }

    @PostMapping(value = "/employee")
    public Employee save(@Valid @RequestBody EmployeeDto employee) throws EmployeeNullPointerException {
        return employeeService.save(employee);
    }

    @GetMapping("employee/{id}")
    public Employee getEmployeeById(@PathVariable("id") Long id) throws NullIdException, EmployeeDoesNotExistException {
        return employeeService.findById(id);
    }

    @DeleteMapping("employee/{id}")
    public void deleteEmployee(@PathVariable("id") Long id) throws NullIdException {
        employeeService.deleteById(id);
    }

    @PatchMapping("employee/{id}")
    public Employee updateEmployee(@Valid @PathVariable("id") Long id, @RequestBody EmployeeDto employee) throws NullIdException, EmployeeDoesNotExistException {
        return employeeService.updateEmployee(id, employee);
    }

}
