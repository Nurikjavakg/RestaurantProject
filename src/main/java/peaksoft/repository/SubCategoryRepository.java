package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.subcategory.SubCategoryResponse;
import peaksoft.entities.SubCategory;
import java.util.List;
import java.util.Optional;


@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> getSubCategoriesByName(String name);

    @Query("select new peaksoft.dto.subcategory.SubCategoryResponse(c.name,s.id, s.name) from Category c join c.subCategories s group by c.name,s.id, s.name")
    List<SubCategoryResponse> getByGroupSupCategory();
}