package ru.mera.sergeynazin.repository;

import java.util.Collection;
import java.util.List;

public interface Repository {

    // ADD
    <T> T add(T transientEntity);
    <T> List<T> addAll(Collection<T> transientEntities);

    // REMOVE
    <T> void remove(T persistentOrDetachedEntity);
    void removeAll();
    void removeAll(Collection<?> entities);

    // helpers
    long count();
    <T> boolean contains(T entity);
}
