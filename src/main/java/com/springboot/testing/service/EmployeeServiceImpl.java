package com.springboot.testing.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.testing.exception.ResourceNotFoundException;
import com.springboot.testing.model.Employee;
import com.springboot.testing.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

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

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(long employeeId) {
		employeeRepository.deleteById(employeeId);
	}

}
