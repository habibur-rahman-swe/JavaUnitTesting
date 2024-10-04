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
### 🔗 Links
Repository Lair Unit test [Repository Test Class](https://github.com/habibur-rahman-swe/JavaUnitTesting/blob/spring-boot-app-testing/src/test/java/com/springboot/testing/repository/EmployeeRepositoryTest.java).

## Testing Service Layer
-	Create the mock of the dependencies of the service class 
	``` 
	import org.mockito.Mock;

	@Mock
	private EmployeeRepository employeeRepository;
	```

-	Inject the mock to the service class

	```
	import org.mockito.InjectMocks;

	@InjectMocks
	private EmployeeServiceImpl employeeService;
	```	
-	Integrat the `mocks` using this annotation to the declaration of the class
	```
	import org.junit.jupiter.api.extension.ExtendWith;

	@ExtendWith(MockitoExtension.class)
	public class EmployeeServiceTests {
	
	}
	```

- A full example of a test
	```
	@Test
	@DisplayName("Juit test for save method")
	public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployee() throws Exception {
		// given
		Employee employee = Employee.builder().id(1l).firstName("A").lastName("B").email("e@email.com").build();

		BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

		BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

		System.out.println(employeeRepository);
		System.out.println(employeeService);
		
		// when
		Employee savedEmployee = employeeService.save(employee);

		// then
		assertThat(savedEmployee).isNotNull();
	}
	```


