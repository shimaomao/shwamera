package ru.mera.sergeynazin.repository;

import javax.persistence.criteria.Predicate;
import java.util.List;

public interface IMainRepository {
    <T> void addItem(T item);
    <T> void removeItem(T item);
    <T> void updateItem(T item);
    List query(Predicate predicate);
}
