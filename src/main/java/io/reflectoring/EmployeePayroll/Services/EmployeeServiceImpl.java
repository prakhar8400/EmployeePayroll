package io.reflectoring.EmployeePayroll.Services;

import io.reflectoring.EmployeePayroll.DTO.EmployeeDTO;
import io.reflectoring.EmployeePayroll.Entities.Employee;
import io.reflectoring.EmployeePayroll.Exception.EmployeeNotFoundException;
import io.reflectoring.EmployeePayroll.Interfaces.EmployeeInterface;
import io.reflectoring.EmployeePayroll.Repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeInterface {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        try {
            log.info("Creating new employee: {}", employeeDTO);
            Employee employee = convertToEntity(employeeDTO);
            Employee savedEmployee = employeeRepository.save(employee);
            return convertToDTO(savedEmployee);
        } catch (Exception e) {
            log.error("Error creating employee: {}", e.getMessage());
            throw new RuntimeException("Failed to create employee, please try again.");
        }
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        try {
            log.info("Fetching employee with ID: {}", id);
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
            return convertToDTO(employee);
        } catch (EmployeeNotFoundException e) {
            log.error("Employee not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error fetching employee: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch employee, please try again.");
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            log.info("Fetching all employees");
            return employeeRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching employees: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch employees, please try again.");
        }
    }

    @Transactional
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
            return convertToDTO(updatedEmployee);
        } catch (EmployeeNotFoundException e) {
            log.error("Employee not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error updating employee: {}", e.getMessage());
            throw new RuntimeException("Failed to update employee, please try again.");
        }
    }

    @Transactional
    @Override
    public void deleteEmployee(Long id) {
        try {
            log.info("Deleting employee with ID: {}", id);
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
            employeeRepository.delete(employee);
        } catch (EmployeeNotFoundException e) {
            log.error("Employee not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error deleting employee: {}", e.getMessage());
            throw new RuntimeException("Failed to delete employee, please try again.");
        }
    }

    // Utility methods for conversion
    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getName(),
                employee.getEmail(), employee.getDepartment(), employee.getSalary());
    }

    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        return new Employee(null, employeeDTO.getName(), employeeDTO.getEmail(),
                employeeDTO.getDepartment(), employeeDTO.getSalary());
    }
}
