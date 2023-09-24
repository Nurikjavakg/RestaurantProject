package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Save restaurant",description = "")
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        restaurantService.saveRestaurant(restaurantRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s successfully saved!",restaurantRequest.getName()))
                .build();
    }
    @GetMapping
    @Operation(summary = "Get restaurant by Id",description = "")
    public RestaurantResponse getRestaurantById(@RequestParam (required = false) Long restaurantId){
        return restaurantService.getRestaurantById(restaurantId);
    }

    @PostMapping("/assign/{userId}/{restaurantId}")
    @Operation(summary = "Assign user to restaurant",description = "")
    public SimpleResponse assign(@PathVariable Long userId, @PathVariable Long restaurantId){
         restaurantService.assignUserToRestaurant(userId,restaurantId);
         return SimpleResponse.builder()
                 .httpStatus(HttpStatus.OK)
                 .message(String.format("User with id:"+userId+" successfully assigned to restaurant..."))
                 .build();
    }

    @PutMapping("/updateRestaurant/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update restaurant",description = "")
    public SimpleResponse updateRestaurantById(@PathVariable Long restaurantId, @RequestBody RestaurantRequest restaurantRequest) {
        restaurantService.updateRestaurant(restaurantId,restaurantRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name: %s successfully updated!", restaurantRequest.getName()))
                .build();
    }
    @DeleteMapping("/deleteRestaurant/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete restaurant by id",description = "")
    public SimpleResponse deleteRestaurantById(@PathVariable Long restaurantId){
        restaurantService.deleteRestaurant(restaurantId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant with id:"+restaurantId+" is deleted...")
                .build();
    }

}
