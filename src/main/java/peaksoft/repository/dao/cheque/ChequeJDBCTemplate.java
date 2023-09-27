package peaksoft.repository.dao.cheque;

import peaksoft.dto.cheque.ChequeFromWaiterRequest;
import peaksoft.dto.cheque.ChequeResponse;
import peaksoft.dto.cheque.GetAllChequeFromRestaurantResponse;
import peaksoft.entities.Restaurant;
import peaksoft.entities.User;

import java.util.List;

public interface ChequeJDBCTemplate {
    int countMenu(Long menuId);
    ChequeResponse getChequeFromUserFromOneDay(Long userId);
    GetAllChequeFromRestaurantResponse getAllChequeFromRes(Long restId);
}
