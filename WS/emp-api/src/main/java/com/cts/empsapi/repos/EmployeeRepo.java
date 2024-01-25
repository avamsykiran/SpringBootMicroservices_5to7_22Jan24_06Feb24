package com.cts.empsapi.repos;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.empsapi.entities.Employee;
	
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
	
	boolean existsByMailId(String mailId);
	boolean existsByMobile(String mobile);
	
	Optional<Employee> findByMailId(String mailId);
	Optional<Employee> findByMobile(String mobile);
	
	@Query("SELECT e FROM Employee e WHERE e.joinDate BETWEEN :start AND :end")
	List<Employee> findEmployeesJoinedIn(LocalDate start,LocalDate end);
	
	@Query("SELECT e.fullName,e.joinDate FROM Employee e")
	List<Object[]> findNamesAndJoinDates();
}
