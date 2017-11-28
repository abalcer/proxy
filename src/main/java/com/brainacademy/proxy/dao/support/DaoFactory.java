package com.brainacademy.proxy.dao.support;

import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

public class DaoFactory {
    private final SessionFactory sessionFactory;

    public DaoFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <D, E> D createDaoForType(Class<D> dao, Class<E> entity) {
        return (D) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{dao},
                new DaoInvocationHandler<>(sessionFactory, entity));
    }
}
