package com.jsp.employee.CustomResponseEntity;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomResponseEntity <T>{
    private HttpStatus status;
    private T data;
    private String message;
}