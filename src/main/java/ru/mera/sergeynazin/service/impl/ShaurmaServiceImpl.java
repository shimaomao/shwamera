package ru.mera.sergeynazin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.IRepository;
import ru.mera.sergeynazin.service.ShaurmaService;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class ShaurmaServiceImpl implements ShaurmaService {

    /**
     * Test
     */
    private static final Logger logger = LoggerFactory.getLogger(ShaurmaServiceImpl.class);

    private IRepository repository;

    public void setRepository(final IRepository repository) {
        logger.info("ShaurmaServiceImpl::setRepository() called with: repository = [" + repository + "]");
        this.repository = repository;
    }

    @Override
    public void save(final Shaurma shaurma) {
        logger.info("ShaurmaServiceImpl::save() called with: shaurma = [" + shaurma + "]");
        repository.createItem(shaurma);
    }

    @Override
    public Shaurma loadAsPersistent(final Long id) {
        logger.info("loadAsPersistent() called with: id = [" + id + "]");
        return repository.load(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Shaurma> getAll() {
        logger.info("ShaurmaServiceImpl::getAll() called");
        CriteriaQuery<Shaurma>  criteriaQuery = repository.myCriteriaQuery();
        Root<Shaurma> root = criteriaQuery.from(Shaurma.class);
        criteriaQuery.select(root);
        return repository.readItems(criteriaQuery);
    }


    @Override
    public void update(final Shaurma detachedEntity) {
        logger.info("ShaurmaServiceImpl::update() called with: detachedEntity = [" + detachedEntity + "]");
        repository.updateItem(detachedEntity);
    }

    @Override
    public void delete(final Shaurma persistentShaurma) {
        logger.info("ShaurmaServiceImpl::delete() called with: persistentShaurma = [" + persistentShaurma + "]");
        repository.deleteItem(persistentShaurma);
    }

    @Override
    public boolean tryDelete(final Long id) {
        logger.info("ShaurmaServiceImpl::tryDelete() called with: id = [" + id + "]");
        final Shaurma shaurma = repository.get(id);
        if (shaurma != null) {
            repository.deleteItem(shaurma);
            return true;
        }
        else return false;
    }

    @Override
    public Optional<Shaurma> optionalIsExist(final Long id) {
        logger.info("ShaurmaServiceImpl::optionalIsExist() called with: id = [" + id + "]");
        return Optional.of(repository.get(id));
    }
}