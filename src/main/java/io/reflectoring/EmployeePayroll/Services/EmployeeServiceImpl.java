package io.reflectoring.EmployeePayroll.Services;

import io.reflectoring.EmployeePayroll.DTO.EmployeeDTO;
import io.reflectoring.EmployeePayroll.Entities.Employee;
import io.reflectoring.EmployeePayroll.Exception.EmployeeNotFoundException;
import io.reflectoring.EmployeePayroll.Interfaces.EmployeeInterface;
import io.reflectoring.EmployeePayroll.Repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j  // Lombok annotation for logging
@Service
public class EmployeeServiceImpl implements EmployeeInterface {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        try {
            log.info("Creating new employee: {}", employeeDTO);
            Employee employee = new Employee(null, employeeDTO.getName(), employeeDTO.getEmail(),
                    employeeDTO.getDepartment(), employeeDTO.getSalary());
            Employee savedEmployee = employeeRepository.save(employee);
            return new EmployeeDTO(savedEmployee.getId(), savedEmployee.getName(),
                    savedEmployee.getEmail(), savedEmployee.getDepartment(), savedEmployee.getSalary());
        } catch (Exception e) {
            log.error("Error occurred while creating employee: {}", e.getMessage());
            throw new RuntimeException("Failed to create employee");
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        try {
            log.info("Fetching employee with ID: {}", id);
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
            return new EmployeeDTO(employee.getId(), employee.getName(), employee.getEmail(),
                    employee.getDepartment(), employee.getSalary());
        } catch (EmployeeNotFoundException e) {
            throw e;  // Custom exception is already handled in GlobalExceptionHandler
        } catch (Exception e) {
            log.error("Error occurred while fetching employee: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch employee");
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            log.info("Fetching all employees");
            return employeeRepository.findAll().stream()
                    .map(emp -> new EmployeeDTO(emp.getId(), emp.getName(),
                            emp.getEmail(), emp.getDepartment(), emp.getSalary()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching all employees: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch employees");
        }
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        try {
            log.info("Updating employee with ID: {}", id);
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

            employee.setName(employeeDTO.getName());
            employee.setEmail(employeeDTO.getEmail());
            employee.setDepartment(employeeDTO.getDepartment());
            employee.setSalary(employeeDTO.getSalary());

            Employee updatedEmployee = employeeRepository.save(employee);
            return new EmployeeDTO(updatedEmployee.getId(), updatedEmployee.getName(),
                    updatedEmployee.getEmail(), updatedEmployee.getDepartment(), updatedEmployee.getSalary());
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while updating employee: {}", e.getMessage());
            throw new RuntimeException("Failed to update employee");
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        try {
            log.info("Deleting employee with ID: {}", id);
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
            employeeRepository.delete(employee);
        } catch (EmployeeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while deleting employee: {}", e.getMessage());
            throw new RuntimeException("Failed to delete employee");
        }
    }
}
