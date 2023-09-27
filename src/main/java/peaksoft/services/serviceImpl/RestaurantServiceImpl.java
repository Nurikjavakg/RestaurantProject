package peaksoft.services.serviceImpl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.dto.restaurant.RestaurantRequest;
import peaksoft.dto.restaurant.RestaurantResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entities.Restaurant;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.AccessDenied;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.services.RestaurantService;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDenied("Authentication required to update product !!!");
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.getName());
        restaurant.setLocation(restaurantRequest.getLocation());
        restaurant.setRestType(restaurantRequest.getRestType());
        restaurant.setService(restaurantRequest.getService());

        restaurantRepository.save(restaurant);


        log.info("Restaurant is saved ...");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant is saved ..."))
                .build();
    }

    @Override
    public SimpleResponse assignUserToRestaurant(Long userId, Long restaurantId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("User with id:"+userId+" not found..."));

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new NotFoundException("Restaurant with id:"+restaurantId+" not found..."));
        int numberOfEmployees = restaurantRepository.countEmployees(restaurantId);
        restaurant.setNumberOfEmployees(numberOfEmployees+1);
        restaurant.getUsers().add(user);
        user.setRestaurant(restaurant);
        restaurantRepository.save(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with id:"+userId+" successfully assigned to restaurant..."))
                .build();
    }

    @Override
    public RestaurantResponse getRestaurantById(Long restaurantId) {
        RestaurantResponse restaurantResponse = restaurantRepository.getRestaurantById(restaurantId)
                .orElseThrow(()-> new NotFoundException("Restaurant with id:"+restaurantId+" not found..."));
         int countEmployees = restaurantRepository.countEmployees(restaurantId);
         restaurantResponse.setNumberOfEmployees(countEmployees);

        return restaurantResponse;
    }

    @Override
    public SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new AccessDenied("Authentication required to update restaurant!!!");
            }
            if (authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals(Role.ADMIN))) {
                throw new AccessDenied("You do not have permission to update a restaurant.");
            }

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new NotFoundException("Restaurant with id:" + restaurantId + " not found"));

            restaurant.setName(restaurantRequest.getName());
            restaurant.setLocation(restaurantRequest.getLocation());
            restaurant.setRestType(restaurantRequest.getRestType());
            restaurant.setService(restaurantRequest.getService());

            log.info("Restaurant with id:" + restaurantId + " updated...");

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format("Restaurant with id: %s successfully updated", restaurantId))
                    .build();
        }


        @Override
    public SimpleResponse deleteRestaurant(Long restaurantId) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new AccessDenied("Authentication required to delete a restaurant !!!");
            }

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> {
                        log.info("Restaurant with id:" + restaurantId + " not found...");
                        return new NotFoundException("Restaurant with id:" + restaurantId + " not found...");
                    });

            restaurantRepository.delete(restaurant);

            log.info("Restaurant is deleted with id:" + restaurantId + "...");
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Restaurant with id:" + restaurantId + " has been deleted.")
                    .build();
        }
}
