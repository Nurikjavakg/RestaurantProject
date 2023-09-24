package peaksoft.repository.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.subcategory.SubCategoryResponse;
import peaksoft.entities.Category;
import peaksoft.repository.CategoryRepository;

import javax.swing.tree.RowMapper;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class SubCategoryJDBCTemplateImpl implements SubCategoryJDBCTemplate{
    private final JdbcTemplate jdbcTemplate;
    private final CategoryRepository categoryRepository;

    @Override
    public List<SubCategoryResponse> getSubCategoriesCategoryById(Long categoryId){
    String sql = """
            select c.name as category_name,
            s.id as sub_categories_id,
            s.name as sub_categories_name
            from sub_categories s
            join Categories c on 
            c.id = s.category_id
            where c.id = ?
            order by s.name asc
            """;
        return jdbcTemplate.query(sql, new Object[]{categoryId}, (rs, rowNum) ->
                new SubCategoryResponse(
                        rs.getString("category_name"),
                        rs.getLong("sub_categories_id"),
                        rs.getString("sub_categories_name")));
}
}
