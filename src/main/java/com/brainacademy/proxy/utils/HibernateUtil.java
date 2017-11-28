package com.brainacademy.proxy.utils;

import com.brainacademy.proxy.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties dbProperties = new Properties();
            try {
                dbProperties.load(HibernateUtil.class.getClassLoader().getResourceAsStream("db.properties"));
            } catch (IOException e) {
                throw new RuntimeException("Could not to load database properties", e);
            }

            sessionFactory = new Configuration()
                    .addProperties(dbProperties)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

        }
        return sessionFactory;
    }
}
