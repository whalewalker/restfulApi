package com.example.restful.web.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmployeeDto {

    @NotNull(message = "Firstname must have a value")
    private String firstName;
    @NotNull(message = "LastName must have a value")
    private String lastName;
    @NotNull(message = "Role cannot be null")
    private String role;
}
