package by.gstu.computerdetails.dao;

import by.gstu.computerdetails.entity.MatrixType;

import java.util.List;

public interface MatrixTypeDao extends GenericDao<MatrixType, Long> {

    List<MatrixType> findAll();

}
