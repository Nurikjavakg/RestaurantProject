package peaksoft.repository.dao.cheque;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.cheque.ChequeFromWaiterRequest;
import peaksoft.dto.cheque.ChequeResponse;
import peaksoft.dto.cheque.GetAllChequeFromRestaurantResponse;
import peaksoft.dto.menuItem.MenuItemResponse;
import peaksoft.entities.User;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChequeJDBCTemplateImpl implements ChequeJDBCTemplate {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int countMenu(Long menuId) {
        return 0;
    }

    @Override
    public ChequeResponse getChequeFromUserFromOneDay(Long userId) {

        String sql = """
                SELECT
                       CONCAT(u.first_name, ' ', u.last_name) AS full_name,
                       c.price_average  AS priceAverage,
                       r.service AS service,
                       c.price_average * 1.15 AS grandTotal
      
                FROM restaurants r
                JOIN users u ON r.id = u.restaurant_id
                JOIN cheque c ON u.id = c.user_id
                JOIN menu m ON r.id = m.restaurant_id
                WHERE u.id = ?
                GROUP BY c.price_average, u.first_name, u.last_name, r.service;
                              
                        """;
        ChequeResponse chequeResponse = jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        new ChequeResponse(
                                rs.getString("full_name"),
                                rs.getBigDecimal("priceAverage"),
                                rs.getInt("service"),
                                rs.getBigDecimal("grandTotal"))
                , userId);

        String sql2 = """
                SELECT m.id,
                       m.name,
                       m.price,
                       m.image,
                       m.description,
                       m.is_vegetarian
                 FROM menu m
                          JOIN cheque_menus cm ON m.id = cm.menus_id
                          JOIN cheque c2 on cm.cheque_list_id = c2.id
                          JOIN users u2 on c2.user_id = u2.id
                 WHERE u2.id = ?
                """;
        List<MenuItemResponse> menuItemResponse = jdbcTemplate.query(sql2,
                (rs, rowNum) -> MenuItemResponse
                        .builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .price(rs.getBigDecimal("price"))
                        .image(rs.getString("image"))
                        .description(rs.getString("description"))
                        .isVegetarian(rs.getBoolean("is_vegetarian"))
                        .build(),
                userId);
        if (menuItemResponse  == null) throw new NullPointerException("");
            else chequeResponse.setMenus(menuItemResponse);

        return chequeResponse;

    }


    @Override
    public GetAllChequeFromRestaurantResponse getAllChequeFromRes(Long restId) {
        String sql = """
           select r.name as full_name,
                  sum(c.price_average) as sum_cheque
           from restaurants r
           join users u on r.id = u.restaurant_id
           join cheque c on u.id = c.user_id
           where r.id = ?
           group by r.name
        """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new GetAllChequeFromRestaurantResponse(
                        rs.getString("full_name"),
                        rs.getInt("sum_cheque")
                ),restId
        );
    }

}





