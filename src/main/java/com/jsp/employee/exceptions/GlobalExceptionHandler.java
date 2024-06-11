package com.jsp.employee.exceptions;

import com.jsp.employee.CustomResponseEntity.CustomResponseEntity;
import com.jsp.employee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmployeeIdAlreadyExistsException.class)
    public ResponseEntity<CustomResponseEntity<Employee>> employeeIdAlreadyExistsException(Exception e){
        CustomResponseEntity<Employee> responseEntity = new CustomResponseEntity<>();
        responseEntity.setStatus(HttpStatus.CONFLICT);
        responseEntity.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseEntity);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<CustomResponseEntity<Employee>> employeeNotFoundException(Exception e){
        CustomResponseEntity<Employee> responseEntity = new CustomResponseEntity<>();
        responseEntity.setStatus(HttpStatus.NOT_FOUND);
        responseEntity.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseEntity);
    }

}