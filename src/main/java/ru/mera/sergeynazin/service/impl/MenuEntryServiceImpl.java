package ru.mera.sergeynazin.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.model.MenuEntry;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.MenuEntryService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Only for shaurmamaker
 */

@Transactional(readOnly = true)
public class MenuEntryServiceImpl implements MenuEntryService {

    private JpaRepository repository;

    public void setRepository(final JpaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void save(final MenuEntry transient_) {
        repository.create(transient_);
    }

    @Override
    public MenuEntry loadAsPersistent(final Long id) {
        return repository.getById(id);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<MenuEntry> getAll() {
        final CriteriaQuery<MenuEntry>  criteriaQuery = repository.myCriteriaQuery();
        final Root<MenuEntry> root = criteriaQuery.from(MenuEntry.class);
        criteriaQuery.select(root);
        return repository.getByCriteriaQuery(criteriaQuery);
    }

    @Transactional
    @Override
    public void update(final MenuEntry detached) {
        repository.update(detached);
    }

    @Transactional
    @Override
    public void delete(final MenuEntry detached) {
        repository.delete(detached);
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(MenuEntry_managed_instance)
     */
    @Override
    public Optional<MenuEntry> optionalIsExist(final Long id) {
        return repository.getOptionalById(id);
            // Optional.of(repository.get(id));
    }
}
