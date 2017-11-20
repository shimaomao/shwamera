package ru.mera.sergeynazin.repository;

import org.hibernate.Session;
import org.hibernate.query.internal.AbstractProducedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings({"unchecked"})
public interface HibernateRepository extends JpaRepository, GenericRepository {


    default Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    @Override
    default <T> Serializable create(final T transientEntity) {
        return getSession().save(transientEntity);
    }
//
//    @Override
//    default <T> void delete(final T persistentOrDetachedEntity) {
//        getSession().delete(persistentOrDetachedEntity);
//    }
//
//    @Override
//    default <T> void update(final T detachedEntity) {
//        getSession().update(detachedEntity);
//    }

    /**
     * @param newStatefulEntityWithId new Stateful Entity With ID
     * @param <T> entity type
     * @return updated managed Entity
     */
    @SuppressWarnings({"unchecked"})
    @Override
    default <T> T mergeStateWithDbEntity(final T newStatefulEntityWithId) {
        return (T) getSession().merge(newStatefulEntityWithId);
    }

//    @Override
//    default <T> T getById(final Serializable id) {
//        return getSession().get(getClazz(), Objects.requireNonNull(id));
//    }


    @Override
    default <T> T add(final T transientEntity) {
        if(!contains(transientEntity)) {
            getSession().persist(transientEntity);
            return transientEntity;
        } else
            return (T) getSession().merge(transientEntity);
    }

    @Override
    default <T> List<T> addAll(final Collection<T> transientEntities) {
        throw new UnsupportedOperationException("Stub method! (for now...)");
    }

    @Override
    default <T> void remove(final T persistentOrDetachedEntity) {
        getSession().delete(persistentOrDetachedEntity);
        //if (!contains(persistentOrDetachedEntity)) throw new NotFoundException(Objects.toString(persistentOrDetachedEntity, "Can not toString type " + persistentOrDetachedEntity.getClass())+" object notFound to Delete it")
    }

    @Override
    default void removeAll() {
        throw new UnsupportedOperationException("Stub method! (for now...)");
    }

    @Override
    default void removeAll(final Collection<?> entities) {
        throw new UnsupportedOperationException("Stub method! (for now...)");
    }

    @Override
    default <T> List getByCriteriaQuery(final CriteriaQuery<T> criteriaQuery) {
        return getSession()
            .createQuery(Objects.requireNonNull(criteriaQuery))
            .getResultList();
    }

    @Override
    default <T> T getUniqueByCriteriaQuery(final CriteriaQuery<T> criteriaQuery) {
        return AbstractProducedQuery.uniqueElement(
            getSession().createQuery(Objects.requireNonNull(criteriaQuery)).getResultList());
    }

    @Override
    default <T> CriteriaQuery<T> myCriteriaQuery() {
        return getSession().getCriteriaBuilder().createQuery(getClazz());
    }

    @Override
    default CriteriaBuilder myCriteriaBuilder() {
        return getSession().getCriteriaBuilder();
    }

    @Override
    default long count() {
        final CriteriaBuilder qb = myCriteriaBuilder();
        final CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(getClazz())));
        //cq.where(/*could be ant condition*/);
        return getUniqueByCriteriaQuery(cq);
    }

    @Override
    default <T> boolean contains(final T entity) {
//        CriteriaBuilder cb = myCriteriaBuilder();
//        CriteriaQuery<T> query = cb.createQuery(getClazz());
//        Root<T> root = query.from(getClazz());
//        query.where(
//            cb.function(
//                "CONTAINS", Boolean.class,
//                //assuming 'id' is the property on the Person Java object that is mapped to the last_name column on the Person table.
//                root.<String>get("id"),
//                //Add a named parameter called containsCondition
//                cb.parameter(String.class, "containsCondition")));
//
//        TypedQuery<T> tq = getSession().createQuery(query);
//        tq.setParameter("containsCondition", "%näh%");
//        List<T> people = tq.getResultList();
        throw new UnsupportedOperationException("Stub method! (for now...)");
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.ofNullable(Managed_instance)
     */
    @SuppressWarnings({"unchecked"})
    @Override
    default <T> Optional<T> getOptionalById(final Serializable id) {
        return (Optional<T>) getSession().byId(getClazz()).loadOptional(Objects.requireNonNull(id));
        // Optional.ofNullable(getSession().get(getClazz(),Objects.requireNonNull(id)));
    }
}
