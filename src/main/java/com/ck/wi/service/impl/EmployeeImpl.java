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

        Employee employee;

        if (employeeDto.getEmployeesId() != null) {
            employee = employeeDao.findById(employeeDto.getEmployeesId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
        } else {
            employee = new Employee();
            employee.setCreatedBy(employeeDto.getUser());
        }

        employee.setLegalName(employeeDto.getLegalName());
        employee.setEmployeeNumber(employeeDto.getEmployeeNumber());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setStatus(employeeDto.getStatus());
        employee.setTitle(employeeDto.getTitle());
        employee.setPayGroup("Weekly");
        employee.setEmployeeStatus("1");
        employee.setUpdatedBy(employeeDto.getUser());

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
