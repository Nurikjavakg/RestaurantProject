package peaksoft.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.cheque.ChequeFromWaiterRequest;
import peaksoft.dto.cheque.ChequeRequest;
import peaksoft.dto.cheque.ChequeResponse;
import peaksoft.dto.simple.SimpleResponse;
import peaksoft.dto.stopList.StopListRequest;
import peaksoft.entities.Cheque;
import peaksoft.entities.MenuItem;
import peaksoft.entities.User;
import peaksoft.enums.Role;
import peaksoft.exceptions.AccessDenied;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.UserRepository;
import peaksoft.repository.dao.cheque.ChequeJDBCTemplateImpl;
import peaksoft.services.ChequeService;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChequeServiceImpl implements ChequeService {
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final ChequeRepository chequeRepository;
    private final ChequeJDBCTemplateImpl chequeJDBCTemplate;
    @Override
    public SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id:" + userId + " not found..."));

        if (user.getRole().equals(Role.WAITER) || user.getRole().equals(Role.ADMIN)) {
            List<MenuItem> menuItems = menuItemRepository.findAllById(chequeRequest.getMenuItem());

            BigDecimal totalPrice = BigDecimal.ZERO;
            for (MenuItem menuItem : menuItems) {
                totalPrice = totalPrice.add(menuItem.getPrice());
            }

            Cheque cheque = new Cheque();

            cheque.setCreatedAt(ZonedDateTime.now());
            cheque.setPriceAverage(totalPrice);
            cheque.setUser(user);
            user.setChequeList(List.of(cheque));
            cheque.setMenus(menuItems);
            menuItems.forEach(menuItem -> {
                menuItem.setChequeList(List.of(cheque));
            });

            chequeRepository.save(cheque);
        } else {
            throw new AccessDenied("Cheque can save only waiters");
        }
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message(String.format("Cheque in this time" + ZonedDateTime.now() + " successfully saved..."))
                    .build();


    }
    @Override
    public SimpleResponse updateCheque(Long chequeId, Long menuId, Long waiterId,ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(chequeId)
                .orElseThrow(()-> new NotFoundException("Cheque with id:"+chequeId+" not found..."));

        MenuItem menuItem = menuItemRepository.findById(menuId)
                .orElseThrow(()-> new NotFoundException("Menu with id:"+menuId+" not found..."));

        User user = userRepository.findById(waiterId)
                .orElseThrow(()-> new NotFoundException("Waiter with id:"+waiterId+" not found..."));

        List<MenuItem> menuItems = new ArrayList<>(List.of(menuItem));

        cheque.setUpdatedAt(ZonedDateTime.now());

        cheque.setUser(user);
        cheque.setMenus(menuItems);
        chequeRepository.save(cheque);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque in this time" + ZonedDateTime.now() + " successfully updated..."))
                .build();
    }

    @Override
    public SimpleResponse deleteCheque(Long chequeId, Long waiterId) {
        User user = userRepository.findById(waiterId)
                .orElseThrow(() -> new NotFoundException("User with id:" + waiterId + " not found..."));

        Cheque cheque = chequeRepository.findById(chequeId)
                .orElseThrow(() -> new NotFoundException("Cheque with id:" + chequeId + " not found..."));

        user.getChequeList().remove(cheque);
        cheque.setMenus(null);
        chequeRepository.delete(cheque);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque with id " + chequeId + " successfully deleted..."))
                .build();
    }




}
