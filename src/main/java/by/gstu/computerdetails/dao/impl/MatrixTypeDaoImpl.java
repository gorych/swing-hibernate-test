package by.gstu.computerdetails.dao.impl;

import by.gstu.computerdetails.config.HibernateUtil;
import by.gstu.computerdetails.dao.MatrixTypeDao;
import by.gstu.computerdetails.dao.ScreenResolutionDao;
import by.gstu.computerdetails.entity.MatrixType;
import by.gstu.computerdetails.entity.ScreenResolution;

import java.util.List;

public class MatrixTypeDaoImpl extends GenericDaoImpl<MatrixType, Long> implements MatrixTypeDao {

    @SuppressWarnings("unchecked")
    public List<MatrixType> findAll() {
        HibernateUtil.beginTransaction();
        List<MatrixType> matrixTypes = getSession().createQuery("from MatrixType").list();
        HibernateUtil.commitTransaction();

        return matrixTypes;
    }
}
