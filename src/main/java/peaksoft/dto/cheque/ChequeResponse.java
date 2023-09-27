package peaksoft.dto.cheque;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.menuItem.MenuItemResponse;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChequeResponse {
    private String fullName;
    private BigDecimal priceAverage;
    private int service;
    private BigDecimal grandTotal;
    private List<MenuItemResponse> menus;


    public ChequeResponse(String fullName, BigDecimal priceAverage, int service, BigDecimal grandTotal) {
        this.fullName = fullName;
        this.priceAverage = priceAverage;
        this.service = service;
        this.grandTotal = grandTotal;
    }
}
