package com.miaoqi.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.miaoqi.mybatis.bean.Department;
import com.miaoqi.mybatis.bean.Employee;
import com.miaoqi.mybatis.dao.DepartmentMapper;
import com.miaoqi.mybatis.dao.EmployeeMapper;
import com.miaoqi.mybatis.dao.EmployeeMapperDynamicSQL;
import com.miaoqi.mybatis.dao.EmployeeMapperPlus;

/**
 * @author miaoqi
 * @date 2017-11-25 下午6:04
 **/
public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 1. 根据xml配置文件(全局配置文件)创建一个SqlSessionFactory对象
     *      有数据源一些运行环境信息
     * 2. sql映射文件: 配置了每一个sql, 以及sql的封装规则
     * 3. 将sql映射文件注册在全局配置文件中
     * 4. 写代码
     *      1). 根据全局配置文件得到SqlSessionFactory
     *      2). 使用SqlSessionFactory获取到SqlSession对象来执行增删改查
     *          一个SqlSession对象就是代表和数据库的一次会话, 用完关闭
     *      3). 使用sql的唯一标识来告诉MyBatis执行哪个sql, sql都是保存在sql映射文件中的
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 2. 获取SqlSession实例, 能直接执行已经映射的sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Employee employee = sqlSession.selectOne("com.miaoqi.mybatis.EmployeeMapper.selectEmp", 1);
            System.out.println(employee);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void test01() throws IOException {
        // 1. 获取SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2. 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            // 3. 获取接口的实现类对象
            // 会为接口自动的创建一个代理对象, 代理对象去执行增删改查方法
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee emp = mapper.getEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(emp);
            sqlSession.close();
        } finally {
            sqlSession.close();
        }
    }

    /**
     *
     * @author miaoqi
     * @date 2017年11月25日
     * @description 测试增删改
     * 1. mybatis允许增删改直接定义以下类型返回值
     *      Integer, Long, Boolean, void
     */
    @Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 1. 获取到的sqlSession不会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            // 测试添加
            Employee emp = new Employee(null, "jerry", "jerry@miaoqi.cn", "1");
            mapper.addEmp(emp);
            System.out.println(emp.getId());
            // 测试修改
            //            mapper.updateEmp(new Employee(1, "jerry", "jerry@miaoqi.cn", "1"));
            // 测试删除
            //            mapper.deleteEmp(2);
            // 2. 手动提交
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    // EmployeeMapper
    @Test
    public void test04() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 1. 获取到的sqlSession不会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            //            Employee emp = mapper.getEmpByIdAndLastName(1, "jerry");
            //            Map<String, Object> map = new HashMap<>();
            //            map.put("id", 1);
            //            map.put("lastName", "jerry");
            //            map.put("tableName", "tbl_employee");
            //            Employee emp = mapper.getEmpByMap(map);
            //            System.out.println(emp);
            List<Employee> emps = mapper.getEmpsByLastNameLike("%e%");
            System.out.println(emps);

            //            Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
            //            System.out.println(map);
            Map<Integer, Employee> emps2 = mapper.getEmpsByLastNameLikeReturnMap("%e%");
            System.out.println(emps2);
        } finally {
            sqlSession.close();
        }
    }

    // EmployeeMapperPlus
    @Test
    public void test05() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 1. 获取到的sqlSession不会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            //            Employee emp = mapper.getEmpById(1);
            //            System.out.println(emp);
            //            System.out.println("--------------");
            //            Employee emp = mapper.getEmpAndDept(1);
            //            System.out.println(emp);
            //            System.out.println(emp.getDept());
            //            System.out.println("----------");
            Employee empByIdStep = mapper.getEmpByIdStep(3);
            System.out.println(empByIdStep);
            System.out.println(empByIdStep.getDept());
        } finally {
            sqlSession.close();
        }
    }

    // DepartmentMapper
    @Test
    public void test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 1. 获取到的sqlSession不会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
            Department deptByIdPlus = mapper.getDeptByIdPlus(1);
            System.out.println(deptByIdPlus);
            System.out.println(deptByIdPlus.getEmps());
        } finally {
            sqlSession.close();
        }
    }

    // EmployeeMapperDynamicSQL
    @Test
    public void test07() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 1. 获取到的sqlSession不会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            Employee employee = new Employee(1, "Admin2", null, null);
            //            List<Employee> emps = mapper.getEmpsByConditionIf(employee);
            //            for (Employee emp : emps) {
            //                System.out.println(emp);
            //            }
            // 查询的时候如果某些条件没带可能sql拼装会有问题
            // 1. 给where后面加上 1 = 1, 后面的条件都是 and xxx
            // 2. mybatis推荐使用<where>标签将所有的查询条件包括在内, 只能去掉第一个多出来的and
            //            List<Employee> emps2 = mapper.getEmpsByConditionTrim(employee);
            //            for (Employee employee2 : emps2) {
            //                System.out.println(employee2);
            //            }
            //            List<Employee> emps = mapper.getEmpsByConditionChoose(employee);
            //            for (Employee employee2 : emps) {
            //                System.out.println(employee2);
            //            }
            //            mapper.updateEmp(employee);
            //            sqlSession.commit();
            List<Employee> emps = mapper.getEmpsByConditionForeach(Arrays.asList(1, 2, 3, 4));
            for (Employee employee2 : emps) {
                System.out.println(employee2);
            }
        } finally {
            sqlSession.close();
        }
    }

    // EmployeeMapperDynamicSQL
    @Test
    public void test08() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 1. 获取到的sqlSession不会自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperDynamicSQL mapper = sqlSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> emps = new ArrayList<>();
            emps.add(new Employee(null, "smith", "smith@miaoqi.cn", "0", new Department(1)));
            emps.add(new Employee(null, "allen", "allen@miaoqi.cn", "1", new Department(2)));

            mapper.addEmps(emps);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
