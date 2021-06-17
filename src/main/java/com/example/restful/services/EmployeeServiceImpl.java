package com.example.restful.services;

import com.example.restful.data.model.Employee;
import com.example.restful.data.repository.EmployeeRepository;
import com.example.restful.web.dto.EmployeeDto;
import com.example.restful.web.exceptions.EmployeeDoesNotExistException;
import com.example.restful.web.exceptions.EmployeeNullPointerException;
import com.example.restful.web.exceptions.NullIdException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Employee save(EmployeeDto employee) throws EmployeeNullPointerException {
        if(employee == null) throw new EmployeeNullPointerException("Employee cannot be null");
        Employee employeeToSave = modelMapper.map(employee, Employee.class);
        return employeeRepository.save(employeeToSave);
    }

    @Override
    public Employee findById(Long id) throws NullIdException, EmployeeDoesNotExistException {
        validateId(id);
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeDoesNotExistException("Employee with this id does not exist"));
    }

    private void validateId(Long id) throws NullIdException {
        if(id == null) throw new NullIdException("Id cannot be null");
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) throws NullIdException {
        validateId(id);
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeDto updatedEmployee) throws NullIdException, EmployeeDoesNotExistException {
        Employee employeeToUpdate = findById(id);
        employeeToUpdate.setFirstName(updatedEmployee.getFirstName());
        employeeToUpdate.setLastName(employeeToUpdate.getLastName());
        employeeToUpdate.setRole(employeeToUpdate.getRole());
        return employeeRepository.save(employeeToUpdate);
    }
}
