package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.category.CategoryRequest;
import peaksoft.dto.category.CategoryResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.services.CategoryService;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class CategoryApi {
    private final CategoryService categoryService;
    @PostMapping("/saveCategory")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Save category",description = "")
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest){
        categoryService.saveCategory(categoryRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name: %s successfully saved!",categoryRequest.getName()))
                .build();
    }
    @GetMapping("/getUserById")
    @Operation(summary = "Get category by Id",description = "")
    public CategoryResponse getCategoryById(@RequestParam(required = false) Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }


    @PutMapping("/updateCategory/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update category",description = "")
    public SimpleResponse updateCategoryById(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategoryById(categoryId,categoryRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name: %s successfully updated!", categoryRequest.getName()))
                .build();
    }
    @DeleteMapping("/deleteCategory/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete restaurant by id",description = "")
    public SimpleResponse deleteCategoryById(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        System.out.println("new file");
        System.out.println("new status");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Category with id:"+categoryId+" is deleted...")
                .build();

    }
}
