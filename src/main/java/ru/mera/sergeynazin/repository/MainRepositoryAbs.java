package ru.mera.sergeynazin.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mera.sergeynazin.model.Ingredient;

import javax.persistence.criteria.Predicate;
import java.util.List;

public abstract class MainRepositoryAbs implements IMainRepository {

    private SessionFactory sessionFactory;


    @Override
    public <T> void addItem(T item) {

    }

    @Override
    public <T> void removeItem(T item) {
        //Open Session
        Session session = sessionFactory.openSession();

        Ingredient ingredient = session.load();
        if (null != ingredient) {
            session.delete(ingredient);
        }

        //Close session
        session.close();
    }

    @Override
    public <T> void updateItem(T item) {

    }

    @Override
    public List query(Predicate predicate) {
        return null;
    }
}
