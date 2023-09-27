package peaksoft.dto.stopList;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
@Getter
@Setter
public class StopListResponse {
    private Long id;
    private String reason;
    private ZonedDateTime date;

    public StopListResponse(Long id, String reason, ZonedDateTime date) {
        this.id = id;
        this.reason = reason;
        this.date = date;
    }
}
