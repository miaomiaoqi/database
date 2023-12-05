package com.miaoqi.hibernate.demo2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.domain.Customer;
import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * Hibernate延迟加载
 * @author miaoqi
 *
 */
public class HibernateDemo1 {

    /**
     * 类级别延迟加载
     *  在class标签上配置lazy
     */
    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = session.get(Customer.class, 1L);
        System.out.println(customer);

        transaction.commit();
    }
}
