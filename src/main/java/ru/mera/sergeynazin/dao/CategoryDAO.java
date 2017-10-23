package ru.mera.sergeynazin.dao;

import ru.mera.sergeynazin.model.old.Category;

import java.util.List;

public interface CategoryDAO {

    void add(Category category);

    void update(Category category);

    Category getCategory(Long id);

    void delete(Long id);

    List<Category> getCategories();

}
