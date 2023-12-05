package com.miaoqi.mapper.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miaoqi.mapper.entities.Employee;
import com.miaoqi.mapper.mappers.EmployeeMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public Employee getOne(Employee employeeQueryCondition) {
        return employeeMapper.selectOne(employeeQueryCondition);
    }

    public Employee getEmployeeById(Integer empdId) {
        return employeeMapper.selectByPrimaryKey(empdId);
    }

    public Boolean isExsists(Integer empdId) {
        return employeeMapper.existsWithPrimaryKey(empdId);
    }

    public void saveEmployee(Employee employee) {
        employeeMapper.insert(employee);
    }

    public void saveEmployeeSelective(Employee employee) {
        employeeMapper.insertSelective(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeMapper.updateByPrimaryKey(employee);
    }

    public void updateEmployeeSelective(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public void removeEmployee(Employee employee) {
        employeeMapper.delete(employee);
    }

    public void deleteEmployeeById(Integer empId) {
        employeeMapper.deleteByPrimaryKey(empId);
    }

    public List<Employee> getEmpListByExample(Example example) {
        return employeeMapper.selectByExample(example);
    }

}
