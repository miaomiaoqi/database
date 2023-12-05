package com.miaoqi.hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    public static final Configuration CONFIGURATION;
    public static final SessionFactory SESSION_FACTORY;

    static {
        CONFIGURATION = new Configuration().configure();
        SESSION_FACTORY = CONFIGURATION.buildSessionFactory();
    }

    public static Session openSession() {
        return SESSION_FACTORY.openSession();
    }

}
