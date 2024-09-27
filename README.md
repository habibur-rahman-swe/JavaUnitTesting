# Spring Boot Project Testing in different layers
Basic of unit testing

- given - precondition or setup
- when - action or the behaviour that we are going test
- then - verify the output

- @Test - always use test annotation on a test method
- @DisplayName("message") - show the message of the test
## Testing Repository Layer
- `@DataJpaTest` annotations is used for testing `repository` layer. `repository` layer connected only with the `database`
- Example:
```
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

```