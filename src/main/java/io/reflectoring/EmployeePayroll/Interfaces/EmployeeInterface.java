package io.reflectoring.EmployeePayroll.Interfaces;

import java.util.List;
import io.reflectoring.EmployeePayroll.DTO.EmployeeDTO;

public interface EmployeeInterface {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(Long id);
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
}
