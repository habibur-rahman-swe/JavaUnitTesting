package com.springboot.testing.service;

import java.util.List;
import java.util.Optional;

import com.springboot.testing.model.Employee;

public interface EmployeeService {
	Employee save(Employee employee);

	List<Employee> getAllEmployee();

	Optional<Employee> getEmployeeById(Long id);

	Employee updateEmployee(Employee employee);

	void deleteEmployee(long employeeId);
}
