package com.miaoqi.mapper.services;

import com.miaoqi.mapper.entities.Employee;
import com.miaoqi.mapper.mappers.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> getAll() {
        return this.employeeMapper.selectAll();
    }

}
