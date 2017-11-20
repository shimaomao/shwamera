package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Again, for educational purposes different approach is used here
 * IT COULD BE GENERIFIED AS WELL AS
 * @see JpaRepository
 *
 * @author sergeynazin
 * */


public interface IngredientService {

    // GET
    List<Ingredient> getAll();
    Optional<Ingredient> optionalIsExist(String name);
    Optional<Ingredient> optionalIsExist(Long id);
    Ingredient getOrThrow(Long id);

    // POST
    Long saveOrThrowExist(Ingredient transient_);

    // PUT
    Ingredient mergeOrThrow(Ingredient newDetached);

    // DELETE
    Ingredient deleteOrThrow(Long id);

    // helpers
    boolean validateExistsOrThrow(Ingredient... ingredients);
}
