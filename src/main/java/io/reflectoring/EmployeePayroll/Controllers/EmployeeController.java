package io.reflectoring.EmployeePayroll.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.reflectoring.EmployeePayroll.Services.EmployeeService;
import io.reflectoring.EmployeePayroll.DTO.EmployeeDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j  // Lombok Logging
@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/post")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Received request to create employee: {}", employeeDTO);
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        log.info("Employee created successfully with ID: {}", createdEmployee.getId());
        return ResponseEntity.ok(createdEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        log.info("Fetching employee with ID: {}", id);
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        log.info("Employee found: {}", employee);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        log.info("Fetching all employees");
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        log.info("Total employees fetched: {}", employees.size());
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {}", id);
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        log.info("Employee updated successfully: {}", updatedEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        log.warn("Deleting employee with ID: {}", id);
        employeeService.deleteEmployee(id);
        log.info("Employee deleted successfully with ID: {}", id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
