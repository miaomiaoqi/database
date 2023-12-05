package com.miaoqi.hibernate.demo1;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * 测试当前线程绑定的session
 * @author miaoqi
 *
 */
public class HibernateDemo4 {

    // 证明一级缓存的存在
    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("往西");
        session.save(customer);

        transaction.commit();
    }

}
