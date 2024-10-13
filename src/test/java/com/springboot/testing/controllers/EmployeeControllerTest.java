package com.springboot.testing.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.testing.model.Employee;
import com.springboot.testing.service.EmployeeService;

@WebMvcTest
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee()
			throws JsonProcessingException, Exception {
		// given
		Employee employee = Employee.builder().firstName("A").lastName("B").email("C@C.C").build();

		BDDMockito.given(employeeService.save(any(Employee.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));

		// when
		ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee)));

		// then
		response.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
				.andExpect(jsonPath("$.email", is(employee.getEmail())));
	}

	@Test
	public void givenListOfEmployees_whenGetAllEmployees_then() throws Exception {
		// given
		List<Employee> listOfEmployees = new ArrayList<>();
		listOfEmployees.add(Employee.builder().firstName("A").lastName("B").email("C@C.C").build());
		listOfEmployees.add(Employee.builder().firstName("B").lastName("C").email("D@D.D").build());
		given(employeeService.getAllEmployee()).willReturn(listOfEmployees);

		// when
		ResultActions response = mockMvc.perform(get("/api/employees"));

		// then
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
	}

	@Test
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
		// given
		Employee employee = Employee.builder().firstName("A").lastName("B").email("C@C.C").build();
		long employeeId = 1;
		given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

		// when
		ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

		// then
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
				.andExpect(jsonPath("$.email", is(employee.getEmail())));

	}

	@Test
	public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmptyEmployeeObject() throws Exception {
		// given
		long employeeId = 1;
		given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

		// when
		ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

		// then
		response.andExpect(status().isNotFound()).andDo(print());

	}

	// positive scenario
	@Test
	public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeeByEmployeeId()
			throws JsonProcessingException, Exception {
		// given
		Employee savedEmployee = Employee.builder().firstName("A").lastName("B").email("C@C.C").build();
		long employeeId = 1;

		Employee updatedEmployee = Employee.builder().firstName("B").lastName("C").email("D@D.D").build();

		given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
		given(employeeService.updateEmployee(any(Employee.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));
		// when
		ResultActions respone = mockMvc.perform(put("/api/employees/{id}", employeeId)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployee)));

		// then
		respone.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
				.andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
	}

	// negative scenario
	@Test
	public void givenUpdatedEmployeeWithId_whenUpdateEmployee_thenReturnUpdateEmployeeeBy()
			throws JsonProcessingException, Exception {
		// given
		long employeeId = 1;

		Employee updatedEmployee = Employee.builder().firstName("B").lastName("C").email("D@D.D").build();

		given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
		given(employeeService.updateEmployee(any(Employee.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));
		// when
		ResultActions respone = mockMvc.perform(put("/api/employees/{id}", employeeId)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployee)));

		// then
		respone.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
		// given
		long employeeId = 1l;

		willDoNothing().given(employeeService).deleteEmployee(employeeId);

		// when
		ResultActions respoonse = mockMvc.perform(delete("/api/employees/{id}", employeeId));

		// then
		respoonse.andExpect(status().isOk()).andDo(print());
	}
}
