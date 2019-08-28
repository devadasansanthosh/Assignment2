package com.ibm.sf.employeeservice.controller;

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

import com.ibm.sf.employeeservice.exception.DepartmentAlreadyExistsException;
import com.ibm.sf.employeeservice.exception.DepartmentNotFoundException;
import com.ibm.sf.employeeservice.model.Department;
import com.ibm.sf.employeeservice.service.DepartmentService;


@RestController
@RequestMapping("/department")
public class DepartmentController {
	
	private ResponseEntity<?> responseEntity;
	private DepartmentService departmentService;
	
	public DepartmentController() {
		super();
	}
	
	@Autowired
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService=departmentService;
	}
	
	@PostMapping(value="/create/department", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createDepartment(@RequestBody Department department) throws DepartmentAlreadyExistsException {
			
		try {
			Department dept = departmentService.createDepartment(department);
			responseEntity = new ResponseEntity<Department>(department, HttpStatus.CREATED);
		} catch (DepartmentAlreadyExistsException e) {
			throw new DepartmentAlreadyExistsException();
		}
			return responseEntity;

	}
	
	@PutMapping(value="/update/department/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> updateDepartment(@PathVariable("id") String id,@RequestBody Department department) throws DepartmentNotFoundException {
			
		try {
			Department dept = departmentService.updateDepartment(id,department);
			responseEntity = new ResponseEntity<Department>(department, HttpStatus.OK);
		} catch (DepartmentNotFoundException e) {
			throw new DepartmentNotFoundException();
		}
			return responseEntity;
	}
	
	@GetMapping(value="/getdepartment/{id}",produces = "application/json")
	public ResponseEntity<?> getDepartment(@PathVariable("id") String id) throws DepartmentNotFoundException{
		Department dept = null;
		try {
			dept= departmentService.getDepartment(id);
			responseEntity = new ResponseEntity<>(dept,HttpStatus.OK);
		} catch (DepartmentNotFoundException e) {
			throw new DepartmentNotFoundException();
		}
		return responseEntity;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteDepartment(@PathVariable("id") String id) throws DepartmentNotFoundException{
		boolean deleted=false;
		try {
			departmentService.getDepartment(id);
		} catch (DepartmentNotFoundException e) {
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
		deleted=departmentService.deleteDepartment(id);
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
