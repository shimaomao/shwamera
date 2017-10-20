package ru.mera.sergeynazin.repos;

import org.hibernate.Session;

public interface IRepository extends ICRUDRepository {

    Session getSession();

    @Override
    default <T> void createItem(T item) {
        getSession().save(item);
    }

    @Override
    default <T> void deleteItem(T item) {
        getSession().delete(item);
    }

    @Override
    default <T> void updateItem(T item) {
        getSession().update(item);
    }
}
