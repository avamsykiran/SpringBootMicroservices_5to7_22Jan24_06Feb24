package com.cts.empsapi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.empsapi.entities.Employee;
import com.cts.empsapi.exceptions.EmployeeNotFoundException;
import com.cts.empsapi.exceptions.InvalidEmployeeDetailsException;
import com.cts.empsapi.services.EmployeeService;

@RestController
@RequestMapping("/emps")
public class EmployeeApi {

	@Autowired
	private EmployeeService empService;

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmpsAction() {
		return ResponseEntity.ok(empService.getAll());
	}

	@GetMapping("/{id:[1-9][0-9]{0,4}}")
	public ResponseEntity<Employee> getByIdAction(@PathVariable("id") Long empId) throws EmployeeNotFoundException {
		return ResponseEntity.ok(empService.getById(empId));
	}

	@GetMapping("/{mailId:.+@.+}")
	public ResponseEntity<Employee> getByMailAction(@PathVariable("mailId") String mailId)
			throws EmployeeNotFoundException {
		return ResponseEntity.ok(empService.getByMailId(mailId));
	}

	@GetMapping("/{mobile:[1-9][0-9]{9}}")
	public ResponseEntity<Employee> getByMobileAction(@PathVariable("mobile") String mobile)
			throws EmployeeNotFoundException {
		return ResponseEntity.ok(empService.getByMobile(mobile));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delByIdAction(@PathVariable("id") Long empId) throws EmployeeNotFoundException {
		empService.deleteById(empId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping
	public ResponseEntity<Employee> addEmpAction(@RequestBody @Valid Employee emp, BindingResult results)
			throws InvalidEmployeeDetailsException {
		if (results.hasErrors()) {
			throw new InvalidEmployeeDetailsException(results);
		}

		return new ResponseEntity<>(empService.add(emp), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Employee> updateEmpAction(@RequestBody @Valid Employee emp, BindingResult results)
			throws InvalidEmployeeDetailsException, EmployeeNotFoundException{
		if (results.hasErrors()) {
			throw new InvalidEmployeeDetailsException(results);
		}

		return new ResponseEntity<>(empService.update(emp,emp.getEmpId()), HttpStatus.ACCEPTED);
	}

}
