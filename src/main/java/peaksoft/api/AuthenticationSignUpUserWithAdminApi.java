package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public SimpleResponse signUpUserWithAdmin(@RequestBody SignUpRequest signUpRequest, @PathVariable Long restaurantId) {
         authenticationServcieForAdmin.signUpUserWithAdmin(signUpRequest,restaurantId);
         return SimpleResponse.builder()
                 .httpStatus(HttpStatus.OK)
                 .message("Waiter is successfully saved...")
                 .build();

    }
}
