package com.miaoqi.mybatis.dao;

import com.miaoqi.mybatis.bean.Department;

public interface DepartmentMapper {

    public Department getDeptById(Integer id);

    /**
     *
     * @author miaoqi
     * @date 2018年3月7日
     * @description 级联查询员工
     * @param id
     * @return
     */
    public Department getDeptByIdPlus(Integer id);

}
