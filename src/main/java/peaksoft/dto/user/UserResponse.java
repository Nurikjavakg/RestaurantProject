package peaksoft.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String token;
    private ZonedDateTime createdAt;


    public UserResponse(Long id, String email, String token, ZonedDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.createdAt = createdAt;
    }
}
