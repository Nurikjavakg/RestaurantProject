package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import peaksoft.entities.User;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> getUserByEmail(String email);


    User findByEmail(String email);

   @Query("select count (u.role) from User u where u.role =:role")
    Long chefCount(Role role);

}
