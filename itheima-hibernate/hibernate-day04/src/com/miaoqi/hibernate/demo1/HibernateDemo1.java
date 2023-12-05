package com.miaoqi.hibernate.demo1;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.domain.Customer;
import com.miaoqi.hibernate.domain.LinkMan;
import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * HQL的查询方式的测试类
 * @author miaoqi
 *
 */
public class HibernateDemo1 {

    /**
     * 初始化数据
     */
    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 创建一个客户
        Customer customer = new Customer();
        customer.setCust_name("张六");

        for (int i = 1; i <= 10; i++) {
            LinkMan linkMan = new LinkMan();
            linkMan.setLkm_name("如花" + i);
            linkMan.setCustomer(customer);

            customer.getLinkMans().add(linkMan);
            session.save(linkMan);
        }
        session.save(customer);

        transaction.commit();
    }

    /**
     * HQL简单查询
     */
    @Test
    public void demo2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 简单查询
        Query query = session.createQuery("FROM Customer");
        List<Customer> list = query.list();
        // sql中支持*号写法: select * from cst_customer; 但是HQL不支持*号写法
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 别名查询
     */
    @Test
    public void demo3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 别名查询
        //        Query query = session.createQuery("FROM Customer c");
        //        List<Customer> list = query.list();
        Query query = session.createQuery("SELECT c FROM Customer c");
        List<Customer> list = query.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 排序查询
     */
    @Test
    public void demo4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 排序查询
        List<Customer> list = session.createQuery("FROM Customer order by cust_id DESC").list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 条件查询
     */
    @Test
    public void demo5() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 条件查询
        // 1. 按位置查询: 根据参数的位置绑定
        // 一个条件
        //        Query query = session.createQuery("FROM Customer WHERE cust_name = ?");
        //        query.setParameter(0, "李四");
        //        List<Customer> list = query.list();
        // 多个条件
        //        Query query = session.createQuery("FROM Customer WHERE cust_source = ? and cust_name like ?");
        //        query.setParameter(0, "小广告");
        //        query.setParameter(1, "李%");
        //        List<Customer> list = query.list();
        // 2. 按名称绑定
        Query query = session.createQuery("FROM Customer WHERE cust_source = :aaa AND cust_name LIKE :bbb");
        // 设置参数
        query.setParameter("aaa", "朋友推荐");
        query.setParameter("bbb", "苗%");
        List<Customer> list = query.list();

        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 投影查询
     */
    @Test
    public void demo6() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 投影查询
        // 单个属性
        //        List<Object> list = session.createQuery("SELECT c.cust_name FROM Customer c").list();
        //
        //        for (Object object : list) {
        //            System.out.println(object);
        //        }
        // 多个属性
        //        List<Object[]> list = session.createQuery("SELECT c.cust_name, c.cust_source FROM Customer c").list();
        //        for (Object[] object : list) {
        //            System.out.println(Arrays.toString(object));
        //        }

        // 查询多个属性, 封装到对象中, 需要提供构造函数
        List<Customer> list = session.createQuery("select new Customer(cust_name, cust_source) from Customer").list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 分页查询
     */
    @Test
    public void demo7() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM LinkMan");
        query.setFirstResult(10);
        query.setMaxResults(10);
        List<LinkMan> list = query.list();
        for (LinkMan linkMan : list) {
            System.out.println(linkMan);
        }

        transaction.commit();
    }

    /**
     * 分组统计查询
     */
    @Test
    public void demo8() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 聚合函数的使用, count(), max(), min(), avg(), sum()
        Object result = session.createQuery("select count(*) from Customer").uniqueResult();
        System.out.println(result);
        // 分组统计
        List<Object[]> list = session
                .createQuery("select c.cust_source, count(*) from Customer c GROUP BY cust_source HAVING count(*) >= 2")
                .list();
        for (Object[] objects : list) {
            System.out.println(Arrays.toString(objects));
        }
        transaction.commit();
    }

    /**
     * 多表查询
     */
    @Test
    public void demo9() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // SQL: SELECT * FROM cst_customer c INNER JOIN cst_linkman l ON c.cust_id = l.lkm_cust_id;
        // HQL: 内连接
        //        List<Object[]> list = session.createQuery("FROM Customer c INNER JOIN c.linkMans").list();
        //        for (Object[] objects : list) {
        //            System.out.println(Arrays.toString(objects));
        //        }
        // HQL: 迫切内连接, 在普通的内连接inner join后添加一个关键字fetch
        List<Customer> list = session.createQuery("SELECT DISTINCT c FROM Customer c INNER JOIN FETCH c.linkMans")
                .list();

        transaction.commit();
    }

}
