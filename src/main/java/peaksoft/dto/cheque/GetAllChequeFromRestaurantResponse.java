package peaksoft.dto.cheque;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAllChequeFromRestaurantResponse {
    private String name;
    private int sumCheque;

    public GetAllChequeFromRestaurantResponse(String name, int sumCheque) {
        this.name = name;
        this.sumCheque = sumCheque;
    }
}
