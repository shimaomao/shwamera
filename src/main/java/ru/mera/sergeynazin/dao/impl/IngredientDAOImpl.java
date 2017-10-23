package ru.mera.sergeynazin.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.mera.sergeynazin.model.Ingredient;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Set;
import java.util.stream.Collectors;

public class IngredientDAOImpl implements IngredientDAO {

    private SessionFactory sessionFactory;

    public IngredientDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    // TODO: Static ?????
    @Override
    public Set<Ingredient> getAllIngredients() {

        //Open Session
        Session session = sessionFactory.openSession();

        //Get Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();

        //Create Criteria
        CriteriaQuery<Ingredient> criteria = builder.createQuery(Ingredient.class);
        Root<Ingredient> ingredientRoot = criteria.from(Ingredient.class);
        criteria.select(ingredientRoot);

        //Use criteria to query with session to fetch all ingredients
        final Set<Ingredient> ingredients = session
            .createQuery(criteria)
            .getResultStream()
            .collect(Collectors.toSet());

        //Close session
        session.close();
        return ingredients;
    }


    @Override
    public Ingredient getIngredient(final Long id) {

        //Open Session
        Session session = sessionFactory.openSession();

        //Get Criteria Builder
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        //Create Criteria
        CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        Root<Ingredient> ingredientRoot = criteriaQuery.from(Ingredient.class);

        criteriaQuery.select(ingredientRoot);
        criteriaQuery.where(criteriaBuilder.equal(ingredientRoot.get("id"), id));

        final Ingredient ingredient = session
            .createQuery(criteriaQuery)
            .getSingleResult();

        //Close session
        session.close();

        return ingredient;
    }

    public void addIngredient(Ingredient ingredient) {
        //Open Session
        Session session = sessionFactory.openSession();

        session.save(ingredient);

        //Close session
        session.close();
    }


    public void updateIngredient(Ingredient ingredient) {
        //Open Session
        Session session = sessionFactory.openSession();

        session.update(ingredient);

        //Close session
        session.close();
    }


    public void deleteIngredient(Long id) {

        //Open Session
        Session session = sessionFactory.openSession();

        Ingredient ingredient = session.load(Ingredient.class, id);
        if (null != ingredient) {
            session.delete(ingredient);
        }

        //Close session
        session.close();
    }

}
