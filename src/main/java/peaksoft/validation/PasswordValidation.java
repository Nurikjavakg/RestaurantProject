package peaksoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


    public class PasswordValidation implements ConstraintValidator<PasswordValidatian,String> {

        @Override
        public boolean isValid(String password, ConstraintValidatorContext context) {
            return password != null && password.length() >= 4;
        }
    }


