package peaksoft.dto.subcategory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryResponse {
    private String categoryName;
    private Long id;
    private String name;

    public SubCategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubCategoryResponse(String categoryName, Long id, String name) {
        this.categoryName = categoryName;
        this.id = id;
        this.name = name;
    }
}
