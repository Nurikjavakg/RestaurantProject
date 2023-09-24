package peaksoft.dto.menuItem;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class MenuItemRequest {
    private String name;
    private String image;
    @Positive
    private BigDecimal price;
    private String description;
    private Boolean isVegetarian;

    public MenuItemRequest(String name, String image, BigDecimal price, String description, Boolean isVegetarian) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
