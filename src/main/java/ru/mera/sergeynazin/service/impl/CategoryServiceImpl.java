package ru.mera.sergeynazin.service.impl;

import ru.mera.sergeynazin.dao.CategoryDAO;
import ru.mera.sergeynazin.model.Category;
import ru.mera.sergeynazin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public abstract class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public void add(Category category) {
        categoryDAO.add(category);
    }

    @Override
    public void update(Category category) {
        categoryDAO.update(category);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryDAO.getCategory(id);
    }

    @Override
    public void delete(Long id) {
        categoryDAO.delete(id);
    }

    @Override
    public List<Category> getCategories() {
        return categoryDAO.getCategories();
    }
}
