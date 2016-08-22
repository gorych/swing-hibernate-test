package by.gstu.computerdetails.dao.impl;

import by.gstu.computerdetails.config.HibernateUtil;
import by.gstu.computerdetails.dao.ClusterDao;
import by.gstu.computerdetails.dao.MonitorDao;
import by.gstu.computerdetails.entity.Cluster;
import by.gstu.computerdetails.entity.Monitor;

import java.util.List;

public class ClusterDaoImpl extends GenericDaoImpl<Cluster, Long> implements ClusterDao {

    @SuppressWarnings("unchecked")
    public List<Cluster> findAll() {
        HibernateUtil.beginTransaction();
        List<Cluster> clusters = getSession().createQuery("from Cluster").list();
        HibernateUtil.commitTransaction();

        return clusters;
    }
}
