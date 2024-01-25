package com.cts.empsapi.services;

import java.util.List;

import com.cts.empsapi.entities.Employee;
import com.cts.empsapi.exceptions.EmployeeNotFoundException;

public interface EmployeeService {
	List<Employee> getAll();
	Employee add(Employee emp);
	Employee update(Employee emp,Long id) throws EmployeeNotFoundException;
	Employee getById(Long id) throws EmployeeNotFoundException;
	void deleteById(Long id) throws EmployeeNotFoundException;
}
