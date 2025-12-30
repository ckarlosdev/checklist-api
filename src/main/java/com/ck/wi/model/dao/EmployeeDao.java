package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer> {
    Employee findByEmployeeNumber(String number);
}
