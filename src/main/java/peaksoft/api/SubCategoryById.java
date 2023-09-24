package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.category.CategoryRequest;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.subcategory.SubCategoryRequest;
import peaksoft.dto.subcategory.SubCategoryResponse;
import peaksoft.repository.dao.SubCategoryJDBCTemplateImpl;
import peaksoft.services.SubCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class SubCategoryById {
    private final SubCategoryService subCategoryService;
    private final SubCategoryJDBCTemplateImpl subCategoryJDBCTemplate;

    @PostMapping("/saveSubCategory/{categoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Save SubCategory", description = "")
    public SimpleResponse saveSubCategory(@RequestBody SubCategoryRequest subCategoryRequest, @PathVariable Long categoryId) {
        subCategoryService.saveSubCategory(categoryId, subCategoryRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with name: %s successfully saved!", subCategoryRequest.getName()))
                .build();
    }

    @GetMapping("/getSubCategoriesByCategoryId")
    @Operation(summary = "Get subCategories by category Id", description = "")
    public List<SubCategoryResponse> getSubCategoriesByCategoryId(@RequestParam(required = false) Long categoryId) {
        return subCategoryJDBCTemplate.getSubCategoriesCategoryById(categoryId);
    }

    @GetMapping("/getGroupBySubCategories")
    @Operation(summary = "Get subCategories by category Id", description = "")
    public List<SubCategoryResponse> getGroupSubCategories() {
        return subCategoryService.getByGroupSupCategory();
    }


    @PutMapping("/updateSubCategory/{subCategoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update SubCategory",description = "")
    public SimpleResponse updateSubCategoryById(@PathVariable Long subCategoryId, @RequestBody SubCategoryRequest subCategoryRequest) {
        subCategoryService.updateSubCategoryById(subCategoryId, subCategoryRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Category with name: %s successfully updated!", subCategoryRequest.getName()))
                .build();
    }
    @DeleteMapping("/deleteSubCategory/{categoryId}/{subCategoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete subCategory by id",description = "")
    public SimpleResponse deleteCategoryById(@PathVariable Long categoryId,@PathVariable Long subCategoryId){
        subCategoryService.deleteSubCategory(categoryId,subCategoryId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("SubCategory with id:"+categoryId+" is deleted...")
                .build();
    }
}
