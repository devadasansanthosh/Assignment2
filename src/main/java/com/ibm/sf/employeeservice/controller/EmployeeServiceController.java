package com.ibm.sf.employeeservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.sf.employeeservice.exception.DepartmentNotFoundException;
import com.ibm.sf.employeeservice.exception.EmployeeAlreadyExistsException;
import com.ibm.sf.employeeservice.exception.EmployeeNotFoundException;
import com.ibm.sf.employeeservice.model.Department;
import com.ibm.sf.employeeservice.model.Employee;
import com.ibm.sf.employeeservice.service.DepartmentService;
import com.ibm.sf.employeeservice.service.EmployeeService;

@RestController
@RequestMapping("/employeeservice")
public class EmployeeServiceController {
	
	private ResponseEntity<?> responseEntity;
	private EmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	public EmployeeServiceController() {
		super();
	}
	
	@Autowired
	public EmployeeServiceController(EmployeeService employeeService) {
		this.employeeService=employeeService;
	}
	
	@PostMapping(value="/create/employee/{deptId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createEmployee(@PathVariable("deptId") String deptId,@RequestBody Employee employee) 
			throws EmployeeAlreadyExistsException,DepartmentNotFoundException {
		Department dept=null;
		try {
			dept = departmentService.getDepartment(deptId);
		}catch(DepartmentNotFoundException e) {
			throw new DepartmentNotFoundException();
		}
		try {
			employee.setDepartment(dept);
			Employee emp = employeeService.createEmployee(employee);
			responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
		} catch (EmployeeAlreadyExistsException e) {
			throw new EmployeeAlreadyExistsException();
		}
			return responseEntity;

	}
	
	@PutMapping(value="/update/employee/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateEmployee(@PathVariable("id") String id,@RequestBody Employee employee) 
			throws EmployeeAlreadyExistsException {
		
		try {
			//employee.setDepartment(dept);
			Employee emp = employeeService.updateEmployee(id,employee);
			responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} catch (EmployeeAlreadyExistsException e) {
			throw new EmployeeAlreadyExistsException();
		}
			return responseEntity;
	}
	
	@GetMapping(value="employee/{id}",produces = "application/json")
	public ResponseEntity<?> getEmployee(@PathVariable("id") String id) throws EmployeeNotFoundException{
		Employee emp = null;
		try {
			emp= employeeService.getEmployee(id);
			responseEntity = new ResponseEntity<>(emp,HttpStatus.OK);
		} catch (EmployeeNotFoundException e) {
			throw new EmployeeNotFoundException();
		}
		return responseEntity;
	}
	
	@DeleteMapping("employee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") String id) throws EmployeeNotFoundException{
		boolean deleted=false;
		try {
			employeeService.getEmployee(id);
		} catch (EmployeeNotFoundException e) {
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
		deleted=employeeService.deleteEmployee(id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(deleted == true) {
			responseEntity = new ResponseEntity<>(HttpStatus.OK);
		}else {
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

}
