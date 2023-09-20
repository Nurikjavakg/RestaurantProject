package peaksoft.dto.user;

public record UserRequest(
        String email,
        String password
) {
}
