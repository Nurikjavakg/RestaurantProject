package peaksoft.services;

import peaksoft.dto.restaurant.RestaurantRequest;
import peaksoft.dto.restaurant.RestaurantResponse;
import peaksoft.dto.simple.SimpleResponse;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    SimpleResponse assignUserToRestaurant(Long userId, Long restaurantId);
    RestaurantResponse getRestaurantById(Long restaurantId);

    SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest);
    SimpleResponse deleteRestaurant(Long restaurantId);

}
