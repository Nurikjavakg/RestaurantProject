package peaksoft.repository.dao.menuItem;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import peaksoft.dto.menuItem.MenuItemResponse;
import peaksoft.dto.subcategory.SubCategoryResponse;
import peaksoft.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuItemJDBCTemplateImpl implements MenuItemJDBCTemplate {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MenuItemResponse> menuResponseRowMapper = (resultSet, rowNumber) -> {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String image = resultSet.getString("image");
        BigDecimal price = resultSet.getBigDecimal("price");
        String description = resultSet.getString("description");
        boolean isVegetarian = resultSet.getBoolean("is_vegetarian");
        return new MenuItemResponse(id, name, image, price, description, isVegetarian);
    };


    @Override
    public List<MenuItemResponse> getMenuByAlphabet(String alphabet) {
        String sql = """
                select m.id,
                 m.name as name,
                 m.image as image,
                 m.price as price, 
                 m.description as description,
                 m.is_vegetarian as is_vegetarian
                 from menu m 
                 where m.name like ? || '%'
                """;
        return jdbcTemplate.query(sql, new Object[]{alphabet}, (rs, rowNum) ->
                new MenuItemResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getBigDecimal("price"),
                        rs.getString("description"),
                        rs.getBoolean("is_vegetarian")
                ));
    }

    @Override
    public List<MenuItemResponse> getSortByPrice(String ascOrDesc) {
        String sortOrder = "asc".equalsIgnoreCase(ascOrDesc) ? "asc" : "desc";
        String sql = """
                select
                    m.id as id,
                    m.name as name,
                    m.image as image,
                    m.price as price,
                    m.description as description,
                    m.is_vegetarian as is_vegetarian
                    from menu m
                    order by m.price """ + " " + sortOrder;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new MenuItemResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("image"),
                        rs.getBigDecimal("price"),
                        rs.getString("description"),
                        rs.getBoolean("is_vegetarian")
                ));
    }

    @Override
    public List<MenuItemResponse> getByFilterMenu(Boolean filter) {
        // String sortOrder = "false".equalsIgnoreCase(filter) ? "false" : "true";
        if (filter.equals(false)) {
            String sql = "SELECT " +
                    "m.id as id, " +
                    "m.name as name, " +
                    "m.image as image, " +
                    "m.price as price, " +
                    "m.description as description, " +
                    "m.is_vegetarian as is_vegetarian " +
                    "FROM menu m " +
                    "WHERE m.is_vegetarian = false ";
            return jdbcTemplate.query(sql, menuResponseRowMapper);

        }else if(filter.equals(true)) {
            String sql = "SELECT " +
                    "m.id as id, " +
                    "m.name as name, " +
                    "m.image as image, " +
                    "m.price as price, " +
                    "m.description as description, " +
                    "m.is_vegetarian as is_vegetarian " +
                    "FROM menu m " +
                    "WHERE m.is_vegetarian = true or m.is_vegetarian = true";

            return jdbcTemplate.query(sql, menuResponseRowMapper);
        }else return null;

    }
}