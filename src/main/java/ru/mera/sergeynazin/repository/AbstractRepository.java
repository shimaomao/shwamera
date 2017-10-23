package ru.mera.sergeynazin.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mera.sergeynazin.repos.IRepository;

import javax.persistence.criteria.Predicate;
import java.util.List;

public abstract class AbstractRepository<C extends Class<C>> implements IRepository {

    private SessionFactory sessionFactory;

    private Class<C> clazz;

    public final void setClazz(final Class<C> clazz ) {
        this.clazz = clazz;
    }

    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public final SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public void deleteByIdIfExist(final C item, final Long id) {
        //Open Session
        final Session session = getSessionFactory().openSession();
        session.beginTransaction();
        final C entity = session.load(clazz, id);
        if (entity != null) {
            IRepository.super.deleteItem(entity);
        }
        session.getTransaction().commit();
        //Close session
        session.close();
    }

    @Override
    public List read(Predicate predicate) {
        return null;
    }

    public C findById(final Long id){
        //Open Session
        final Session session = getSessionFactory().openSession();

        session.load(clazz, id);

        //Close session
        session.close();
    }
}
