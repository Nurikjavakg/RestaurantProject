package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.restaurant.RestaurantResponse;
import peaksoft.entities.Restaurant;

import java.util.Optional;

@Repository
public interface


RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT COUNT(u.id) AS employeeCount FROM Restaurant r join r.users u where r.id =?1")
    int countEmployees(Long restaurantId);



    @Query("select new peaksoft.dto.restaurant.RestaurantResponse(r.id,r.name,r.location,r.restType,r.service) from  Restaurant r where r.id = :restaurantId")
    Optional<RestaurantResponse> getRestaurantById(Long restaurantId);


    Optional<Restaurant> findRestaurantById(Long restaurantId);
    @Query("select (r) from Restaurant r where r.id = 1L")
    Long findRestaurantByIdd();

}