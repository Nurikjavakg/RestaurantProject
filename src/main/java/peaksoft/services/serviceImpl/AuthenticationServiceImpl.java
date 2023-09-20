package peaksoft.services.serviceImpl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.user.SignInRequest;
import peaksoft.dto.user.SignUpRequest;
import peaksoft.dto.user.UserResponseWithToken;
import peaksoft.entities.Restaurant;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.*;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.services.AuthenticationServcie;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationServcie {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final JwtService jwtService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public SimpleResponse signUp(SignUpRequest request) {
        Restaurant restaurant = restaurantRepository.findRestaurantById(1L).orElse(null);

        if(restaurant.getNumberOfEmployees() == 6) throw new NotVacanciesException("Unfortunately we have not vacancies!");

        User userEmail = userRepository.findByEmail(request.getEmail());
        if (userEmail != null) {
            throw new AlreadyExists("User with email:" + request.getEmail() + " already exist");
        }
        User user = convertRequestToUser(request);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        LocalDate currentDate = LocalDate.now();
        LocalDate dob = request.getDateOfBirth();

        int age = Period.between(dob, currentDate).getYears();
        if (request.getRole().equals(Role.WAITER)) {
            Long waiterCount = userRepository.chefCount(Role.WAITER);
            if (waiterCount > 13) {
                throw new AccessDenied("You can save only 13 chef");
            }
            if (age < 18 ||age > 30) {
                user.setDateOfBirth(request.getDateOfBirth());
                throw new InvalidAgeException("Waiter age must be between 18 and 30 !");
            }
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setExperience(request.getExperience());
            if(request.getExperience() < 1) {
                throw new InvalidExperience("The waiter must have more than 1 years of experience");
            }
            if (Role.WAITER.equals(request.getRole())) {
                user.setRole(Role.WAITER);
                userRepository.save(user);
            } else {
                throw new AccessDenied("You have not permission signUp in this restaurant");
            }




        } else if (Role.CHEF.equals(request.getRole())) {
            Long chefCount = userRepository.chefCount(Role.CHEF);
            if (chefCount > 2) {
                throw new AccessDenied("You can save only 2 chef");
            }

            if (age < 25 || age > 45) {
                user.setDateOfBirth(request.getDateOfBirth());
                throw new InvalidAgeException("Waiter age must be between 18 and 30 !");
            }
            if(request.getExperience() < 2) {
                throw new InvalidExperience("The chef must have more than 2 years of experience");
            }
            user.setExperience(request.getExperience());


            user.setRole(Role.CHEF);
            userRepository.save(user);
        } else {
            throw new AccessDenied("You have not permission save with another role");
        }

        log.info("Successfully saved user with id: " + user.getId());
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully saved user with id: " + user.getId())
                .build();

    }


    private User convertRequestToUser(SignUpRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }


    @PostConstruct
    public void initSaveAdmin() {
        User user = User.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    @Override
    public UserResponseWithToken login(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.email()).orElseThrow(() ->
                new RuntimeException(String.format("User with email: %s not found!", signInRequest.email())));

        String password = signInRequest.password();
        String dbEncodePassword = user.getPassword();

        if (!passwordEncoder.matches(password, dbEncodePassword)) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtService.generateToken(user);

        return new UserResponseWithToken(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}
