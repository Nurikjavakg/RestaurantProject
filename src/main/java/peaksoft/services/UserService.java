package peaksoft.services;

import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.userInfo.PaginationResponse;
import peaksoft.dto.userInfo.UserRequestInfo;
import peaksoft.dto.userInfo.UserResponseInfo;

public interface UserService {
    PaginationResponse getAllUsers(int currentPage, int pageSize);
    UserResponseInfo getUserById(Long userId);
    SimpleResponse updateUser(Long userId, UserRequestInfo userRequestInfo);
    SimpleResponse deleteUser(Long userId);
}
