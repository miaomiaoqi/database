package com.miaoqi.hibernate.demo1;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.miaoqi.hibernate.domain.Customer;
import com.miaoqi.hibernate.domain.LinkMan;
import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * 测试QBC(Query By Criteria)查询
 * @author miaoqi
 *
 */
public class HibernateDemo2 {

    /**
     * 简单查询
     */
    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 获取Criteria
        Criteria criteria = session.createCriteria(Customer.class);
        List<Customer> list = criteria.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 排序查询
     */
    @Test
    public void demo2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 排序查询
        Criteria criteria = session.createCriteria(Customer.class);
        //        criteria.addOrder(Order.asc("cust_id"));
        criteria.addOrder(Order.desc("cust_id"));
        List<Customer> list = criteria.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 排序查询
     */
    @Test
    public void demo3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 分页查询
        Criteria criteria = session.createCriteria(LinkMan.class);
        criteria.setFirstResult(0);
        criteria.setMaxResults(10);
        List<LinkMan> list = criteria.list();
        for (LinkMan linkMan : list) {
            System.out.println(linkMan);
        }

        transaction.commit();
    }

    /**
     * 条件查询
     */
    @Test
    public void demo4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 条件查询
        Criteria criteria = session.createCriteria(Customer.class);
        // 设置条件
        /**
         * =    eq
         * >    gt
         * >=   ge
         * <    lt
         * <=   le
         * <>   ne
         * like
         * in
         * and
         * or
         */
        criteria.add(Restrictions.eq("cust_source", "小广告"));
        criteria.add(Restrictions.like("cust_name", "刘%"));
        List<Customer> list = criteria.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

    /**
     * 统计查询
     */
    @Test
    public void demo5() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 统计查询
        Criteria criteria = session.createCriteria(Customer.class);
        /**
         * add              : 普通的条件, where条件后面
         * addOrder         : 排序
         * addProjection    : 聚合函数, group by, having
         */
        criteria.setProjection(Projections.rowCount());
        Long num = (Long) criteria.uniqueResult();
        System.out.println(num);

        transaction.commit();
    }

    /**
     * 离线条件查询
     */
    @Test
    public void demo6() {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
        detachedCriteria.add(Restrictions.like("cust_name", "李%"));

        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Criteria criteria = detachedCriteria.getExecutableCriteria(session);
        List<Customer> list = criteria.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

}
