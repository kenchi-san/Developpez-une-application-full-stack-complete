package com.openclassrooms.mddapi.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])" +          // au moins un chiffre
                    "(?=.*[a-z])" +           // au moins une minuscule
                    "(?=.*[A-Z])" +           // au moins une majuscule
                    "(?=.*[!@#$%^&*()_+=\\-{}\\[\\]:;\"'`~<>.,?/\\\\|])" + // au moins un caractère spécial
                    "(?=\\S+$).{8,}$";        // pas d'espace, min 8 caractères

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.matches(PASSWORD_REGEX);
    }
}