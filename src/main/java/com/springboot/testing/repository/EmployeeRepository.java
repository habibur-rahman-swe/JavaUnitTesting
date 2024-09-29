package com.springboot.testing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.testing.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Optional<Employee> findByEmail(String email);
	
	// index parameter
	@Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);
	
	// jpql with named parameters
	@Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
    Employee findByJpqlWithNamedParameters(@Param("firstName")String firstName, @Param("lastName")String lastName);
	
	// index parameter
	@Query(value = "select * from employees e where e.first_name=?1 and e.last_name=?2", nativeQuery = true)
    Employee findByJpqlWithNativeQuery(@Param("firstName")String firstName, @Param("lastName")String lastName);
	
	@Query(value = "select * from employees e where e.first_name=:firstName and e.last_name=:lastName", nativeQuery = true)
    Employee findByJpqlWithNativeQueryNamedParam(@Param("firstName")String firstName, @Param("lastName")String lastName);
}
