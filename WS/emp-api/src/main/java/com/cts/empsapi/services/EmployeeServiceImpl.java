package com.cts.empsapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.cts.empsapi.entities.Employee;
import com.cts.empsapi.exceptions.EmployeeNotFoundException;
import com.cts.empsapi.repos.EmployeeRepo;

public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepo empRepo;

	@Override
	public List<Employee> getAll() {
		return empRepo.findAll();
	}

	@Override
	public Employee add(Employee emp) {
		return empRepo.save(emp);
	}

	@Override
	public Employee update(Employee emp, Long id) throws EmployeeNotFoundException {
		if(id!=null && emp!=null) {
			
			if(!empRepo.existsById(id)) {
				throw new EmployeeNotFoundException("Can not update a non existing employee");
			}
			
			emp = empRepo.save(emp);
		}
		return emp;
	}

	@Override
	public Employee getById(Long id) throws EmployeeNotFoundException {
		return empRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException("no such employee found"));
	}

	@Override
	public void deleteById(Long id) throws EmployeeNotFoundException {
		if(!empRepo.existsById(id)) {
			throw new EmployeeNotFoundException("Can not delete a non existing employee");
		}
		empRepo.deleteById(id);
	}

	@Override
	public Employee getByMobile(String mobile) throws EmployeeNotFoundException {
		return empRepo.findByMobile(mobile).orElseThrow(() -> new EmployeeNotFoundException("no such employee found"));
	}

	@Override
	public Employee getByMailId(String mailId) throws EmployeeNotFoundException {
		return empRepo.findByMailId(mailId).orElseThrow(() -> new EmployeeNotFoundException("no such employee found"));
	}

}
