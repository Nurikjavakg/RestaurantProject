package peaksoft.dto.userInfo;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;

import java.time.LocalDate;
import java.time.ZonedDateTime;
@Getter
@Setter
public class UserResponseInfo{
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;

    private Role role;
    private int experience;

    public UserResponseInfo(Long id, String firstName, String lastName, LocalDate dateOfBirth, String email, String password, Role role, int experience) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.role = role;
        this.experience = experience;
    }
}
