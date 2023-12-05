package com.miaoqi.mybatis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miaoqi.mybatis.bean.Employee;
import com.miaoqi.mybatis.service.EmployeeService;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/emps")
    public String emps(Map<String, Object> map) {
        List<Employee> emps = employeeService.emps();
        map.put("emps", emps);
        return "list";
    }

}
