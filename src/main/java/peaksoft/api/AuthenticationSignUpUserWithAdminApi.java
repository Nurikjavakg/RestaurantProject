package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.user.SignUpRequest;
import peaksoft.services.AuthenticationServcieForAdmin;
@RestController
@RequestMapping("/api/admin/signUp")
@RequiredArgsConstructor
public class AuthenticationSignUpUserWithAdminApi {

    private final AuthenticationServcieForAdmin authenticationServcieForAdmin;

    @PostMapping("/withAdmin/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Sign up user with admin",description = "")
    public ResponseEntity<SimpleResponse> signUpUserWithAdmin(@RequestBody SignUpRequest signUpRequest, @PathVariable Long restaurantId) {
        return ResponseEntity.ok(authenticationServcieForAdmin.signUpUserWithAdmin(signUpRequest,restaurantId));

    }
}
