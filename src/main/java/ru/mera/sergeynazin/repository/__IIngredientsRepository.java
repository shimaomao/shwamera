package ru.mera.sergeynazin.repository;

import ru.mera.sergeynazin.model.Ingredient;

import javax.persistence.criteria.Predicate;
import java.util.List;

public interface __IIngredientsRepository extends IMainRepository<Ingredient> {
    @Override
    default void addItem(Ingredient item) {

    }

    @Override
    void removeItem(Ingredient item);

    @Override
    void updateItem(Ingredient item);

    @Override
    List query(Predicate predicate);
}
