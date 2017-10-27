package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.MenuEntry;
import ru.mera.sergeynazin.repository.IRepository;

import java.util.List;
import java.util.Optional;

/**
 * Again, for educational purposes different approach is used here
 * IT COULD BE GENERIFIED AS WELL AS
 * @see IRepository
 *
 * @author sergeynazin
 * */

public interface MenuEntryService {
    void save(MenuEntry menuEntry);
    List<MenuEntry> getAll();
    MenuEntry loadAsPersistent(Long id);
    void update(MenuEntry detachedEntity);
    void delete(MenuEntry persistentEntity);
    Optional<MenuEntry> optionalIsExist(Long id);
}
