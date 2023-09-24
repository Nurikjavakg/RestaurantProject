package peaksoft.services;

import peaksoft.dto.menuItem.MenuItemRequest;
import peaksoft.dto.menuItem.MenuItemResponse;
import peaksoft.dto.restaurant.RestaurantResponse;
import peaksoft.dto.simple.SimpleResponse;

import java.util.List;

public interface MenuItemService {
    SimpleResponse saveMenuItem(Long restaurantId, MenuItemRequest menuItemRequest);
    SimpleResponse assignMenuItemToSubCategory(Long subCategoryId, Long menuItemId);
    SimpleResponse updateMenuItemById(Long menuItemId, MenuItemRequest menuItemRequest);
    SimpleResponse deleteMenuItem(Long menuItemId,Long subCategoryId);
}
