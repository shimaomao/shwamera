package ru.mera.sergeynazin.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.ShaurmaService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Transactional(readOnly = true)
public class ShaurmaServiceImpl implements ShaurmaService {

    private JpaRepository repository;

    public void setRepository(final JpaRepository shaurmaRepository) {
        this.repository = shaurmaRepository;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Shaurma> getAll() {
        final CriteriaQuery<Shaurma> criteriaQuery = repository.myCriteriaQuery();
        final Root<Shaurma> root = criteriaQuery.from(Shaurma.class);
        criteriaQuery.select(root);
        return repository.getByCriteriaQuery(criteriaQuery);
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.ofNullable(Shaurma_managed_instance)
     */
    @Override
    public Optional<Shaurma> optionalIsExist(final Long id) {
        return repository.getOptionalById(id);
        //Optional.ofNullable(repository.getOptionalById(id));
    }

    @Override
    public Shaurma getOrThrow(Long id) {
        return (Shaurma) repository.getOptionalById(id)
            .orElseThrow(() -> NotFoundException.throwNew(id));
    }

    @Transactional
    @Override
    public Long saveValid(final Shaurma transient_) {
        return (Long) repository.create(transient_);
    }

    @Transactional
    @Override
    public Shaurma mergeOrThrow(Shaurma newDetached) {
        return repository.getOptionalById(newDetached.getId())
            .map(oldPersistent -> repository.mergeStateWithDbEntity(newDetached))
            .orElseThrow(() -> NotFoundException.throwNew(newDetached.getId()));
    }

    @Transactional
    @Override
    public Shaurma deleteOrThrow(Long id) {
        return (Shaurma) repository.getOptionalById(id)
            .map(ingredient -> {
                repository.remove(ingredient);
                return ingredient;
            }).orElseThrow(() -> NotFoundException.throwNew(id));
    }

    @Override
    public boolean validateExistsOrThrow(Shaurma... shaurmas) {
        Stream.of(shaurmas)
            .parallel()
            .filter(shaurma -> !optionalIsExist(shaurma.getId()).isPresent())
            .map(shaurma -> Objects.toString(shaurma.getId(), shaurma.getName()))
            .reduce((n1, n2) -> n1 +", "+ n2)
            .ifPresent(NotFoundException::throwNew);
        return true;
    }
}
