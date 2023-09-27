package peaksoft.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.cheque.ChequeFromWaiterRequest;
import peaksoft.dto.cheque.ChequeRequest;
import peaksoft.dto.cheque.ChequeResponse;
import peaksoft.dto.cheque.GetAllChequeFromRestaurantResponse;
import peaksoft.dto.menuItem.MenuItemResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.repository.dao.cheque.ChequeJDBCTemplateImpl;
import peaksoft.services.ChequeService;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class ChequeApi {
    private final ChequeService chequeService;
    private final ChequeJDBCTemplateImpl chequeJDBCTemplate;


    @PostMapping("/saveCheque/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Save Cheque", description = "")
    public SimpleResponse saveCheque(@PathVariable Long userId, @RequestBody ChequeRequest chequeRequest) {
        chequeService.saveCheque(userId,  chequeRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque in this time" + chequeRequest.getMenuItem() + " successfully saved..."))
                .build();
    }
    @GetMapping("/getInfo/{waiterId}")
    @Operation(summary = "Get water info from one day")
    public ChequeResponse getWaiterInfo(@PathVariable Long waiterId) {
        return chequeJDBCTemplate.getChequeFromUserFromOneDay(waiterId);
    }


    @GetMapping("/getAllChequeFromRestaurant/{restId}")
    @Operation(summary = "Get  cheque from restaurant")
    public GetAllChequeFromRestaurantResponse getChequeFromRestaurant(@PathVariable Long restId) {
        return chequeJDBCTemplate.getAllChequeFromRes(restId);
    }



    @PostMapping("{chequeId}/{menuId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update cheque", description = "")
    public SimpleResponse updateCheque(@PathVariable Long chequeId, @PathVariable Long menuId, @PathVariable Long userId, @RequestBody ChequeRequest chequeRequest) {
        chequeService.updateCheque(chequeId, menuId, userId, chequeRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque with date: %s successfully updated!", ZonedDateTime.now()))
                .build();
    }

    @DeleteMapping("/deleteCheque/{chequeId}/{waiterId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete cheque by id", description = "")
    public SimpleResponse deleteCheque(@PathVariable Long chequeId,  @PathVariable Long waiterId) {
        chequeService.deleteCheque(chequeId, waiterId);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Menu with id:" + chequeId + " is deleted...")
                .build();
    }

}
