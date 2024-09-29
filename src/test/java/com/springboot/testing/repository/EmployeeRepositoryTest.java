package com.springboot.testing.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springboot.testing.model.Employee;

@DataJpaTest
class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	private Employee employee;
	
	@BeforeEach
	public void setUp() {
		employee = Employee.builder().firstName("A").lastName("B").email("c@gmail.com").build();
	}
	
	@DisplayName("JUnit test for save emplyee operation")
	@Test
	public void givenEmployeeObjec_whenSave_thenReturnSavedEmployee() {
		// given - precondition or setup
		

		// when - action or the behaviour that we are going test
		Employee savedEmployee = employeeRepository.save(employee);

		// then - verify the output
		Assertions.assertThat(savedEmployee).isNotNull();
		Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
	}

	@DisplayName("JUnit test for get all employees operation")
	@Test
	public void givenAllEmpyes_whenSaved_thenCount() {
		// given
		Employee employee0 = Employee.builder().firstName("A").lastName("B").email("c@gmail.com").build();
		Employee employee1 = Employee.builder().firstName("A").lastName("B").email("c@gmail.com").build();
		
		employeeRepository.save(employee0);
		employeeRepository.save(employee1);
		
		// when
		List<Employee> employees = employeeRepository.findAll();
		
		// then
		assertThat(employees).isNotNull();
		assertThat(employees.size()).isEqualTo(2);
	}
	
	// JUnit test for get employee by id operation
	@DisplayName("Find by id")
	@Test
	public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
		// given
		
		employeeRepository.save(employee);
		
		// when
		Employee employeeDB = employeeRepository.findById(employee.getId()).get();
		
		// then
		assertThat(employeeDB).isNotNull();
		
	}
	
	@DisplayName("Find by email")
	@Test
	public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
		// given
		employeeRepository.save(employee);
		
		// when
		Employee employeeDB = employeeRepository.findByEmail("c@gmail.com").get();
		System.out.println(employeeRepository.count());
		
		// than
		assertThat(employeeDB).isNotNull();
	}
	
	@DisplayName("Update Employee")
	@Test
	public void givenEmployeeObject_whenUpdateEmail_thenReturnUpdateEmail() {
		// given
		employeeRepository.save(employee);
		
		// when
		Employee employeeDB = employeeRepository.findByEmail("c@gmail.com").get();
		employeeDB.setEmail("a@gmail.com");
		employeeDB.setFirstName("D");
		Employee updateEmployee = employeeRepository.save(employeeDB);
		
		// than
		assertThat(updateEmployee).isNotNull();
		assertThat(updateEmployee.getFirstName()).isEqualTo("D");
	}
	
	@DisplayName("Delete Employee")
	@Test
	public void givenEmployeeObject_whenDelete_thenRemove() {
		// given
		employee = employeeRepository.save(employee);
		
		// when
		employeeRepository.delete(employee);
		Optional<Employee> emOptional = employeeRepository.findById(employee.getId());
		
		// than
		assertThat(emOptional).isEmpty();
	}
	
	@DisplayName("JPQL test Employee")
	@Test
	public void givenEmployeeFirstnameLastName_whenFind_thenGet() {
		// given
		employee = employeeRepository.save(employee);
		
		// when
		Employee employee2 = employeeRepository.findByJPQL("A", "B");
		
		// than
		assertThat(employee2).isNotNull();
	}
	
	@DisplayName("JPQL test Employee jpql named param")
	@Test
	public void givenEmployeeFirstnameLastName_whenFindJpqlnamedParam_thenGet() {
		// given
		employee = employeeRepository.save(employee);
		
		// when
		Employee employee2 = employeeRepository.findByJpqlWithNamedParameters("A", "B");
		
		// than
		assertThat(employee2).isNotNull();
	}
	
	@DisplayName("JPQL test native Query")
	@Test
	public void givenEmployeeFirstnameLastNameNativeQuery_whenFindEmployee_thenGetEmployee() {
		// given
		employee = employeeRepository.save(employee);
		
		// when
		Employee employeeDB = employeeRepository.findByJpqlWithNamedParameters("A", "B");
		
		// than
		assertThat(employeeDB).isNotNull();
	}
	
	@DisplayName("JPQL test native Query")
	@Test
	public void givenEmployeeFirstnameLastNameNativeQuery_whenFindEmployeeUningNamedParam_thenGetEmployee() {
		// given
		employee = employeeRepository.save(employee);
		
		// when
		Employee employeeDB = employeeRepository.findByJpqlWithNativeQueryNamedParam("A", "B");
		
		// than
		assertThat(employeeDB).isNotNull();
	}
}
