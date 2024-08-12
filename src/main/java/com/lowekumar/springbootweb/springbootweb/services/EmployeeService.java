package com.lowekumar.springbootweb.springbootweb.services;

import com.lowekumar.springbootweb.springbootweb.dto.EmployeeDTO;
import com.lowekumar.springbootweb.springbootweb.entities.EmployeeEntity;
import com.lowekumar.springbootweb.springbootweb.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntity1 ->modelMapper.map(employeeEntity1, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> emp = employeeRepository.findAll();
        return emp.stream()
                .map(employeeEntity->modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());


    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {

        EmployeeEntity toemployeeEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        EmployeeEntity savedEmpEntity = employeeRepository.save(toemployeeEntity);
        return modelMapper.map(savedEmpEntity, EmployeeDTO.class);

    }

    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO, Long id) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(id);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        EmployeeDTO updatedEmployeeDTO = modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
        return updatedEmployeeDTO;

    }

    public boolean isExistEmployeeById(Long id) {
        return employeeRepository.existsById(id);

    }
    public boolean deleteEmployeeById(Long employee_id)
    {
        boolean exist = isExistEmployeeById(employee_id);
        if (!exist) return false;
        employeeRepository.deleteById(employee_id);
        return true;
    }

    public EmployeeDTO updatePartialEmployeeById(Map<String, Object> updates, Long employeeId)
    {
        boolean exist = isExistEmployeeById(employeeId);
        if(!exist) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).get();
        // using Reflection below concept now to change the object partially
        updates.forEach((field, value)->{
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated, employeeEntity, value);
        });

        // Changing EmployeeEntity Object to EmployeeDTO class
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDTO.class);


    }

}
