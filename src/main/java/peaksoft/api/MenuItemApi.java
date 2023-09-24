package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.category.CategoryRequest;
import peaksoft.dto.menuItem.MenuItemRequest;
import peaksoft.dto.menuItem.MenuItemResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.subcategory.SubCategoryResponse;
import peaksoft.repository.dao.menuItem.MenuItemJDBCTemplateImpl;
import peaksoft.services.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class MenuItemApi {
    private final MenuItemService menuItemService;
    private final MenuItemJDBCTemplateImpl menuItemJDBCTemplate;
    @PostMapping("/saveMenuItem/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(summary = "Save category",description = "")
    public SimpleResponse saveMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItemRequest menuItemRequest){
        menuItemService.saveMenuItem(restaurantId,menuItemRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Menu with name: %s successfully saved!",menuItemRequest.getName()))
                .build();
    }

    @PostMapping("/assignMenuItemToSubCategory/{subCategoryId}/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @Operation(summary = "Assign menu to subCategory",description = "")
    public SimpleResponse assignMenuItemToSubCategory(@PathVariable Long subCategoryId, @PathVariable Long menuItemId){
        menuItemService.assignMenuItemToSubCategory(subCategoryId,menuItemId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Menu is assigned to subCategory"))
                .build();
    }
    @GetMapping("/getMenuByAlphabet/{alphabet}")
    @Operation(summary = "Get menu by alphabet")
    public List<MenuItemResponse> getMenuByAlphabet(@PathVariable String alphabet) {
        return menuItemJDBCTemplate.getMenuByAlphabet(alphabet);
    }

    @GetMapping("/getSortByPrice/{ascOrDesc}")
    @Operation(summary = "Get menu by sortByPrice")
    public List<MenuItemResponse> getSortByPrice(@PathVariable String ascOrDesc) {
        return menuItemJDBCTemplate.getSortByPrice(ascOrDesc);
    }

    @GetMapping("/{vegetarian}")
    @Operation(summary = "Get menu by filter vegetarian")
    public List<MenuItemResponse> getByFilterVegetarian(@PathVariable Boolean vegetarian) {
        return menuItemJDBCTemplate.getByFilterMenu(vegetarian);
    }


    @PutMapping("/{menuId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update menu",description = "")
    public SimpleResponse updateMenu(@PathVariable Long menuId, @RequestBody MenuItemRequest menuItemRequest) {
        menuItemService.updateMenuItemById(menuId,menuItemRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Menu with name: %s successfully updated!", menuItemRequest.getName()))
                .build();
    }
    @DeleteMapping("/{menuId}/{subCategoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete menu by id",description = "")
    public SimpleResponse deleteMenu(@PathVariable Long menuId,@PathVariable Long subCategoryId){
        menuItemService.deleteMenuItem(menuId,subCategoryId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Menu with id:"+menuId+" is deleted...")
                .build();
    }
}
