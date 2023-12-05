package junit.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.miaoqi.mybatis.entities.Department;
import com.miaoqi.mybatis.services.DepartmentService;

public class MyBatisTest {

    private DepartmentService departmentService;

    {
        departmentService = new ClassPathXmlApplicationContext("spring-context.xml").getBean(DepartmentService.class);
    }

    @Test
    public void testGetAll() {
        List<Department> list = departmentService.getAll();
        for (Department department : list) {
            System.out.println(department);
        }
    }

}
