package com.PaulDanielT.cardShield.dao.category;

import com.PaulDanielT.cardShield.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryDao {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Integer categoryId);
    Optional<Category> getCategoryByName(String categoryName);
}
