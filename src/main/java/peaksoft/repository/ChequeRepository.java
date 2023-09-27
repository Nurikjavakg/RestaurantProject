package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.cheque.ChequeResponse;
import peaksoft.entities.Cheque;

import java.math.BigDecimal;
import java.util.List;
@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select cast(count (m.price)as int )from Restaurant r join r.users u join u.chequeList ch join ch.menus m where m.id = :menuId")
    BigDecimal countMenu(Long menuId);
    @Query("select m.id from MenuItem m where m.id = :menuId")
    List<Long> menus(Long menuId);


}