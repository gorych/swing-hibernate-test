package by.gstu.computerdetails.dao;

import by.gstu.computerdetails.entity.Monitor;

import java.util.List;

public interface MonitorDao extends GenericDao<Monitor, Long> {

    List<Monitor> findAll();

}
