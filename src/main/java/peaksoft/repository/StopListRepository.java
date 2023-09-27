package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.entities.StopList;
@Repository
public interface StopListRepository extends JpaRepository<StopList, Long> {
    StopList getStopListsByReason(String reason);
}