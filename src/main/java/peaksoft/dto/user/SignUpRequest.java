package peaksoft.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.enums.Role;
import peaksoft.validation.PasswordValidatian;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class SignUpRequest{
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String email;
        @PasswordValidatian
        private String password;
        private Role role;
        private int experience;


}