package by.gstu.computerdetails.dao;

import by.gstu.computerdetails.entity.ScreenResolution;

import java.util.List;

public interface ScreenResolutionDao extends GenericDao<ScreenResolution, Long> {

    List<ScreenResolution> findAll();

}
