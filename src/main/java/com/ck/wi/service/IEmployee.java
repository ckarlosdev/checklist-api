package com.ck.wi.service;

import com.ck.wi.model.dto.EmployeeDto;
import com.ck.wi.model.entity.Employee;

import java.util.List;

public interface IEmployee {

    Employee save(EmployeeDto employeeDto);

    Employee findById(Integer id);

    void delete(Employee employee);

    List<Employee> findAll();
}
