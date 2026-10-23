package com.salon.categoryservice.service.implementation;

import com.salon.categoryservice.dto.SalonDTO;
import com.salon.categoryservice.model.Category;
import com.salon.categoryservice.repository.CategoryRepository;
import com.salon.categoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDTO.getId());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalonId(Long salonId) {
        return categoryRepository.findBySalonId(salonId);
    }

    @Override
    public Category getCategoryById(Long categoryId) throws Exception {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        if (category == null) {
            throw new Exception("Category does not exist with id "+categoryId);
        }
        return category;
    }

    @Override
    public void deleteCategoryById(Long categoryId,Long salonId) throws Exception {
        Category category = getCategoryById(categoryId);

        if (!category.getSalonId().equals(salonId)) {
            throw new Exception("You don't have permission to delete this Category");
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category findByIdAndSalonId(Long id, Long salonId) throws Exception {
        Category category = categoryRepository.findByIdAndSalonId(id,salonId);
        if (category == null) {
            throw new Exception("Category not found...");
        }
        return category;
    }
}
