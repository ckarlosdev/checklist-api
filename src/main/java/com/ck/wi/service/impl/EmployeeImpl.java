package com.ck.wi.service.impl;

import com.ck.wi.model.dao.EmployeeDao;
import com.ck.wi.model.dto.EmployeeDto;
import com.ck.wi.model.entity.Employee;
import com.ck.wi.service.IEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeImpl implements IEmployee {

    @Autowired
    private EmployeeDao employeeDao;

    @Transactional
    @Override
    public Employee save(EmployeeDto employeeDto) {
        Employee employee = Employee.builder()
                .employeesId(employeeDto.getEmployeesId())
                .employeeNumber(employeeDto.getEmployeeNumber())
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .status(employeeDto.getStatus())
                .title(employeeDto.getTitle())
                .build();
        return employeeDao.save(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public Employee findById(Integer id) {
        return employeeDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> findAll() {
        return (List<Employee>) employeeDao.findAll();
    }

    @Transactional
    @Override
    public void delete(Employee employee) {
        employeeDao.delete(employee);
    }
}
