package peaksoft.repository.dao.menuItem;

import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.menuItem.MenuItemResponse;

import java.util.List;

public interface MenuItemJDBCTemplate {
    List<MenuItemResponse> getMenuByAlphabet(String alphabet);
    List<MenuItemResponse>getSortByPrice(String ascOrDesc);
    List<MenuItemResponse>getByFilterMenu(Boolean filter);
}
