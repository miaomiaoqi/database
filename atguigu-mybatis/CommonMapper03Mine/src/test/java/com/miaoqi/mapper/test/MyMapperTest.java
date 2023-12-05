package com.miaoqi.mapper.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.miaoqi.mapper.entities.Employee;
import com.miaoqi.mapper.services.EmployeeService;

public class MyMapperTest {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        EmployeeService employeeService = context.getBean(EmployeeService.class);

        List<Employee> empList = employeeService.getAll();
        for (Employee employee : empList) {
            System.out.println(employee);
        }

        context.close();

    }

}
