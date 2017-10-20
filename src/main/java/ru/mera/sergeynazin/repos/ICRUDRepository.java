package ru.mera.sergeynazin.repos;

import javax.persistence.criteria.Predicate;
import java.util.List;

public interface ICRUDRepository {
    <T> void createItem(T item);
    <T> void deleteItem(T item);
    <T> void updateItem(T item);
    List read(Predicate predicate);
}
