package com.ibm.sf.employeeservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.sf.employeeservice.exception.EmployeeAlreadyExistsException;
import com.ibm.sf.employeeservice.exception.EmployeeNotFoundException;
import com.ibm.sf.employeeservice.model.Employee;
import com.ibm.sf.employeeservice.repository.EmployeeServiceRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeServiceRepository empRepository;
	
	public Employee createEmployee(Employee employee) throws EmployeeAlreadyExistsException{
		
		Optional<Employee> optional = empRepository.findById(employee.getId());
		if (optional.isPresent()) {
			throw new EmployeeAlreadyExistsException();
		}else {
		return empRepository.save(employee);
		}
	}
	
	public Employee updateEmployee(String id,Employee employee) throws EmployeeAlreadyExistsException{
		
		Employee emp = null;
		Optional<Employee> optional = empRepository.findById(id);
		if(optional.isPresent()) {
			emp = optional.get();
			emp.setId(employee.getId());
			emp.setFirstname(employee.getFirstname());
			emp.setLastname(employee.getLastname());
			emp.setEmail(employee.getEmail());
			empRepository.save(emp);
		}
		return emp;
		}

	public Employee getEmployee(String id) throws EmployeeNotFoundException {
		Employee emp = null;
		Optional<Employee> optional = empRepository.findById(id);
		if(optional.isPresent()) {
			emp = optional.get();
		}
		else
			throw new EmployeeNotFoundException();
			return emp;
	}

	public boolean deleteEmployee(String id) throws EmployeeNotFoundException {
		boolean status = false;
		Optional<Employee> optional = empRepository.findById(id);
		if(optional.isPresent()) {
			empRepository.delete(optional.get());
			status = true;
		}
		else
			throw new EmployeeNotFoundException();
		return status;
	}
}
