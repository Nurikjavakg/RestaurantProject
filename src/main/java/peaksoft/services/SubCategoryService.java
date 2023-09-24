package peaksoft.services;

import peaksoft.dto.category.CategoryRequest;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.subcategory.SubCategoryRequest;
import peaksoft.dto.subcategory.SubCategoryResponse;

import java.util.List;

public interface SubCategoryService {
    SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subcategoryRequest);
    SimpleResponse assignUserToRestaurant(Long userId, Long restaurantId);
    List<SubCategoryResponse> getByGroupSupCategory();
    SimpleResponse updateSubCategoryById(Long subCategoryId, SubCategoryRequest subCategoryRequest);
    SimpleResponse deleteSubCategory(Long categoryId,Long subCategoryId);
}
