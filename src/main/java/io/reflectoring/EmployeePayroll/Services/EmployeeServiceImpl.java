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
        log.info("Creating new employee: {}", employeeDTO);

        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);

        return convertToDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        return convertToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");

        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        // Updating fields
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToDTO(updatedEmployee);
    }

    @Transactional
    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        employeeRepository.delete(employee);
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
