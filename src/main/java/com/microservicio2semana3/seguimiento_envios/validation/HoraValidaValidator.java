package com.microservicio2semana3.seguimiento_envios.validation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

public class HoraValidaValidator implements ConstraintValidator<HoraValida, String> {

    @Override
    public boolean isValid(String hora, ConstraintValidatorContext context) {
        if (hora == null || hora.isEmpty()) return false;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false); // No permite horas como 25:61

        try {
            sdf.parse(hora);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

