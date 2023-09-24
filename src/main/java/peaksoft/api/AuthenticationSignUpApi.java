package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.user.SignUpRequest;
import peaksoft.services.serviceImpl.AuthenticationServiceForUserImpl;

@RestController
@RequestMapping("/api/admin/signUp")
@RequiredArgsConstructor
public class AuthenticationSignUpApi {
    private final AuthenticationServiceForUserImpl authenticationService;

    @PostMapping
    @Operation(summary = "Send resume to restaurant",description = "")
    public ResponseEntity<SimpleResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));

    }
}