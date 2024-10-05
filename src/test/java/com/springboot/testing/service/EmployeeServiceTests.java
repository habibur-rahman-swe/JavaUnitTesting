package com.springboot.testing.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
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
	

	// JUnit test for getAllEmployees method
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList(){
        // given - precondition or setup

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tony@gmail.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

        // when -  action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployee();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllEmployees method for empty list")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){
        // given - precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the 
        List<Employee> employeeList = employeeService.getAllEmployee();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(0);
    }

 // JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        // given
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        // then
        assertThat(savedEmployee).isNotNull();

    }
    
 // JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("ram@gmail.com");
        employee.setFirstName("Ram");
        // when -  action or the behaviour that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
    }
    
 // JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        // given - precondition or setup
        long employeeId = 1L;

        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when -  action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
    
}
