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
     * 两级缓存:
     * 一级缓存:(本地缓存) sqlSession级别的缓存, 一直开启
     *      与数据库同一次会话期间查询到的数据会放在本地缓存中
     *      以后如果需要获取相同的数据, 直接从缓存中查, 没必要去查询数据库
     *
     *      一级缓存失效的情况(没有使用到当前一级缓存的情况, 效果就是, 还需要再向数据库发送查询语句)
     *      1. sqlSession不同
     *      2. sqlSession相同, 查询条件不同.(当前一级缓存中还没有这个数据)
     *      3. sqlSession相同, 两次查询之间执行了增删操作(这次增删改可能会对当前数据有影响)
     *      4. sqlSession相同, 但是手动清空了一级缓存
     * 二级缓存:(全局缓存): 基于namespace级别的缓存, 一个namespace对应一个二级缓存
     *      工作机制:
     *      1. 一个会话, 查询一条数据, 这个数据就会被放在当前会话的一级缓存中
     *      2. 如果会话关闭, 一级缓存中的数据会被保存到二级缓存中, 就可以参照二级缓存
     *
     *      使用:
     *          1). 开启全局二级缓存 <setting name="aggressiveLazyLoading" value="false"/>
     *          2). 去mapper.xml中配置使用二级缓存
     *              <cache></cache>
     *          3). 我们的POJO需要实现序列化接口
     *
     * 和缓存有关的设置/属性：
     *          1）、cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
     *          2）、每个select标签都有useCache="true"：
     *                  false：不使用缓存（一级缓存依然使用，二级缓存不使用）
     *          3）、【每个增删改标签的：flushCache="true"：（一级二级都会清除）】
     *                  增删改执行完成后就会清除缓存；
     *                  测试：flushCache="true"：一级缓存就清空了；二级也会被清除；
     *                  查询标签：flushCache="false"：
     *                      如果flushCache=true;每次查询之后都会清空缓存；缓存是没有被使用的；
     *          4）、sqlSession.clearCache();只是清楚当前session的一级缓存；
     *          5）、localCacheScope：本地缓存作用域：（一级缓存SESSION）；当前会话的所有数据保存在会话缓存中；
     *                              STATEMENT：可以禁用一级缓存；     
     *
     * 第三方缓存整合：
     *      1）、导入第三方缓存包即可；
     *      2）、导入与第三方缓存整合的适配包；官方有；
     *      3）、mapper.xml中使用自定义缓存
     *      <cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
     * @throws IOException
     */
    @Test
    public void testFirstLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
            Employee emp1 = mapper.getEmpById(1);
            System.out.println(emp1);
            Employee emp2 = mapper.getEmpById(1);
            System.out.println(emp2);
            session.commit();
        } finally {
            session.close();
        }
    }

    @Test
    public void testSecondLevelCache() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        try {
            // 1. 
            EmployeeMapper mapper1 = session1.getMapper(EmployeeMapper.class);
            Employee emp01 = mapper1.getEmpById(1);
            System.out.println(emp01);
            session1.close();
            // 2. 第二次查询是从二级缓存中拿到的数据, 并没有发送新的sql
            EmployeeMapper mapper2 = session2.getMapper(EmployeeMapper.class);
            Employee emp02 = mapper2.getEmpById(1);
            System.out.println(emp02);
            session2.close();
        } finally {
        }
    }

}
