package peaksoft.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.category.CategoryRequest;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.subcategory.SubCategoryRequest;
import peaksoft.dto.subcategory.SubCategoryResponse;
import peaksoft.entities.Category;
import peaksoft.entities.SubCategory;
import peaksoft.exceptions.InvalidNameException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.services.SubCategoryService;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class SubCategoryServiceImpl implements SubCategoryService{
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subcategoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id:" + categoryId + " not found"));

            SubCategory subCategory = new SubCategory();
            subCategory.setName(subcategoryRequest.getName());
            subCategory.setCategory(category);
            subCategoryRepository.save(subCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Subcategory with id: %s successfully saved", categoryId))
                .build();
    }

    @Override
    public SimpleResponse assignUserToRestaurant(Long userId, Long restaurantId) {
        return null;
    }


    @Override
    public List<SubCategoryResponse> getByGroupSupCategory() {
        return subCategoryRepository.getByGroupSupCategory();
    }

    @Override
    @Transactional
    public SimpleResponse updateSubCategoryById(Long subCategoryId, SubCategoryRequest subCategoryRequest) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(()-> new NotFoundException("SubCategory with id:"+subCategoryId+ " not found"));
        subCategory.setName(subCategoryRequest.getName());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Subcategory with id: %s successfully updated", subCategoryId))
                .build();
    }

    @Override
    public SimpleResponse deleteSubCategory(Long categoryId, Long subCategoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id:" + categoryId + " not found"));

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(()-> new NotFoundException("SubCategory with id:"+subCategoryId+ " not found"));

        category.getSubCategories().remove(subCategory);
        subCategoryRepository.delete(subCategory);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Subcategory with id: %s successfully deleted", categoryId))
                .build();
    }

}

