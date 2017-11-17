package ru.mera.sergeynazin.service.impl;

import org.springframework.transaction.annotation.Transactional;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.JpaRepository;
import ru.mera.sergeynazin.service.ShaurmaService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public class ShaurmaServiceImpl implements ShaurmaService {

    private JpaRepository shaurmaRepository, ingredientRepository;

    public void setShaurmaRepository(final JpaRepository shaurmaRepository) {
        this.shaurmaRepository = shaurmaRepository;
    }

    public void setIngredientRepository(final JpaRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    @Override
    public void save(final Shaurma transient_) {
        shaurmaRepository.create(transient_);
    }

    @Override
    public Shaurma loadAsPersistent(final Long id) {
        return shaurmaRepository.getById(id);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public List<Shaurma> getAll() {
        final CriteriaQuery<Shaurma>  criteriaQuery = shaurmaRepository.myCriteriaQuery();
        final Root<Shaurma> root = criteriaQuery.from(Shaurma.class);
        criteriaQuery.select(root);
        return shaurmaRepository.getByCriteriaQuery(criteriaQuery);
    }

    @Transactional
    @Override
    public void update(final Shaurma detached) {
        shaurmaRepository.update(detached);
    }

    @Override
    public Shaurma merge(Shaurma transientOrDetached) {
        return shaurmaRepository.mergeStateWithDbEntity(transientOrDetached);
    }

    @Transactional
    @Override
    public void delete(final Shaurma detached) {
        shaurmaRepository.delete(detached);
    }

    /**
     * Hibernate 5.2.x providing support of Optional so we switch to that
     * otherwise can uncomment lines below
     * @param id Primary Key
     * @return Optional.of(Shaurma_managed_instance)
     */
    @Override
    public Optional<Shaurma> optionalIsExist(final Long id) {
        return shaurmaRepository.getOptionalById(id);
            //Optional.of(shaurmaRepository.getOptionalById(id));
    }

    @Override
    public Long saveValidOrThrow(Shaurma transient_) {
        transient_.getIngredientSet()
            .parallelStream()
            .filter(ingredient -> {
                ingredientRepository.
            })
    }
}
