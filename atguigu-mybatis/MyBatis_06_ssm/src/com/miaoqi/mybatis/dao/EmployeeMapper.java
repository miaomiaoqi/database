package com.miaoqi.mybatis.dao;

import java.util.List;

import com.miaoqi.mybatis.bean.Employee;

/**
 * @author miaoqi
 * @date 2017-11-25 下午7:43
 **/
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public List<Employee> emps();

}
