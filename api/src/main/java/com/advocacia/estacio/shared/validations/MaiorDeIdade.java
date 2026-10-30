package com.advocacia.estacio.shared.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaiorDeIdadeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaiorDeIdade {
    String message() default "A pessoa deve ter pelo menos 18 anos.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
