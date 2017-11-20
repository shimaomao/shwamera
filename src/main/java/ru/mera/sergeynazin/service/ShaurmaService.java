package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.model.Shaurma;
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

public interface ShaurmaService {
    //void delete(Shaurma detached);

    // GET
    List<Shaurma> getAll()
    Optional<Shaurma> optionalIsExist(Long id);
    Shaurma getOrThrow(Long id);

    // POST
    Long saveValid(Shaurma transient_);

    // PUT
    Shaurma mergeOrThrow(Shaurma newDetached);

    // DELETE
    Shaurma deleteOrThrow(Long id);

    // helpers
    boolean validateExistsOrThrow(Shaurma... shaurmas);

}
