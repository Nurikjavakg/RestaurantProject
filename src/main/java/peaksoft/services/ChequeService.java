package peaksoft.services;

import peaksoft.dto.cheque.ChequeFromWaiterRequest;
import peaksoft.dto.cheque.ChequeRequest;
import peaksoft.dto.cheque.ChequeResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.stopList.StopListRequest;

import java.util.List;

public interface ChequeService {
    SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest);
    SimpleResponse updateCheque(Long chequeId, Long menuId, Long waiterId,ChequeRequest chequeRequest);
    SimpleResponse deleteCheque(Long chequeId, Long waiterId);
}
