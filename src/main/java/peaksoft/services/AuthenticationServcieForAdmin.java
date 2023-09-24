package peaksoft.services;

import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.user.SignInRequest;
import peaksoft.dto.user.SignUpRequest;
import peaksoft.dto.user.UserResponseWithToken;

public interface AuthenticationServcieForAdmin {
    SimpleResponse signUpUserWithAdmin(SignUpRequest request, Long restaurantId);


}
