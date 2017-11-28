package com.brainacademy.proxy;

import com.brainacademy.proxy.dao.support.DaoFactory;
import com.brainacademy.proxy.dao.UserDao;
import com.brainacademy.proxy.model.User;
import com.brainacademy.proxy.utils.HibernateUtil;
import org.hibernate.SessionFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        DaoFactory daoFactory = new DaoFactory(sessionFactory);

        UserDao userDao = daoFactory.createDaoForType(UserDao.class, User.class);

        List<User> users = userDao.findUsers("Vasya", "Address");
        System.out.println(users);


        sessionFactory.close();
    }
}
