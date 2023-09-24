package peaksoft.services.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.userInfo.PaginationResponse;
import peaksoft.dto.userInfo.UserRequestInfo;
import peaksoft.dto.userInfo.UserResponseInfo;
import peaksoft.entities.Restaurant;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.AccessDenied;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.services.UserService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PaginationResponse getAllUsers(int currentPage, int pageSize) {
        Pageable pageable = PageRequest.of(currentPage-1, pageSize);
        Page<UserResponseInfo> getAllUsers = userRepository.getAllCompaniesUser(pageable);
        log.info("Get all users method is able...");
        return PaginationResponse.builder()
                .userResponseInfoList(getAllUsers.getContent())
                .currentPage(getAllUsers.getNumber()+1)
                .pageSize(getAllUsers.getTotalPages())
                .build();
    }

    @Override
    public UserResponseInfo getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("User with id:"+userId+" not found..."));
        if (user.getRole() == Role.ADMIN) {
            throw new NotFoundException("User with id:" + userId + " not found...");
        }
        log.info("Get by id:"+userId+" found");
        return userRepository.getUserById(userId)
                .orElseThrow(()-> {
                    log.info("Get by id:"+userId+" not found");
                    return new NotFoundException("User with id:"+userId+" not exists");
                });

    }

    @Override
    @Transactional
    public SimpleResponse updateUser(Long userId, UserRequestInfo userRequestInfo) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->{
                    log.error("User not found...");
                    return new NotFoundException("User not found...");
                });
        if (user.getRole() == Role.ADMIN) {
            throw new AccessDenied("You do not have permission to delete an admin.");
        }else {
            user.setFirstName(userRequestInfo.getFirstName());
            user.setLastName(userRequestInfo.getLastName());
            user.setDateOfBirth(userRequestInfo.getDateOfBirth());
            user.setEmail(userRequestInfo.getEmail());
            user.setPassword(passwordEncoder.encode(userRequestInfo.getPassword()));
            user.setRole(userRequestInfo.getRole());
            user.setExperience(userRequestInfo.getExperience());
            log.info("User with id:" + userId + " updated...");
        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with id: %s successfully updated", user.getId()))
                .build();
    }



    @Override
    public SimpleResponse deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.info("User with id:" + userId + " not found...");
                    return new NotFoundException("User with id:" + userId + " not found...");
                });
        if (user.getRole() == Role.ADMIN) {
            throw new AccessDenied("You do not have permission to delete an admin.");
        } else {
            userRepository.delete(user);
        }
        log.info("User is deleted with id:" + userId + "...");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User with id:" + userId + " has been deleted.")
                .build();
    }
    }

