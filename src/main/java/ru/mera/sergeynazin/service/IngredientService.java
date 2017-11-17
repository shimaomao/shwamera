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

    void save(Ingredient transient_);
    List<Ingredient> getAll();
    void update(Ingredient detached);
    Ingredient mergeOrSave(Ingredient transientOrDetached);
    void delete(Ingredient detached);

    // GET

    Optional<Ingredient> optionalIsExist(String name);
    Optional<Ingredient> optionalIsExist(Long id);
    Ingredient getOrThrow(Long id);

    // POST
    Long saveOrThrowExist(Ingredient transient_);

    // PUT
    Ingredient mergeOrThrowNotFound(Ingredient newDetached);

    // DELETE
    Ingredient deleteOrThrow(Long id);

    // helpers
    boolean validateExistsOrThrow(Ingredient... ingredients);
}
