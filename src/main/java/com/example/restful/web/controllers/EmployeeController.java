package com.example.restful.web.controllers;

import com.example.restful.data.model.Employee;
import com.example.restful.services.EmployeeServiceImpl;
import com.example.restful.web.dto.EmployeeDto;
import com.example.restful.web.exceptions.EmployeeDoesNotExistException;
import com.example.restful.web.exceptions.EmployeeNullPointerException;
import com.example.restful.web.exceptions.NullIdException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployee() {
        List<Employee> employees = employeeService.findAll().stream()
                .map(employee -> {
                            try {
                                return employee.add(
                                        linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),
                                        linkTo(methodOn(EmployeeController.class).getAllEmployee()).withRel("employees"));
                            } catch (NullIdException | EmployeeDoesNotExistException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                ).collect(Collectors.toList());


        return ResponseEntity.ok().body(employees);
    }

    @PostMapping(value = "/employee")
    public ResponseEntity<?> save(@Valid @RequestBody EmployeeDto employeeDto) throws EmployeeNullPointerException {
        Employee employee = employeeService.save(employeeDto);
        try {
            employee.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),
                    linkTo(methodOn(EmployeeController.class).getAllEmployee()).withRel("employees"));
        } catch (NullIdException | EmployeeDoesNotExistException e) {
            e.printStackTrace();
        }

        return ResponseEntity.created(employee.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(employee);
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) throws NullIdException, EmployeeDoesNotExistException {

        Employee employee = employeeService.findById(id);

        EntityModel<Employee> entityModel = null;
        try {
            employee.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),
                    linkTo(methodOn(EmployeeController.class).getAllEmployee()).withRel("employees"));
        } catch (NullIdException | EmployeeDoesNotExistException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) throws NullIdException {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("employee/{id}")
    public ResponseEntity<?> updateEmployee(@Valid @PathVariable("id") Long id, @RequestBody EmployeeDto employeeDto) {

        EntityModel<Employee> entityModel = null;
        Employee employee = null;
        try {
            employee = employeeService.updateEmployee(id, employeeDto);

            try {
               employee.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel(),
                        linkTo(methodOn(EmployeeController.class).getAllEmployee()).withRel("employees"));
            } catch (NullIdException | EmployeeDoesNotExistException e) {
                log.info("Error Occurred --> {}", e.getMessage());
                ResponseEntity.badRequest().body(e.getMessage());
            }
        } catch (NullIdException | EmployeeDoesNotExistException e) {
            log.info("Error Occurred --> {}", e.getMessage());
            ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(employee);
    }

}
