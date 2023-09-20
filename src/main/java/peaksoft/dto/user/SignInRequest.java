package peaksoft.dto.user;

public record SignInRequest(
        String email,
        String password
) {
}
