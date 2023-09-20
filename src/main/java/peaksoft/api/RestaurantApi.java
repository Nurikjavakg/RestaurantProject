package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.restaurant.RestaurantRequest;
import peaksoft.dto.restaurant.RestaurantResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.services.RestaurantService;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class RestaurantApi {
    private final RestaurantService restaurantService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        restaurantService.saveRestaurant(restaurantRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s successfully saved!",restaurantRequest.getName()))
                .build();
    }
    @GetMapping
    public RestaurantResponse getRestaurantById(@RequestParam (required = false) Long restaurantId){
        return restaurantService.getRestaurantById(restaurantId);
    }

    @PostMapping("/assign/{userId}/{restaurantId}")
    public SimpleResponse assign(@PathVariable Long userId, @PathVariable Long restaurantId){
         restaurantService.assignUserToRestaurant(userId,restaurantId);
         return SimpleResponse.builder()
                 .httpStatus(HttpStatus.OK)
                 .message(String.format("User with id:"+userId+" successfully assigned to restaurant..."))
                 .build();
    }

}
