package com.lowekumar.springbootweb.springbootweb.controllers;

import com.lowekumar.springbootweb.springbootweb.dto.EmployeeDTO;
import com.lowekumar.springbootweb.springbootweb.entities.EmployeeEntity;
import com.lowekumar.springbootweb.springbootweb.repositories.EmployeeRepository;
import com.lowekumar.springbootweb.springbootweb.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/{employee_id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employee_id") Long id){
//        return new EmployeeDTO(id, "Lowe", "lowe@gmail.com",28, "Developer", 9000000.00, LocalDate.of(2024, 12, 12), true);
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(id);
        return employeeDTO.map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee(@RequestParam(required = false, name="inputName") String name,
                                               @RequestParam(required = false, name="inputAge") Integer age){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping()
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO employee){
        EmployeeDTO employeeDTO = employeeService.createNewEmployee(employee);
        return new ResponseEntity<>(employeeDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{employee_id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employee_id){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeDTO, employee_id));

    }

    @DeleteMapping("/{employee_id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long employee_id){
        boolean gotDeletedEmployee = employeeService.deleteEmployeeById(employee_id);
        if(gotDeletedEmployee){ return ResponseEntity.ok(true);}
        return ResponseEntity.notFound().build();

    }


    @PatchMapping("/{employee_id}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String,Object> updates, @PathVariable Long employee_id)
    {
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(updates, employee_id);
        if(employeeDTO == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);

    }
}
