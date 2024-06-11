package com.jsp.employee.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {

    private int empId;
    private String name;
    private int age;
    private String job;
    private double salary;
    private String email;

}