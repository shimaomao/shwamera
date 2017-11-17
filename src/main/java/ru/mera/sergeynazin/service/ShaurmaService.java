package ru.mera.sergeynazin.service;

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
    Shaurma loadAsPersistent(Long id);
    void save(Shaurma transient_);
    List<Shaurma> getAll();
    void update(Shaurma detached);
    Shaurma merge(Shaurma transientOrDetached);
    void delete(Shaurma detached);


    // GET
    Optional<Shaurma> optionalIsExist(Long id);
    Shaurma getOrThrow(Long id);

    // POST
    Long saveValidOrThrow(Shaurma transient_);

    // PUT
    Shaurma mergeOrThrowNotFound(Shaurma newDetached);

    // DELETE
    Shaurma deleteOrThrow(Long id);
}
