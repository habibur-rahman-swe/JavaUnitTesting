package com.springboot.testing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springboot.testing.exception.ResourceNotFoundException;
import com.springboot.testing.model.Employee;
import com.springboot.testing.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeServiceImpl employeeService;

	private Employee employee;
	
	@BeforeEach
	public void setUp() {
		employee = Employee.builder().id(1l).firstName("A").lastName("B").email("e@email.com").build();		
	}

	@Test
	@DisplayName("Juit test for save method")
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployee() throws Exception {
		// given
		given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

		given(employeeRepository.save(employee)).willReturn(employee);

		System.out.println(employeeRepository);
		System.out.println(employeeService);
		
		// when
		Employee savedEmployee = employeeService.save(employee);

		// then
		assertThat(savedEmployee).isNotNull();
	}
	
	@Test
	@DisplayName("Juit test for save method which throws exception")
	public void givenEmployeeObject_whenSaveEmployee_thenThrowException() throws Exception {
		// given
		given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
		
		// when
		assertThrows(ResourceNotFoundException.class, () -> {
			employeeService.save(employee);
		});
		
		// then
		verify(employeeRepository, never()).save(any(Employee.class));
	}
}
