package com.springboot.testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.testing.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
