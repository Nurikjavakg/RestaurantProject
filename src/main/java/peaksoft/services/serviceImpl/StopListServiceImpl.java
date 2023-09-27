package peaksoft.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.stopList.StopListRequest;
import peaksoft.entities.MenuItem;
import peaksoft.entities.StopList;
import peaksoft.exceptions.AlreadyExists;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.services.StopListService;

@Service
@RequiredArgsConstructor
@Slf4j
public class StopListServiceImpl implements StopListService{
    private final MenuItemRepository menuItemRepository;
    private final StopListRepository stopListRepository;
    @Override
    public SimpleResponse saveStopList(Long menuId, StopListRequest stopListRequest) {

        StopList stopList1 = stopListRepository.getStopListsByReason(stopListRequest.getReason());
        MenuItem menuItem = menuItemRepository.findById(menuId)
                .orElseThrow(()-> new NotFoundException("Menu with id:"+menuId+" not found..."));

        if(stopList1==null){
        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(stopListRequest.getDate());
        stopList.setMenu(menuItem);
        stopListRepository.save(stopList);
        menuItem.setStopList(stopList);
        menuItemRepository.save(menuItem);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("StopList with name: %s successfully saved!",stopListRequest.getReason()))
                .build();
    }else {
            throw new AlreadyExists("Check is already exist");
        }
    }

    @Override
    public SimpleResponse updateStopList(Long stopListId, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(stopListId)
                .orElseThrow(()-> new NotFoundException("StopList with id:"+stopListId+ " not found..."));
        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(stopListRequest.getDate());
        stopListRepository.save(stopList);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("StopList with name: %s successfully updated!",stopListRequest.getReason()))
                .build();
    }

    @Override
    public SimpleResponse deleteStopList(Long stopListId) {
        StopList stopList = stopListRepository.findById(stopListId)
                .orElseThrow(()-> new NotFoundException("StopList with id:"+stopListId+ " not found..."));
        stopList.setMenu(null);

        stopListRepository.delete(stopList);
        return null;
    }
}
