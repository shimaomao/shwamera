package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Shaurma;

import java.util.List;
import java.util.Optional;

public interface ShaurmaService {

    void save(Shaurma shaurma);
    List<Shaurma> getAll();
    Shaurma loadAsPersistent(Long id);
    void update(Shaurma detachedEntity);
    void delete(Shaurma persistentEntity);
    boolean tryDelete(Long id);
    Optional<Shaurma> optionalIsExist(Long id);
}
