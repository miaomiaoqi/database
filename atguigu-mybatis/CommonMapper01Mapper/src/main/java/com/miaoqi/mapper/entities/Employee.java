package com.miaoqi.mapper.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

//use common_mapper;
//CREATE TABLE `tabple_emp` (
//`emp_id` int NOT NULL AUTO_INCREMENT,
//`emp_name` VARCHAR(500) NULL ,
//`emp_salary` DOUBLE(15, 5) NULL ,
//`emp_age` INT NULL ,
//PRIMARY KEY (`emp_id`)
//);
//INSERT INTO `tabple_emp` (`emp_name`, `emp_salary`, `emp_age`) VALUES ('tom', '1254.37', '27');
//INSERT INTO `tabple_emp` (`emp_name`, `emp_salary`, `emp_age`) VALUES ('jerry', '6635.42', '38');
//INSERT INTO `tabple_emp` (`emp_name`, `emp_salary`, `emp_age`) VALUES ('bob', '5560.11', '40');
//INSERT INTO `tabple_emp` (`emp_name`, `emp_salary`, `emp_age`) VALUES ('kate', '2209.11', '22');
//INSERT INTO `tabple_emp` (`emp_name`, `emp_salary`, `emp_age`) VALUES ('justin', '4203.15', '30');

@Table(name = "tabple_emp")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empId;
    @Column(name = "emp_name")
    private String empName;
    private Double empSalary;
    private Integer empAge;
    @Transient
    private String otherThings;

    public Employee() {
        super();
    }

    public Employee(Integer empId, String empName, Double empSalary, Integer empAge) {
        super();
        this.empId = empId;
        this.empName = empName;
        this.empSalary = empSalary;
        this.empAge = empAge;
    }

    @Override
    public String toString() {
        return "Employee [empId=" + empId + ", empName=" + empName + ", empSalary=" + empSalary + ", empAge=" + empAge
                + "]";
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(Double empSalary) {
        this.empSalary = empSalary;
    }

    public Integer getEmpAge() {
        return empAge;
    }

    public void setEmpAge(Integer empAge) {
        this.empAge = empAge;
    }

}
