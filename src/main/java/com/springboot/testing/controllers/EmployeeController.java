package com.springboot.testing.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.testing.model.Employee;
import com.springboot.testing.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployee();
	}

	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId) {
		return employeeService.getEmployeeById(employeeId).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("{id}")
	public ResponseEntity<Employee> udateEmployee(@PathVariable("id") long employeeId, @RequestBody Employee employee) {

		return employeeService.getEmployeeById(employeeId).map(savedEmployee -> {
			savedEmployee.setFirstName(employee.getFirstName());
			savedEmployee.setLastName(employee.getLastName());
			savedEmployee.setEmail(employee.getEmail());

			Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);

			return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());

	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeId) {

		employeeService.deleteEmployee(employeeId);

		return new ResponseEntity<String>("Employee deleted successfully", HttpStatus.OK);
	}
}
