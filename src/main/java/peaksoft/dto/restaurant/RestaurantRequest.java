package peaksoft.dto.restaurant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequest {
    private String name;
    private String location;
    private String restType;
    private int Service;

}
