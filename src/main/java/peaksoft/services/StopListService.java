package peaksoft.services;

import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.stopList.StopListRequest;

public interface StopListService {
    SimpleResponse saveStopList(Long menuId, StopListRequest stopListRequest);
    SimpleResponse updateStopList(Long stopListId, StopListRequest stopListRequest);
    SimpleResponse deleteStopList(Long stopListId);
}
