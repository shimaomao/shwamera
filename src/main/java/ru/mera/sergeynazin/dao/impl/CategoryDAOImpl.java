package ru.mera.sergeynazin.dao.impl;

import ru.mera.sergeynazin.dao.CategoryDAO;
import ru.mera.sergeynazin.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public abstract class CategoryDAOImpl implements CategoryDAO {

    @Resource(name = "sessionFactory")
    SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(Category category) {
        getCurrentSession().save(category);
    }

    @Override
    public void update(Category category) {
        Category categoryToUpdate = getCategory(category.getCategoryId());
        categoryToUpdate.setCategoryTitle(category.getCategoryTitle());
        categoryToUpdate.setCategoryDesc(category.getCategoryDesc());
        getCurrentSession().update(categoryToUpdate);
    }

    @Override
    public Category getCategory(Long id) {
        Category category = (Category) getCurrentSession().get(Category.class, id);
        return category;
    }

    @Override
    public void delete(Long id) {
        Category category = getCategory(id);
        if (category != null) {
            getCurrentSession().delete(category);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Category> getCategories() {
        return getCurrentSession().createQuery("FROM Category").list();
    }
}
