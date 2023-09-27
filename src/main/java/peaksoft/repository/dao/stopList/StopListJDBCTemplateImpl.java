package peaksoft.repository.dao.stopList;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.menuItem.MenuItemResponse;
import peaksoft.dto.stopList.StopListResponse;
import peaksoft.dto.subcategory.SubCategoryResponse;


import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StopListJDBCTemplateImpl implements StopListJDBCTemplate{
      private final JdbcTemplate jdbcTemplate;
    @Override
    public List<StopListResponse> getSubCategoriesCategoryById() {
        String sql = """
                select s.id,
                s.reason as reason,
                s.date as date
                from stop_list s
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new StopListResponse(
                        rs.getLong("id"),
                        rs.getString("reason"),
                        timestampToZonedDateTime(rs.getTimestamp("date"))
                ));
    }

    private ZonedDateTime timestampToZonedDateTime(Timestamp timestamp) {
        Instant instant = timestamp.toInstant();
        ZoneId zoneId = ZoneId.of("UTC");
        return instant.atZone(zoneId);
    }
}
