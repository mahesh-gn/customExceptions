package com.jsp.employee.exceptions;

public class EmployeeIdAlreadyExistsException extends RuntimeException {
    public EmployeeIdAlreadyExistsException(String message) {
        super(message);
    }
}