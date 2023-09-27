package peaksoft.dto.menuItem;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
public class MenuItemResponse {
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private Boolean isVegetarian;

    public MenuItemResponse(Long id, String name, String image, BigDecimal price, String description, Boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
