package com.salon.categoryservice.controller;

import com.salon.categoryservice.dto.SalonDTO;
import com.salon.categoryservice.model.Category;
import com.salon.categoryservice.service.CategoryService;
import com.salon.categoryservice.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories/salon-owner")
@RequiredArgsConstructor
public class SalonCategoryController {

    private final CategoryService categoryService;
    private final SalonFeignClient salonFeignClient;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category,@RequestHeader("Authorization")String token) throws Exception {

        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(token).getBody();

        Category savedCategory = categoryService.saveCategory(category, salonDTO);

        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id,@RequestHeader("Authorization")String token) throws Exception {

        SalonDTO salonDTO =salonFeignClient.getSalonByOwnerId(token).getBody();

        categoryService.deleteCategoryById(id,salonDTO.getId());
        return ResponseEntity.ok("Salon category deleted");
    }

    @GetMapping("/salon/{salonId}/category/{id}")
    public ResponseEntity<Category> getCategoryByIdAndSalonId(@PathVariable Long id,@PathVariable Long salonId) throws Exception {
        Category category = categoryService.findByIdAndSalonId(id,salonId);
        return ResponseEntity.ok(category);
    }
}
