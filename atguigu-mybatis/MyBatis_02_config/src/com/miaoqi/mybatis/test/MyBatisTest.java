package com.miaoqi.mybatis.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.miaoqi.mybatis.bean.Employee;
import com.miaoqi.mybatis.dao.EmployeeMapper;

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

}
