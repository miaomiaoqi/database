package com.miaoqi.hibernate.demo1;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * 主键生成策略
 * @author miaoqi
 *
 */
public class HibernateDemo1 {

    // 保存一条记录
    // 演示increment
    @Test
    public void demo1() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("向西");
        session.save(customer);

        transaction.commit();
        session.close();
    }

    // 保存一条记录
    // 演示increment
    @Test
    public void demo2() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("向东");
        session.save(customer);

        transaction.commit();
        session.close();
    }

}
