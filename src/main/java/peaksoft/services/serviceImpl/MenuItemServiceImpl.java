package peaksoft.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.menuItem.MenuItemRequest;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.entities.MenuItem;
import peaksoft.entities.Restaurant;
import peaksoft.entities.SubCategory;
import peaksoft.exceptions.InvalidNameException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.services.MenuItemService;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public SimpleResponse saveMenuItem(Long restaurantId, Long subCategoryId,MenuItemRequest menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant with id:" + restaurantId + " not found..."));

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(()-> new NotFoundException("SubCategory with id:"+subCategoryId+" not found"));

        MenuItem menuItemName = menuItemRepository.findMenuItemByName(menuItemRequest.getName());
        if (menuItemName == null) {
            MenuItem menuItem = new MenuItem();
            menuItem.setName(menuItemRequest.getName());
            menuItem.setImage(menuItemRequest.getImage());
            menuItem.setPrice(menuItemRequest.getPrice());
            menuItem.setDescription(menuItemRequest.getDescription());
            menuItem.setIsVegetarian(menuItemRequest.getIsVegetarian());
            menuItem.setRestaurant(restaurant);
            restaurant.getMenus().add(menuItem);
            subCategory.getMenus().add(menuItem);
            menuItem.setSubCategory(subCategory);
            menuItemRepository.save(menuItem);
            restaurantRepository.save(restaurant);

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format("Menu with name:" + menuItemName + " successfully assigned to restaurant..."))
                    .build();
        } else {
            log.info(String.format("Menu with name: %s exists", menuItemRequest.getName()));
            throw new InvalidNameException(String.format("Menu with name: %s exists", menuItemRequest.getName()));

        }
    }

    @Override
    public SimpleResponse assignMenuItemToSubCategory(Long subCategoryId, Long menuItemId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new NotFoundException("SubCategory with id:" + subCategoryId + " not found..."));

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundException("Menu with id:" + menuItemId + " not found..."));

        subCategory.getMenus().add(menuItem);
        menuItem.setSubCategory(subCategory);
        subCategoryRepository.save(subCategory);
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Menu is successfully assigned to subcategory..."))
                .build();
    }


    @Override
    public SimpleResponse updateMenuItemById(Long menuItemId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundException("Menu with id:" + menuItemId + " not found..."));

        menuItem.setName(menuItemRequest.getName());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setIsVegetarian(menuItemRequest.getIsVegetarian());
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Menu is successfully updated..."))
                .build();
    }

    @Override
    public SimpleResponse deleteMenuItem(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new NotFoundException("Menu with id:" + menuItemId + " not found..."));


        menuItemRepository.delete(menuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Menu is successfully deleted..."))
                .build();
    }
}
