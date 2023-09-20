package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.user.SignUpRequest;
import peaksoft.services.serviceImpl.AuthenticationServiceImpl;

@RestController
@RequestMapping("/api/admin/signUp")
@RequiredArgsConstructor
public class AuthenticationSignUpApi {
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<SimpleResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));

    }
}