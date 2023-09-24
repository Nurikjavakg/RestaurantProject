package peaksoft.repository;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import peaksoft.dto.userInfo.UserResponseInfo;
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
    @Query("SELECT NEW peaksoft.dto.userInfo.UserResponseInfo(u.id, u.firstName, u.lastName,u.dateOfBirth, u.email, u.password, u.role, u.experience) FROM Restaurant r join r.users u")
    Page<UserResponseInfo> getAllCompaniesUser( Pageable pageable);
    @Query("SELECT NEW peaksoft.dto.userInfo.UserResponseInfo(u.id, u.firstName, u.lastName,u.dateOfBirth, u.email, u.password, u.role, u.experience) FROM Restaurant r join r.users u where u.id = :userId")
    Optional<UserResponseInfo> getUserById(Long userId);
}
