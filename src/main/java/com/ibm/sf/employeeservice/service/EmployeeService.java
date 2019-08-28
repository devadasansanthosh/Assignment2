package com.ibm.sf.employeeservice.service;

import com.ibm.sf.employeeservice.exception.EmployeeAlreadyExistsException;
import com.ibm.sf.employeeservice.exception.EmployeeNotFoundException;
import com.ibm.sf.employeeservice.model.Employee;

public interface EmployeeService {
	public Employee createEmployee(Employee employee)throws EmployeeAlreadyExistsException;
	public Employee updateEmployee(String id, Employee employee)throws EmployeeAlreadyExistsException;
	public Employee getEmployee(String id) throws EmployeeNotFoundException;
	public boolean deleteEmployee(String id) throws EmployeeNotFoundException;

}
