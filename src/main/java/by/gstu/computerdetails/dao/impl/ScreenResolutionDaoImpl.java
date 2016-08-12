package by.gstu.computerdetails.dao.impl;

import by.gstu.computerdetails.config.HibernateUtil;
import by.gstu.computerdetails.dao.ScreenResolutionDao;
import by.gstu.computerdetails.entity.ScreenResolution;

import java.util.List;

public class ScreenResolutionDaoImpl extends GenericDaoImpl<ScreenResolution, Long> implements ScreenResolutionDao {

    @SuppressWarnings("unchecked")
    public List<ScreenResolution> findAll() {
        HibernateUtil.beginTransaction();
        List<ScreenResolution> screenResolutions = getSession().createQuery("from ScreenResolution").list();
        HibernateUtil.commitTransaction();

        return screenResolutions;
    }
}
