package com.miaoqi.hibernate.demo1;

import com.miaoqi.hibernate.utils.HibernateUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/*
 * Hibernate工具类的测试
 */

public class HibernateDemo2 {

    // 保存操作
    @Test
    public void demo1() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        Customer c = new Customer();
        c.setCust_name("aaa");
        Serializable id = session.save(c);
        System.out.println(id);
        transaction.commit();
        session.close();
    }

    // 查询操作
    // *** get方法和load方法的区别
    @Test
    public void demo2() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        /**
         * get方法
         *  * 采用的是立即加载, 执行到这行代码的时候, 就会马上发送SQL语句去查询
         *  * 查询后返回的是真实对象本身
         *  * 查询一个找不到的对象时, 返回null
         *
         * load方法
         *  * 采用的是延迟加载(lazy加载), 执行到这行代码的时候, 不会发送SQL语句, 当真正去使用这个对象时才会发送SQL语句
         *  * 查询后方法的是代理对象   javassist-3.18.1-GA.jar利用javassist技术产生的代理
         *  * 查询一个找不到的对象时, 返回ObjectNotFoundException
         */

        // 使用get方法查询
        //        Customer customer = session.get(Customer.class, 1L);
        //        System.out.println(customer);

        Customer customer = session.load(Customer.class, 2222L);
        System.out.println(customer);

        transaction.commit();
        session.close();
    }

    // 修改操作
    @Test
    public void demo3() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        // 直接创建对象, 进行修改
        //        Customer customer = new Customer();
        //        customer.setCust_id(1L);
        //        customer.setCust_name("hahaha");
        //        session.update(customer);

        // 先查询再修改(推荐)
        Customer customer = session.get(Customer.class, 1L);
        customer.setCust_name("agdasgdsg");
        session.update(customer);

        transaction.commit();
        session.close();
    }

    // 删除操作
    @Test
    public void demo4() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        // 直接创建对象, 删除
        //        Customer customer = new Customer();
        //        customer.setCust_id(1L);
        //        session.delete(customer);

        // 先查询再删除(推荐)--级联删除
        Customer customer = session.get(Customer.class, 2L);
        session.delete(customer);

        transaction.commit();
        session.close();
    }

    // 保存或更新
    @Test
    public void demo5() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        //        Customer customer = new Customer();
        //        customer.setCust_name("汪峰");
        //        session.saveOrUpdate(customer);
        Customer customer = new Customer();
        customer.setCust_id(6L);
        customer.setCust_name("旺旺");
        session.saveOrUpdate(customer);

        transaction.commit();
        session.close();
    }

    // 查询所有
    @Test
    public void demo6() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        // 接收HQL: Hibernate Query Language 面向对象查询语言
        //        Query query = session.createQuery("FROM Customer");
        //        List<Customer> list = query.list();
        //        for (Customer customer : list) {
        //            System.out.println(customer);
        //        }

        // 接收SQL:
        SQLQuery query = session.createSQLQuery("select * from cst_customer");
        List<Object[]> list = query.list();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }

        transaction.commit();
        session.close();
    }

}
