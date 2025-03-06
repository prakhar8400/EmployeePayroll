package io.reflectoring.EmployeePayroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.reflectoring.EmployeePayroll.Entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

