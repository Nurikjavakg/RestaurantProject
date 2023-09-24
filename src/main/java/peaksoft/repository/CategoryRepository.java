package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.category.CategoryResponse;
import peaksoft.entities.Category;

import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select new peaksoft.dto.category.CategoryResponse(c.id,c.name) from  Category c where c.id = :categoryId")
    Optional<CategoryResponse> getCategoryById(Long categoryId);
}