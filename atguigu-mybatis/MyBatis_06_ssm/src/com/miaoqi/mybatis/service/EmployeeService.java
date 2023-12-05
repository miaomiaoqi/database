package com.miaoqi.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miaoqi.mybatis.bean.Employee;
import com.miaoqi.mybatis.dao.EmployeeMapper;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> emps() {
        return employeeMapper.emps();
    }

}
