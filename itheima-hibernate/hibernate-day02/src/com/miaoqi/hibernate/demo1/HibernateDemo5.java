package com.miaoqi.hibernate.demo1;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * Hibernate的其他API
 * @author miaoqi
 *
 */
public class HibernateDemo5 {

    // Query
    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 通过Session获得Query接口
        // 简单查询
        //        String hql = "FROM Customer"; // 查询所有
        // 条件查询
        //        String hql = "FROM Customer where cust_name like ?"; // 模糊查询
        // 分页查询
        String hql = "FROM Customer";
        Query query = session.createQuery(hql);
        // 设置条件
        //        query.setParameter(0, "哈%");
        // 设置分页
        query.setFirstResult(0);
        query.setMaxResults(2);
        List list = query.list();
        System.out.println(list);

        transaction.commit();
    }

    // Criteria:QBC(Query By Criteria)
    @Test
    public void demo2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 通过Session获取Criteria对象
        //        Criteria criteria = session.createCriteria(Customer.class);
        //        List list = criteria.list();
        // 条件查询
        //        Criteria criteria = session.createCriteria(Customer.class);
        //        criteria.add(Restrictions.like("cust_name", "哈%"));
        //        criteria.add(Restrictions.like("cust_name", "哈", MatchMode.START));
        // 分页查询
        Criteria criteria = session.createCriteria(Customer.class);
        criteria.setFirstResult(0);
        criteria.setMaxResults(2);
        List list = criteria.list();
        System.out.println(list);

        transaction.commit();
    }

}
