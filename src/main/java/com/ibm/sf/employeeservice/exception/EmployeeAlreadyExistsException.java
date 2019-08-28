package com.ibm.sf.employeeservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code= HttpStatus.CONFLICT , reason="Employee already exists.")
public class EmployeeAlreadyExistsException extends Exception{

}
