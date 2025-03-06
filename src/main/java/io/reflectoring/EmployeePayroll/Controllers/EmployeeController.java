package io.reflectoring.EmployeePayroll.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.reflectoring.EmployeePayroll.Services.EmployeeService;
import io.reflectoring.EmployeePayroll.DTO.EmployeeDTO;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


}

