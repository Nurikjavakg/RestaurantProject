package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.menuItem.MenuItemResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.stopList.StopListRequest;
import peaksoft.dto.stopList.StopListResponse;
import peaksoft.repository.dao.stopList.StopListJDBCTemplateImpl;
import peaksoft.services.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class StopListApi {
    private final StopListService stopListService;
    private final StopListJDBCTemplateImpl stopListJDBCTemplate;

    @PostMapping("/{menuId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Save StopList", description = "")
    public SimpleResponse saveStopList(@PathVariable Long menuId, @RequestBody StopListRequest stopListRequest) {
        stopListService.saveStopList(menuId,stopListRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("StopList with name: %s successfully saved!",stopListRequest.getReason()))
                .build();
    }


    @PutMapping("/update/{stopListId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update stopList",description = "")
    public SimpleResponse updateStopList(@PathVariable Long stopListId, @RequestBody StopListRequest stopListRequest) {
        stopListService.updateStopList(stopListId,stopListRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("StopList with name: %s successfully updated!", stopListRequest.getReason()))
                .build();
    }
    @DeleteMapping("/delete/stopList/{stopListId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete stopList by id",description = "")
    public SimpleResponse deleteStopList(@PathVariable Long stopListId){
        stopListService.deleteStopList(stopListId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("StopList with id:"+stopListId+" is deleted...")
                .build();
    }

    @GetMapping("/getAllStopList")
    @Operation(summary = "Get all stop list")
    public List<StopListResponse> getByFilterVegetarian() {
        return stopListJDBCTemplate.getSubCategoriesCategoryById();
    }



}
