package com.miaoqi.hibernate.demo1;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * Hibernate的一级缓存的测试
 * @author miaoqi
 *
 */
public class HibernateDemo3 {

    // 证明一级缓存的存在
    @Test
    public void demo1() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        //        Customer customer1 = session.get(Customer.class, 1L); // 发送SQL语句
        //        Customer customer2 = session.get(Customer.class, 1L); // 不发送SQL语句
        //        System.out.println(customer1 == customer2);

        Customer customer = new Customer();
        customer.setCust_name("哈哈");
        Serializable id = session.save(customer);

        session.get(Customer.class, id);

        transaction.commit();
        session.close();
    }

    // 一级缓存的快照区
    @Test
    public void demo2() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, 1L); // 发送SQL语句查询, 同时放入到一级缓存中
        customer.setCust_name("汪峰");

        transaction.commit(); // commit时会用缓存区的对象和快照区进行比较, 如果有变化, 自动执行sql
        session.close();
    }
}
