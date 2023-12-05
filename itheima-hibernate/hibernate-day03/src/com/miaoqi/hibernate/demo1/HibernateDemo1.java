package com.miaoqi.hibernate.demo1;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.domain.Customer;
import com.miaoqi.hibernate.domain.LinkMan;
import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * 一对多测试类
 * @author miaoqi
 *
 */
public class HibernateDemo1 {

    // 保存2个客户和3个联系人, 并且建立好关系
    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 创建2个客户
        Customer customer1 = new Customer();
        customer1.setCust_name("王东");
        Customer customer2 = new Customer();
        customer2.setCust_name("王西");

        // 创建3个联系人
        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name("凤姐");
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name("如花");
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name("旺财");

        // 设置关系:
        linkMan1.setCustomer(customer1);
        linkMan2.setCustomer(customer1);
        linkMan3.setCustomer(customer2);
        customer1.getLinkMans().add(linkMan1);
        customer1.getLinkMans().add(linkMan2);
        customer2.getLinkMans().add(linkMan3);

        // 保存数据:
        session.save(customer1);
        session.save(customer2);
        session.save(linkMan1);
        session.save(linkMan2);
        session.save(linkMan3);
        transaction.commit();
    }

    // 一对多关系只保存一边是否可以
    @Test
    public void demo2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("王东");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("如花");

        // 设置关系:
        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        // 只保存一边是否可以: 不可以, 报瞬时态对象异常: 持久态对象关联了一个瞬时态对象
        //        session.save(customer);
        session.save(linkMan);
        transaction.commit();
    }

    // 级联保存或更新操作(通过配置)
    // 保存客户, 级联联系人, 操作的主题对象时客户对象, 需要在Customer.hbm.xml进行配置
    @Test
    public void demo3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("王东");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("如花");

        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        session.save(customer);

        transaction.commit();
    }

    // 级联保存或更新操作(通过配置)
    // 保存联系人, 级联客户, 操作的主题对象时客户对象, 需要在Customer.hbm.xml进行配置
    @Test
    public void demo4() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("李四");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkm_name("凤姐");

        customer.getLinkMans().add(linkMan);
        linkMan.setCustomer(customer);

        session.save(linkMan);

        transaction.commit();
    }

    // 测试对象导航
    // 前提: 一对多的双方都设置cascade="save-update"
    @Test
    public void demo5() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer();
        customer.setCust_name("李四2");

        LinkMan linkMan1 = new LinkMan();
        linkMan1.setLkm_name("如花");
        LinkMan linkMan2 = new LinkMan();
        linkMan2.setLkm_name("芙蓉");
        LinkMan linkMan3 = new LinkMan();
        linkMan3.setLkm_name("凤姐");

        linkMan1.setCustomer(customer);
        customer.getLinkMans().add(linkMan2);
        customer.getLinkMans().add(linkMan3);

        // 双方设置了cascade
        //        session.save(linkMan1); // 发送几条insert语句, 4条insert
        //        session.save(customer); // 发送几条insert语句, 3条insert
        session.save(linkMan2); // 发送几条insert, 1条insert

        transaction.commit();
    }

    // 级联删除:
    //      删除客户: 级联删除联系人, 删除的主体是客户, 需要在Customer.hbm.xml文件中配置
    @Test
    public void demo6() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //        Customer customer = new Customer();
        //        customer.setCust_name("李四2");
        //
        //        LinkMan linkMan1 = new LinkMan();
        //        linkMan1.setLkm_name("如花");
        //        LinkMan linkMan2 = new LinkMan();
        //        linkMan2.setLkm_name("芙蓉");
        //        LinkMan linkMan3 = new LinkMan();
        //        linkMan3.setLkm_name("凤姐");
        //
        //        linkMan1.setCustomer(customer);
        //        customer.getLinkMans().add(linkMan2);
        //        customer.getLinkMans().add(linkMan3);
        //
        //        // 双方设置了cascade
        //        session.save(linkMan1);

        // 没有设置级联删除, 默认情况: 修改了联系人的外键, 删除客户
        //        Customer customer = session.get(Customer.class, 3L);
        //        session.delete(customer);

        // 删除客户, 同时删除联系人
        Customer customer = session.get(Customer.class, 3L);
        session.delete(customer);

        // 删除客户, 同时删除联系人


        transaction.commit();
    }

    // 将联系人的所属客户更改
    @Test
    public void demo8() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 查询4号联系人
        LinkMan linkMan = session.get(LinkMan.class, 6L);
        // 查询5号客户
        Customer customer = session.get(Customer.class, 5L);
        linkMan.setCustomer(customer);
        customer.getLinkMans().add(linkMan);

        transaction.commit();
    }

}
