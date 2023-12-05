package com.miaoqi.hibernate.demo1;

import com.miaoqi.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.io.Serializable;

/**
 * Hibernate的持久化类的三种状态
 * @author miaoqi
 *
 */
public class HibernateDemo2 {

    // 三种状态的区分
    @Test
    public void demo1() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer(); // 瞬时状态对象: 没有唯一标识OID, 没有被session管理
        customer.setCust_name("苗骐");

        Serializable id = session.save(customer); // 持久态对象: 有唯一标识OID, 被session管理

        session.get(Customer.class, id);

        transaction.commit();
        session.close();

        System.out.println(customer.getCust_name()); // 脱管态对象: 有唯一标识OID, 没有被session管理
    }

    // 持久态对象自动更新数据库
    @Test
    public void demo2() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        // 获得持久态对象
        Customer customer = session.get(Customer.class, 1L);
        customer.setCust_name("向东");

        transaction.commit();
        session.close();
    }

}
