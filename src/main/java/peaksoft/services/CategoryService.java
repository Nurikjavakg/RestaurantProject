package peaksoft.services;

import peaksoft.dto.category.CategoryRequest;
import peaksoft.dto.category.CategoryResponse;
import peaksoft.dto.restaurant.RestaurantRequest;
import peaksoft.dto.restaurant.RestaurantResponse;
import peaksoft.dto.simple.SimpleResponse;

public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    SimpleResponse assignUserToRestaurant(Long userId, Long restaurantId);
    CategoryResponse getCategoryById(Long categoryId);

    SimpleResponse updateCategoryById(Long categoryId, CategoryRequest categoryRequest);
    SimpleResponse deleteCategory(Long categoryId);
}
