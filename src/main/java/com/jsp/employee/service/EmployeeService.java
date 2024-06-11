package com.jsp.employee.service;

import com.jsp.employee.CustomResponseEntity.CustomResponseEntity;
import com.jsp.employee.dto.EmployeeDto;
import com.jsp.employee.exceptions.EmployeeIdAlreadyExistsException;
import com.jsp.employee.exceptions.EmployeeNotFoundException;
import com.jsp.employee.model.Employee;
import com.jsp.employee.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    public ResponseEntity<CustomResponseEntity<List<EmployeeDto>>> getAllEmployees() {
        List<EmployeeDto> list = employeeRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
        return createSuccessResponse(list, HttpStatus.OK, "List of All Employees");
    }

    public ResponseEntity<CustomResponseEntity<EmployeeDto>> getEmployeeById(int id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return createSuccessResponse(convertToDto(employee.get()), HttpStatus.OK, "Employee with ID " + id + " found");
        } else {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
        }
    }

    public ResponseEntity<CustomResponseEntity<EmployeeDto>> createEmployee(Employee employee) {
        if (employeeRepository.existsById(employee.getEmpId())) {
            throw new EmployeeIdAlreadyExistsException("Employee with ID " + employee.getEmpId() + " already exists");
        }
        Employee createdEmployee = employeeRepository.save(employee);
//        rabbitMQProducer.sendMessage("Employee created: " + employee.toString());
        rabbitMQProducer.sendJsonMessage(convertToDto(employee));
        return createSuccessResponse(convertToDto(createdEmployee), HttpStatus.CREATED, "New Employee with ID: " + employee.getEmpId() + " Created Successfully");
    }

    public ResponseEntity<CustomResponseEntity<EmployeeDto>> updateEmployee(int id, Employee employeeDetails) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));

        existingEmployee.setName(employeeDetails.getName());
        existingEmployee.setAge(employeeDetails.getAge());
        existingEmployee.setSalary(employeeDetails.getSalary());
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setPassword(employeeDetails.getPassword());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return createSuccessResponse(convertToDto(updatedEmployee), HttpStatus.OK, "Employee Details Updated Successfully");
    }

    public ResponseEntity<CustomResponseEntity<Void>> deleteEmployee(int id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee with ID " + id + " not found");
        }
        employeeRepository.deleteById(id);
        return createSuccessResponse(null, HttpStatus.NO_CONTENT, "Employee with ID " + id + " Deleted Successfully");
    }

    private EmployeeDto convertToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private <T> ResponseEntity<CustomResponseEntity<T>> createSuccessResponse(T data, HttpStatus status, String message) {
        CustomResponseEntity<T> responseEntity = new CustomResponseEntity<>();
        responseEntity.setStatus(status);
        responseEntity.setData(data);
        responseEntity.setMessage(message);
        return ResponseEntity.status(status).body(responseEntity);
    }
}