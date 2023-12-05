package com.miaoqi.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.miaoqi.mybatis.bean.Employee;

public interface EmployeeMapperDynamicSQL {
    /**
     *
     * @author miaoqi
     * @date 2018年3月8日
     * @description 根据条件查询
     * @param employee
     * @return
     */
    public List<Employee> getEmpsByConditionIf(Employee employee);

    public List<Employee> getEmpsByConditionTrim(Employee employee);

    public List<Employee> getEmpsByConditionChoose(Employee employee);

    public void updateEmp(Employee employee);

    public List<Employee> getEmpsByConditionForeach(List<Integer> ids);

    /**
     *
     * @author miaoqi
     * @date 2018年3月9日
     * @description 批量增加员工
     * @param emps
     */
    public void addEmps(@Param("emps") List<Employee> emps);
}
