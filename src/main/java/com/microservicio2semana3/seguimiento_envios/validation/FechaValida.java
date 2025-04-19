package com.microservicio2semana3.seguimiento_envios.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FechaValidaValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaValida {
    String message() default "Fecha de seguimiento de envio no es válida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
