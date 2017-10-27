package ru.mera.sergeynazin.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mera.sergeynazin.repository.IRepository;

/**
 * @author soberich
 */

public class IRepositoryImpl implements IRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SessionFactory sessionFactory;

    private Class<?> clazz;

    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // TODO: 10/20/17 Annotations more suitable when injecting GENERICS (...I guess)
    /**
     * for educational purposes MAY set through constructor but then extending would become non-trivial
     */
    public <T> void setClazz(T entity) {
        this.clazz = entity.getClass();
    }

    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Class<T> getClazz() {
        return (Class<T>) clazz;
    }
}
