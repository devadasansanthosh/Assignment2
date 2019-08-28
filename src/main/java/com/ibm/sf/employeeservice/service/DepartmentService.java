package com.ibm.sf.employeeservice.service;

import com.ibm.sf.employeeservice.exception.DepartmentAlreadyExistsException;
import com.ibm.sf.employeeservice.exception.DepartmentNotFoundException;
import com.ibm.sf.employeeservice.model.Department;

public interface DepartmentService {
	public Department createDepartment(Department department)throws DepartmentAlreadyExistsException;
	public Department updateDepartment(String id, Department department)throws DepartmentNotFoundException;
	public Department getDepartment(String id) throws DepartmentNotFoundException;
	public boolean deleteDepartment(String id) throws DepartmentNotFoundException;

}
