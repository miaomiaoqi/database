package com.miaoqi.hibernate.demo2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.domain.LinkMan;
import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * many-to-one上的fetch和lazy的测试
 * @author miaoqi
 *
 */
public class HibernateDemo3 {

    /**
     * 默认值
     */
    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        LinkMan linkMan = session.get(LinkMan.class, 1L); // 发送1条查询联系人语句
        System.out.println(linkMan.getLkm_name());
        System.out.println(linkMan.getCustomer().getCust_name()); // 发送1条select语句查询联系人所关联的客户

        transaction.commit();
    }

    /**
     * fetch="select" lazy="proxy"
     */
    @Test
    public void demo2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        LinkMan linkMan = session.get(LinkMan.class, 1L); // 发送1条查询联系人语句
        System.out.println(linkMan.getLkm_name());
        System.out.println(linkMan.getCustomer().getCust_name()); // 发送1条select语句查询联系人所关联的客户

        transaction.commit();
    }

    /**
     * fetch="select" lazy="false"
     */
    @Test
    public void demo3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        LinkMan linkMan = session.get(LinkMan.class, 1L); // 发送1条查询联系人语句, 并且查询客户
        System.out.println(linkMan.getLkm_name());
        System.out.println(linkMan.getCustomer().getCust_name());

        transaction.commit();
    }

    /**
     * fetch="join" lazy=失效
     */
    @Test
    public void demo4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        LinkMan linkMan = session.get(LinkMan.class, 1L); // 发送1条迫切左外连接
        System.out.println(linkMan.getLkm_name());
        System.out.println(linkMan.getCustomer().getCust_name());

        transaction.commit();
    }

}
