package peaksoft.services;

import peaksoft.dto.restaurant.RestaurantRequest;
import peaksoft.dto.restaurant.RestaurantResponse;
import peaksoft.dto.simple.SimpleResponse;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);
    SimpleResponse assignUserToRestaurant(Long userId, Long restaurantId);
    RestaurantResponse getRestaurantById(Long restaurantId);

    SimpleResponse updateProduct(Long restaurantId, RestaurantRequest restaurantRequest);
    SimpleResponse deleteProduct(Long restaurantId);

}
