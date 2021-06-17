package com.example.restful.services;

import com.example.restful.data.model.Employee;
import com.example.restful.web.dto.EmployeeDto;
import com.example.restful.web.exceptions.EmployeeDoesNotExistException;
import com.example.restful.web.exceptions.EmployeeNullPointerException;
import com.example.restful.web.exceptions.NullIdException;

import java.util.List;

public interface EmployeeService {
    Employee save(EmployeeDto employee) throws EmployeeNullPointerException;
    Employee findById(Long id) throws NullIdException, EmployeeDoesNotExistException;
    List<Employee> findAll();
    void deleteById(Long id) throws NullIdException;
    Employee updateEmployee(Long id, EmployeeDto updatedEmployee) throws NullIdException, EmployeeDoesNotExistException;
}
