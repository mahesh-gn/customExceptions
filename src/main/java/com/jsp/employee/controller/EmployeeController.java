package com.jsp.employee.controller;

import com.jsp.employee.CustomResponseEntity.CustomResponseEntity;
import com.jsp.employee.dto.EmployeeDto;
import com.jsp.employee.model.Employee;
import com.jsp.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<CustomResponseEntity<List<EmployeeDto>>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseEntity<EmployeeDto>> getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public ResponseEntity<CustomResponseEntity<EmployeeDto>> createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponseEntity<EmployeeDto>> updateEmployee(@PathVariable int id, @RequestBody Employee employeeDetails) {
        return employeeService.updateEmployee(id, employeeDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponseEntity<Void>> deleteEmployee(@PathVariable int id) {
        return employeeService.deleteEmployee(id);
    }
}