package peaksoft.api;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.dto.user.SignInRequest;
import peaksoft.dto.user.UserResponseWithToken;
import peaksoft.services.serviceImpl.AuthenticationServiceForUserImpl;


@RestController
@RequestMapping("/api/admin/login")
@RequiredArgsConstructor
public class AuthenticationSignInApi {
    private final AuthenticationServiceForUserImpl authenticationService;

    @PostMapping
    @Operation(summary = "Login",description = "")
    public ResponseEntity<UserResponseWithToken> login(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.login(signInRequest));
    }

}
