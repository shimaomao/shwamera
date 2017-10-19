package ru.mera.sergeynazin.dao;

import ru.mera.sergeynazin.model.Ingredient;

import java.util.Set;

public interface IngredientDAO {
    Set<Ingredient> getAllIngredients();
    Ingredient getIngredient(Long id);
}
