package com.atguigu.mybatis.test;

import com.atguigu.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisTest {

    /**
     * SqlSession 默认不自动提交事务, 若需要自动提交事务
     * 可以使用 SqlSessionFactory.openSession(true)
     *
     * @author miaoqi
     * @date 2023-12-24 0:22:18
     *
     * @return
     */
    @Test
    public void testMyBatis() throws IOException {
        // 加载核心配置文件
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        // 获取 SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        // 获取 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        // 获取 SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        // 获取 Mapper 接口对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 测试功能
        int result = userMapper.insertUser();
        // 提交事务
        // sqlSession.commit();
        System.out.println("result: " + result);
    }

}