package com.miaoqi.mapper.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.miaoqi.mapper.entities.Employee;
import com.miaoqi.mapper.services.EmployeeService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

public class EmployeeMapperTest {

    private ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
    private EmployeeService employeeService = ctx.getBean(EmployeeService.class);

    @Test
    public void testSelectOne() {
        // 1. 创建封装查询条件的实体类对象
        // 非空的值会作为查询条件
        Employee employeeQueryCondition = new Employee(null, "bob", 5560.11, null);
        // 2. 执行查询
        Employee emp = employeeService.getOne(employeeQueryCondition);
        System.out.println(emp);
    }

    @Test
    public void testSelect() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectAll() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectCount() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByPrimaryKey() {
        Integer empdId = 3;
        Employee employee = employeeService.getEmployeeById(empdId);
        System.out.println(employee);
    }

    @Test
    public void testExistsWithPrimaryKey() {
        Integer empdId = 3;
        Boolean exists = employeeService.isExsists(empdId);
        System.out.println(exists);
    }

    @Test
    public void testInsert() {
        Employee employee = new Employee(null, "emp03", 3000.00, 23);
        employeeService.saveEmployee(employee);
        System.out.println(employee.getEmpId());
    }

    @Test
    public void testInsertSelective() {
        Employee employee = new Employee(null, "emp04", null, 23);
        employeeService.saveEmployeeSelective(employee);
        System.out.println(employee.getEmpId());
    }

    @Test
    public void testUpdateByPrimaryKey() {
        Employee employee = new Employee(7, "empNewName", null, null);
        employeeService.updateEmployee(employee);
        System.out.println(employee.getEmpId());
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        Employee employee = new Employee(7, "empNewName", null, null);
        employeeService.updateEmployeeSelective(employee);
        System.out.println(employee.getEmpId());
    }

    @Test
    public void testDelete() {
        Employee employee = null;
        employeeService.removeEmployee(employee);
    }

    @Test
    public void testDeleteByPrimaryKey() {
        Integer empId = 5;
        employeeService.deleteEmployeeById(empId);
    }

    @Test
    public void testSelectByExample() {
        // 目标: WHERE (emp_salary > ? and emp_age < ?) OR (emp_salary < ? and emp_age > ?)
        Example example = new Example(Employee.class);

        // 排序
        example.orderBy("empSalary").asc().orderBy("empAge").desc();

        // 去重
        example.setDistinct(true);

        // 指定查询字段
        example.selectProperties("empName", "empSalary");

        Criteria criteria01 = example.createCriteria();
        Criteria criteria02 = example.createCriteria();

        criteria01.andGreaterThan("empSalary", 3000).andLessThan("empAge", 25);
        criteria02.andLessThan("empSalary", 5000).andGreaterThan("empAge", 30);

        example.or(criteria02);
        List<Employee> list = employeeService.getEmpListByExample(example);
        System.out.println(list);
    }

    @Test
    public void testSelectOneByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectCountByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByExample() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateByExampleSelective() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByExampleAndRowBounds() {
        fail("Not yet implemented");
    }

    @Test
    public void testSelectByRowBounds() {
        fail("Not yet implemented");
    }

}
