package by.gstu.computerdetails.dao.impl;

import by.gstu.computerdetails.config.HibernateUtil;
import by.gstu.computerdetails.dao.MonitorDao;
import by.gstu.computerdetails.entity.Monitor;

import java.util.List;

public class MonitorDaoImpl extends GenericDaoImpl<Monitor, Long> implements MonitorDao {

    @SuppressWarnings("unchecked")
    public List<Monitor> findAll() {
        HibernateUtil.beginTransaction();
        List<Monitor> monitors = getSession().createQuery("from Monitor where isProto = false").list();
        HibernateUtil.commitTransaction();

        return monitors;
    }
}
