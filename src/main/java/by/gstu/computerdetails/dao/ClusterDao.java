package by.gstu.computerdetails.dao;

import by.gstu.computerdetails.entity.Cluster;

import java.util.List;

public interface ClusterDao extends GenericDao<Cluster, Long> {

    List<Cluster> findAll();

}
