package peaksoft.dto.cheque;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChequeFromWaiterRequest {
    private String fullName;
    private Long chequeId;
    private int sumCheque;

    public ChequeFromWaiterRequest(String fullName, Long chequeId, int sumCheque) {
        this.fullName = fullName;
        this.chequeId = chequeId;
        this.sumCheque = sumCheque;
    }
}
