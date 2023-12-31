package peaksoft.dto.stopList;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
@Getter
@Setter
public class StopListRequest {
    private String reason;
    private ZonedDateTime date;

    public StopListRequest(String reason, ZonedDateTime date) {
        this.reason = reason;
        this.date = date;
    }
}
