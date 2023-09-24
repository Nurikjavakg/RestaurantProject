package peaksoft.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.category.CategoryRequest;
import peaksoft.dto.category.CategoryResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entities.Category;
import peaksoft.exceptions.AccessDenied;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.services.CategoryService;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDenied("Authentication required to update product !!!");
        }
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        log.info("Category is saved ...");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category is saved ..."))
                .build();

    }

    @Override
    public SimpleResponse assignUserToRestaurant(Long userId, Long restaurantId) {
        return null;
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        CategoryResponse categoryResponse = categoryRepository.getCategoryById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id:" + categoryId + " not found..."));
        return categoryResponse;
    }


    @Override
    @Transactional
    public SimpleResponse updateCategoryById(Long categoryId, CategoryRequest categoryRequest) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id:" + categoryId + " not found"));
        category.setName(categoryRequest.getName());
        log.info("Category with id:" + categoryId + " updated...");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with id: %s successfully updated", categoryId))
                .build();
    }

    @Override
    public SimpleResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id:" + categoryId + " not found"));
        categoryRepository.delete(category);
        log.info("Restaurant is deleted with id:" + categoryId + "...");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant with id:" + categoryId + " has been deleted.")
                .build();
    }
}
