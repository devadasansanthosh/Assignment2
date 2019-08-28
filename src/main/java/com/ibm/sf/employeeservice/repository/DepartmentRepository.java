package com.ibm.sf.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.sf.employeeservice.model.Department;
import com.ibm.sf.employeeservice.model.Employee;

public interface DepartmentRepository extends JpaRepository<Department, String>{

}
