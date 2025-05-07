package com.ck.wi.model.dao;

import com.ck.wi.model.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeDao extends CrudRepository<Employee, Integer> {
}
