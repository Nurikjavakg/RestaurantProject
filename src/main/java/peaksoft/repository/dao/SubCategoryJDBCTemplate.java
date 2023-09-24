package peaksoft.repository.dao;

import peaksoft.dto.subcategory.SubCategoryResponse;

import java.util.List;

public interface SubCategoryJDBCTemplate{
    List<SubCategoryResponse> getSubCategoriesCategoryById(Long categoryId);

}
