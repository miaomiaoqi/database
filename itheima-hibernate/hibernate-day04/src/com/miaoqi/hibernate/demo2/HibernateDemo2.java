package com.miaoqi.hibernate.demo2;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.domain.Customer;
import com.miaoqi.hibernate.domain.LinkMan;
import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * 在<set>上的fetch和lazy
 * @author miaoqi
 *
 */
public class HibernateDemo2 {

    /**
     * 默认情况
     */
    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 查询1号客户
        Customer customer = session.get(Customer.class, 1L); // 发送1条查询客户的SQL
        System.out.println(customer.getCust_name());
        // 查看1号客户的每个联系人的信息
        for (LinkMan linkMan : customer.getLinkMans()) {
            System.out.println(linkMan.getLkm_name());
        }

        transaction.commit();
    }

    /**
     * 设置fetch="select" lazy="true"
     */
    @Test
    public void demo2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 查询1号客户
        Customer customer = session.get(Customer.class, 1L); // 发送1条查询客户的SQL
        System.out.println(customer.getCust_name());
        // 查看1号客户的每个联系人的信息
        for (LinkMan linkMan : customer.getLinkMans()) { // 发送1条根据客户id查询联系人的SQL
            System.out.println(linkMan.getLkm_name());
        }

        transaction.commit();
    }

    /**
     * 设置fetch="select" lazy="false"
     */
    @Test
    public void demo3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 查询1号客户
        Customer customer = session.get(Customer.class, 1L); // 发送2条SQL: 查询客户, 查询客户的联系人
        System.out.println(customer.getCust_name());
        // 查看1号客户的每个联系人的信息
        //        for (LinkMan linkMan : customer.getLinkMans()) {
        //            System.out.println(linkMan.getLkm_name());
        //        }
        System.out.println(customer.getLinkMans().size());

        transaction.commit();
    }

    /**
     * 设置fetch="select" lazy="extra"
     */
    @Test
    public void demo4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 查询1号客户
        Customer customer = session.get(Customer.class, 1L); // 发送1条查询客户的SQL语句
        System.out.println(customer.getCust_name());

        System.out.println(customer.getLinkMans().size());

        transaction.commit();
    }

    /**
     * 设置fetch="join" lazy=失效
     */
    @Test
    public void demo5() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 查询1号客户
        Customer customer = session.get(Customer.class, 1L); // 发送1条查询客户的SQL语句
        System.out.println(customer.getCust_name());

        System.out.println(customer.getLinkMans().size());

        transaction.commit();
    }

    /**
     * 设置fetch="subselect" lazy="true"
     */
    @SuppressWarnings("unchecked")
    @Test
    public void demo6() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 查询1号客户
        List<Customer> list = session.createQuery("FROM Customer").list();
        for (Customer customer : list) {
            System.out.println(customer.getCust_name());
            System.out.println(customer.getLinkMans().size());// 发送1条子查询
        }

        transaction.commit();
    }

    /**
     * 设置fetch="subselect" lazy="false"
     */
    @SuppressWarnings("unchecked")
    @Test
    public void demo7() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 查询1号客户
        List<Customer> list = session.createQuery("FROM Customer").list();// 发送1条子查询
        for (Customer customer : list) {
            System.out.println(customer.getCust_name());
            System.out.println(customer.getLinkMans().size());
        }

        transaction.commit();
    }

}
