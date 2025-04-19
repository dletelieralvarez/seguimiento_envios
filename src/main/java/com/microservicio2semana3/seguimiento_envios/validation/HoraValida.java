package com.microservicio2semana3.seguimiento_envios.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HoraValidaValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HoraValida {
    String message() default "Hora no válida (formato esperado: HH:mm)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
