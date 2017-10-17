package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Category;

import java.util.List;

public interface CategoryService {

    void add(Category category);
    void update(Category category);
    Category getCategory(Long id);
    void delete(Long id);
    List<Category> getCategories();

}
