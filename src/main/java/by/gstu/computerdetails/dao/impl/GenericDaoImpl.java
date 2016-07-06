package by.gstu.computerdetails.dao.impl;

import by.gstu.computerdetails.config.HibernateUtil;
import by.gstu.computerdetails.dao.GenericDao;
import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K> {

    protected Class<? extends E> daoType;

    public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        daoType = (Class<? extends E>) pt.getActualTypeArguments()[0];
    }

    protected Session getSession() {
        return HibernateUtil.getSession();
    }

    public void add(E entity) {
        HibernateUtil.beginTransaction();
        getSession().saveOrUpdate(entity);
        HibernateUtil.commitTransaction();
    }

    public E saveOrUpdate(E entity) {
        HibernateUtil.beginTransaction();
        getSession().saveOrUpdate(entity);
        HibernateUtil.commitTransaction();

        return entity;
    }

    public void remove(E entity) {
        HibernateUtil.beginTransaction();
        getSession().remove(entity);
        HibernateUtil.commitTransaction();
    }

    public E find(K key) {
        HibernateUtil.beginTransaction();
        E entity = getSession().get(daoType, key);
        HibernateUtil.commitTransaction();

        return entity;
    }
}
