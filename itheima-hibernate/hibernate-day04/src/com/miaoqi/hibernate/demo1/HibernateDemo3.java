package com.miaoqi.hibernate.demo1;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.domain.Customer;
import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * SQL查询
 * @author miaoqi
 *
 */
public class HibernateDemo3 {

    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //        SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM cst_customer");
        //        List<Object[]> list = sqlQuery.list();
        //        for (Object[] objects : list) {
        //            System.out.println(Arrays.toString(objects));
        //        }
        SQLQuery sqlQuery = session.createSQLQuery("SELECT * FROM cst_customer");
        sqlQuery.addEntity(Customer.class);
        List<Customer> list = sqlQuery.list();
        for (Customer customer : list) {
            System.out.println(customer);
        }

        transaction.commit();
    }

}
