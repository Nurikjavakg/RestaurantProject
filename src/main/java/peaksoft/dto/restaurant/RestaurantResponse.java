package peaksoft.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantResponse {
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployees;
    private int Service;

    public RestaurantResponse(Long id, String name, String location, String restType,  int service) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.restType = restType;
        Service = service;
    }
}
