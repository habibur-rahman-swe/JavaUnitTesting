package com.springboot.testing.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.testing.exception.ResourceNotFoundException;
import com.springboot.testing.model.Employee;
import com.springboot.testing.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Employee save(Employee employee) {
		Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
		
		if (savedEmployee.isPresent()) {
			throw new ResourceNotFoundException("Employee already exist with given email:" + employee.getEmail());
		}
		

		return employeeRepository.save(employee);
	}

}
