package peaksoft.services.serviceImpl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.user.SignUpRequest;
import peaksoft.entities.Restaurant;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.*;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.services.AuthenticationServcieForAdmin;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceForAdminImpl implements AuthenticationServcieForAdmin {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse signUpUserWithAdmin(SignUpRequest request,Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new NotFoundException("Restaurant with id:"+restaurantId+" not found..."));

        User userEmail = userRepository.findByEmail(request.getEmail());
        if(userEmail != null){
            throw new AlreadyExists("User with email:"+request.getEmail()+" already exist");
        }

        User user = convertRequestToUser(request);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        LocalDate currentDate = LocalDate.now();
        LocalDate dob = request.getDateOfBirth();

        int age = Period.between(dob, currentDate).getYears();
        if (request.getRole().equals(Role.WAITER)) {
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
                int numberOfEmployees = restaurantRepository.countEmployees(restaurantId);
                restaurant.setNumberOfEmployees(numberOfEmployees);
                restaurant.getUsers().add(user);
                user.setRestaurant(restaurant);
                restaurantRepository.save(restaurant);
            } else {
                throw new AccessDenied("You have not permission signUp in this restaurant");
            }




        } else if (Role.CHEF.equals(request.getRole())) {
            if (age < 25 || age > 45) {
                user.setDateOfBirth(request.getDateOfBirth());
                throw new InvalidAgeException("Waiter age must be between 18 and 30 !");
            }
            if(request.getExperience() < 2) {
                throw new InvalidExperience("The chef must have more than 2 years of experience");
            }
            user.setExperience(request.getExperience());


            user.setRole(Role.CHEF);
            user.setRestaurant(restaurant);
            restaurant.getUsers().add(user);
            restaurantRepository.save(restaurant);
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


}
