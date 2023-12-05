package com.miaoqi.mybatis.dao;

import java.util.List;

import com.miaoqi.mybatis.bean.Employee;

public interface EmployeeMapperPlus {

    public Employee getEmpById(Integer id);

    public Employee getEmpAndDept(Integer id);

    /**
     *
     * @author miaoqi
     * @date 2018年3月7日
     * @description 分步级联查询
     * @param id
     * @return
     */
    public Employee getEmpByIdStep(Integer id);

    /**
     *
     * @author miaoqi
     * @date 2018年3月8日
     * @description 根据部门id查询员工
     * @param deptId
     * @return
     */
    public List<Employee> getEmpsByDeptId(Integer deptId);

}
