package com.miaoqi.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.miaoqi.mybatis.bean.Employee;

/**
 * @author miaoqi
 * @date 2017-11-25 下午7:43
 **/
public interface EmployeeMapper {

    // 多条记录封装一个map, Map<Integer, Employee>: 键是这条记录的主键, 值是记录封装后的java对象
    // 告诉MyBatis封装这个map的时候使用哪个属性作为map的key
    @MapKey("id")
    public Map<Integer, Employee> getEmpsByLastNameLikeReturnMap(String lastName);

    // 返回一条记录的map, key就是列名, 值就是对应的值
    public Map<String, Object> getEmpByIdReturnMap(Integer id);

    public List<Employee> getEmpsByLastNameLike(String lastName);

    public Employee getEmpByMap(Map<String, Object> map);

    public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    public Employee getEmpById(Integer id);

    public Long addEmp(Employee employee);

    public void updateEmp(Employee employee);

    public void deleteEmp(Integer id);

}
