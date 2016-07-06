package by.gstu.computerdetails.dao;

public interface GenericDao<E, K> {

    void add(E entity);

    E saveOrUpdate(E entity);

    void remove(E entity);

    E find(K key);

}
