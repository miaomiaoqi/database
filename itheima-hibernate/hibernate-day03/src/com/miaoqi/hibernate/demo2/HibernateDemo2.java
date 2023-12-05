package com.miaoqi.hibernate.demo2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.miaoqi.hibernate.domain.Role;
import com.miaoqi.hibernate.domain.User;
import com.miaoqi.hibernate.utils.HibernateUtils;

/**
 * Hibernate多对多映射
 * @author miaoqi
 *
 */
public class HibernateDemo2 {

    /**
     * 保存多条记录: 保存多个用户和角色
     */
    @Test
    public void demo1() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 创建2个用户
        User user1 = new User();
        user1.setUser_name("aaa");
        User user2 = new User();
        user2.setUser_name("bbb");

        // 创建3个角色
        Role role1 = new Role();
        role1.setRole_name("研发部");
        Role role2 = new Role();
        role2.setRole_name("市场部");
        Role role3 = new Role();
        role3.setRole_name("公关部");

        // 设置双向关联关系
        user1.getRoles().add(role1);
        user1.getRoles().add(role2);
        user2.getRoles().add(role2);
        user2.getRoles().add(role3);

        role1.getUsers().add(user1);
        role2.getUsers().add(user1);
        role2.getUsers().add(user2);
        role3.getUsers().add(user2);

        // 保存操作: 多对多建立了双向关联关系, 必有有一方放弃
        // 一般是被动方放弃外键维护全
        session.save(user1);
        session.save(user2);
        session.save(role1);
        session.save(role2);
        session.save(role3);

        transaction.commit();
    }

    /**
     * 多对多操作:
     *      只保存一边是否可以? 不可以, 瞬时对象异常
     */
    @Test
    public void demo2() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 创建2个用户
        User user1 = new User();
        user1.setUser_name("aaa");

        // 创建3个角色
        Role role1 = new Role();
        role1.setRole_name("研发部");

        // 设置双向关联关系
        user1.getRoles().add(role1);
        //        role1.getUsers().add(user1); 

        // 只保存用户
        session.save(user1);
        //        session.save(role1);

        transaction.commit();
    }

    /**
     * 多对多级联保存
     *      保存用户, 级联保存角色
     */
    @Test
    public void demo3() {
        Session session = HibernateUtils.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        // 创建2个用户
        User user1 = new User();
        user1.setUser_name("aaa");

        // 创建3个角色
        Role role1 = new Role();
        role1.setRole_name("研发部");

        // 设置关联关系
        user1.getRoles().add(role1);

        // 只保存用户
        session.save(user1);

        transaction.commit();
    }

}
