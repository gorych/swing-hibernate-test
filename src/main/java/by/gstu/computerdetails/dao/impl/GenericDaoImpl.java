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

    protected Session getSession(){
        return  HibernateUtil.getSession();
    }

    public void add(E entity) {
        getSession().saveOrUpdate(entity);
    }

    public E saveOrUpdate(E entity) {
        getSession().saveOrUpdate(entity);
        return entity;
    }

    public void remove(E entity) {
        getSession().remove(entity);
    }

    public E find(K key) {
        return getSession().get(daoType, key);
    }
}
