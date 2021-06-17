package com.example.restful.services;

import com.example.restful.data.model.Employee;
import com.example.restful.data.repository.EmployeeRepository;
import com.example.restful.web.dto.EmployeeDto;
import com.example.restful.web.exceptions.EmployeeDoesNotExistException;
import com.example.restful.web.exceptions.EmployeeNullPointerException;
import com.example.restful.web.exceptions.NullIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Sql(scripts = "classpath:db/insert.sql")
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository mockRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;


    @Autowired
     EmployeeService employeeServiceImpl;

    Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("check if employee is save in db")
    void save() throws EmployeeNullPointerException {
        when(employeeService.save(new EmployeeDto())).thenReturn(employee);
        employeeService.save(new EmployeeDto());

        verify(mockRepository, times(1)).save(new Employee());
    }

    @Test
    @DisplayName("Test if employee details can be fetch with employee id")
    void findById() throws NullIdException, EmployeeDoesNotExistException {
        Long id = 1L;

        when(mockRepository.findById(id)).thenReturn(Optional.of(employee));
        employeeService.findById(id);

        verify(mockRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("check all employee can be fetch from db")
    void findAll() {
        List<Employee> employeeList = new ArrayList<>();

        when(employeeService.findAll()).thenReturn(employeeList);
        employeeService.findAll();

        verify(mockRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("check if employee can be deleted with employee id")
    void deleteById() throws NullIdException {
        Long id = 1L;

        employeeService.deleteById(id);

        verify(mockRepository, times(1)).deleteById(id);
    }

    @Test
    void whenUpdateEmployeeIsCalledThenUpdateNotNullValues() throws NullIdException, EmployeeDoesNotExistException {
        Employee existingEmployee = employeeServiceImpl.findById(12L);
        assertThat(existingEmployee.getFirstName()).isEqualTo("robin");
        assertThat(existingEmployee.getLastName()).isEqualTo("dob");
        assertThat(existingEmployee.getRole()).isEqualTo("HR");

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setRole("Accountant");

        Employee employee = employeeServiceImpl.updateEmployee(12L, employeeDto);
        assertThat(employee.getFirstName()).isEqualTo("robin");
        assertThat(employee.getLastName()).isEqualTo("dob");
        assertThat(employee.getRole()).isEqualTo("Accountant");
    }

}