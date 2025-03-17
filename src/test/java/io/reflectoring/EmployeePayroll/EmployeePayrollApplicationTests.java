package io.reflectoring.EmployeePayroll;

import io.reflectoring.EmployeePayroll.DTO.EmployeeDTO;
import io.reflectoring.EmployeePayroll.Entities.Employee;
import io.reflectoring.EmployeePayroll.Exception.EmployeeNotFoundException;
import io.reflectoring.EmployeePayroll.Repository.EmployeeRepository;
import io.reflectoring.EmployeePayroll.Services.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	private Employee employee;
	private EmployeeDTO employeeDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		employee = new Employee(1L, "John Doe", "john@example.com", "Engineering", 50000);
		employeeDTO = new EmployeeDTO(1L, "John Doe", "john@example.com", "Engineering", 50000);
	}

	@Test
	void testCreateEmployee() {
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
		EmployeeDTO savedEmployee = employeeService.createEmployee(employeeDTO);
		assertNotNull(savedEmployee);
		assertEquals("John Doe", savedEmployee.getName());
	}

	@Test
	void testGetEmployeeById() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		EmployeeDTO foundEmployee = employeeService.getEmployeeById(1L);
		assertEquals("John Doe", foundEmployee.getName());
	}

	@Test
	void testGetEmployeeById_NotFound() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
		assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(1L));
	}

	@Test
	void testGetAllEmployees() {
		List<Employee> employees = Arrays.asList(employee);
		when(employeeRepository.findAll()).thenReturn(employees);
		List<EmployeeDTO> employeeList = employeeService.getAllEmployees();
		assertEquals(1, employeeList.size());
	}

	@Test
	void testUpdateEmployee() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		EmployeeDTO updatedEmployee = employeeService.updateEmployee(1L, employeeDTO);
		assertEquals("John Doe", updatedEmployee.getName());
	}

	@Test
	void testDeleteEmployee() {
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		doNothing().when(employeeRepository).delete(employee);

		assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));
	}
}
