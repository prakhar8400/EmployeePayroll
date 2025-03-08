package io.reflectoring.EmployeePayroll.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.reflectoring.EmployeePayroll.DTO.EmployeeDTO;
import io.reflectoring.EmployeePayroll.Entities.Employee;
import io.reflectoring.EmployeePayroll.Repository.EmployeeRepository;
import io.reflectoring.EmployeePayroll.Exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j  // Lombok Logging Annotation
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("Creating a new employee: {}", employeeDTO);
        Employee employee = new Employee(null, employeeDTO.getName(), employeeDTO.getEmail(),
                employeeDTO.getDepartment(), employeeDTO.getSalary());
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee created successfully with ID: {}", savedEmployee.getId());
        return new EmployeeDTO(savedEmployee.getId(), savedEmployee.getName(),
                savedEmployee.getEmail(), savedEmployee.getDepartment(), savedEmployee.getSalary());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with ID: " + id);
                });
        log.info("Employee found: {}", employee);
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getEmail(),
                employee.getDepartment(), employee.getSalary());
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");
        List<EmployeeDTO> employees = employeeRepository.findAll().stream()
                .map(emp -> new EmployeeDTO(emp.getId(), emp.getName(),
                        emp.getEmail(), emp.getDepartment(), emp.getSalary()))
                .collect(Collectors.toList());
        log.info("Total employees fetched: {}", employees.size());
        return employees;
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with ID: " + id);
                });

        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);
        log.info("Employee updated successfully: {}", updatedEmployee);
        return new EmployeeDTO(updatedEmployee.getId(), updatedEmployee.getName(),
                updatedEmployee.getEmail(), updatedEmployee.getDepartment(), updatedEmployee.getSalary());
    }

    @Override
    public void deleteEmployee(Long id) {
        log.warn("Deleting employee with ID: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with ID: " + id);
                });
        employeeRepository.delete(employee);
        log.info("Employee deleted successfully with ID: {}", id);
    }
}
