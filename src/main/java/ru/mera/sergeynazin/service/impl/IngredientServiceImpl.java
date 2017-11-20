package ru.mera.sergeynazin.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.controller.advice.CreatingAlreadyExistentException;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.IngredientService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Transactional(readOnly = true)
public class IngredientServiceImpl implements IngredientService {

    private JpaRepository repository;

    public void setRepository(final JpaRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Ingredient> getAll() {
        final CriteriaQuery<Ingredient> criteriaQuery = repository.myCriteriaQuery();
        final Root<Ingredient> root = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(root);
        return repository.getByCriteriaQuery(criteriaQuery);
    }

    @Override
    public Optional<Ingredient> optionalIsExist(final String name) {
        final CriteriaBuilder criteriaBuilder = repository.myCriteriaBuilder();
        final CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        final Root<Ingredient> ingredientRoot = criteriaQuery.from(Ingredient.class);
        criteriaQuery.select(ingredientRoot).where(criteriaBuilder.equal(ingredientRoot.get("name"), name));

        return Optional.ofNullable(repository.getUniqueByCriteriaQuery(criteriaQuery));
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.ofNullable(Ingredient_managed_instance)
     */
    @Override
    public Optional<Ingredient> optionalIsExist(final Long id) {
        return repository.getOptionalById(id);
            // Optional.ofNullable(repository.get(id));
    }

    @Override
    public Ingredient getOrThrow(final Long id) {
        return (Ingredient) repository.getOptionalById(id)
            .orElseThrow(() -> NotFoundException.throwNew(id));
    }

    @Transactional
    @Override
    public Long saveOrThrowExist(final Ingredient transient_) {
        optionalIsExist(transient_.getName())
            .map(existentDetached -> CreatingAlreadyExistentException.throwNew(transient_.getName(), existentDetached));
        return (Long) repository.create(transient_);
    }

    @Transactional
    @Override
    public Ingredient mergeOrThrow(final Ingredient newDetached) {
        return repository.getOptionalById(newDetached.getId())
            .map(oldPersistent -> repository.mergeStateWithDbEntity(newDetached))
            .orElseThrow(() -> NotFoundException.throwNew(newDetached.getId()));
    }

    @Transactional
    @Override
    public Ingredient deleteOrThrow(final Long id) {
        return (Ingredient) repository.getOptionalById(id)
            .map(ingredient -> {
                repository.remove(ingredient);
                return ingredient;
            }).orElseThrow(() -> NotFoundException.throwNew(id));
    }

    @Override
    public boolean validateExistsOrThrow(final Ingredient... ingredients) {
        Stream.of(ingredients)
            .parallel()
            .filter(ingredient -> !optionalIsExist(ingredient.getName()).isPresent())
            .map(Ingredient::getName)
            .reduce((n1, n2) -> n1 +", "+ n2)
            .ifPresent(NotFoundException::throwNew);
        return true;
    }
}
