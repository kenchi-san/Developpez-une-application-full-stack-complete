package com.openclassrooms.mddapi.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Mot de passe invalide : min 8 caractères, au moins une majuscule, une minuscule, un chiffre, et un caractère spécial.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}