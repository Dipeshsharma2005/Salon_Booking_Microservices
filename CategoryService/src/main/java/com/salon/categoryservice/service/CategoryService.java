package com.salon.categoryservice.service;

import com.salon.categoryservice.dto.SalonDTO;
import com.salon.categoryservice.model.Category;

import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDTO salonDTO);
    Set<Category> getAllCategoriesBySalonId(Long salonId);
    Category getCategoryById(Long categoryId) throws Exception;
    void deleteCategoryById(Long categoryId,Long salonId) throws Exception;
    Category findByIdAndSalonId(Long id,Long salonId) throws Exception;

}
