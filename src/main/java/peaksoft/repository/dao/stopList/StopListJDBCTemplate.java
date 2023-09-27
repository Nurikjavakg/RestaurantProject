package peaksoft.repository.dao.stopList;

import peaksoft.dto.stopList.StopListResponse;
import peaksoft.dto.subcategory.SubCategoryResponse;

import java.util.List;

public interface StopListJDBCTemplate {
    List<StopListResponse> getSubCategoriesCategoryById();
}
