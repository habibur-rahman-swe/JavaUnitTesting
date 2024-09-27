package com.springboot.testing.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springboot.testing.model.Employee;

@DataJpaTest
class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@DisplayName("JUnit test for save emplyee operation")
	@Test
	public void givenEmployeeObjec_whenSave_thenReturnSavedEmployee() {
		// given - precondition or setup
		Employee employee = Employee.builder().firstName("A").lastName("B").email("c@gmail.com").build();
		
		// when - action or the behaviour that we are going test
		Employee savedEmployee = employeeRepository.save(employee);
		
		// then - verify the output
		Assertions.assertThat(savedEmployee).isNotNull();
		Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
	}

}
