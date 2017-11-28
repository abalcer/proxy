package com.brainacademy.proxy.dao.support;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class DaoInvocationHandler<T> implements InvocationHandler {
    private static final String SELECT_BY_PARAM_SQL = "from %s where %s=?";
    private static final String SELECT_ALL_SQL = "from %s";

    private final SessionFactory sessionFactory;
    private final Class<T> entityType;

    public DaoInvocationHandler(SessionFactory sessionFactory, Class<T> entityType) {
        this.sessionFactory = sessionFactory;
        this.entityType = entityType;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();

        for (Annotation annotation : method.getDeclaredAnnotations()) {
            if (annotation instanceof com.brainacademy.proxy.dao.annotaion.Query) {
                return invokeExecuteQuery(((com.brainacademy.proxy.dao.annotaion.Query) annotation).value(), args);
            }
        }

        if (name.startsWith("findBy")) {
            String column = Character.toLowerCase(name.charAt(6)) + name.substring(7);
            Object param = args[0];
            return invokeFindByParam(column, param);
        } else if (name.equals("findAll")) {
            return invokeFindAll();
        }

        return null;
    }

    private List<T> invokeExecuteQuery(String sql, Object[] params) {
        try (Session session = sessionFactory.openSession()) {
            Query<T> query = session.createQuery(sql, entityType);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
            return query.getResultList();
        }
    }

    private List<T> invokeFindByParam(String column, Object param) {
        String sql = String.format(SELECT_BY_PARAM_SQL, entityType.getSimpleName(), column);
        try (Session session = sessionFactory.openSession()) {
            Query<T> query = session.createQuery(sql, entityType)
                    .setParameter(0, param);
            return query.getResultList();
        }
    }

    private List<T> invokeFindAll() {
        String sql = String.format(SELECT_ALL_SQL, entityType.getSimpleName());
        try (Session session = sessionFactory.openSession()) {
            Query<T> query = session.createQuery(sql, entityType);
            return query.getResultList();
        }
    }
}
