package com.ck.wi.controller;

import com.ck.wi.model.dto.EmployeeDto;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.service.IEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://127.0.0.1:5500", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private IEmployee employeeService;

    @PostMapping("employee")
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto){
        Employee employeeSave = employeeService.save(employeeDto);

        return EmployeeDto.builder()
                .employeesId(employeeSave.getEmployeesId())
                .employeeNumber(employeeSave.getEmployeeNumber())
                .firstName(employeeSave.getFirstName())
                .lastName(employeeSave.getLastName())
                .status(employeeSave.getStatus())
                .title(employeeSave.getTitle())
                .build();
    }

    @PutMapping("employee")
    public EmployeeDto update(@RequestBody EmployeeDto employeeDto){
        Employee employeeUpdate = employeeService.save(employeeDto);

        return EmployeeDto.builder()
                .employeesId(employeeUpdate.getEmployeesId())
                .employeeNumber(employeeUpdate.getEmployeeNumber())
                .firstName(employeeUpdate.getFirstName())
                .lastName(employeeUpdate.getLastName())
                .status(employeeUpdate.getStatus())
                .title(employeeUpdate.getTitle())
                .build();
    }

    @DeleteMapping("employee/{id}")
    public void delete(@PathVariable Integer id){
        Employee employee = employeeService.findById(id);
        employeeService.delete(employee);
    }

    @GetMapping("employee/{id}")
    public EmployeeDto showById(@PathVariable Integer id){
        Employee employee = employeeService.findById(id);

        return EmployeeDto.builder()
                .employeesId(employee.getEmployeesId())
                .employeeNumber(employee.getEmployeeNumber())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .status(employee.getStatus())
                .title(employee.getTitle())
                .build();
    }

    @GetMapping("employee")
    public List<EmployeeDto> showAll(){
        List<Employee> employees = employeeService.findAll();
        return employees.stream()
                .map(employee -> EmployeeDto.builder()
                        .employeesId(employee.getEmployeesId())
                        .employeeNumber(employee.getEmployeeNumber())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .status(employee.getStatus())
                        .title(employee.getTitle())
                        .build())
                .collect(Collectors.toList());
    }
}
