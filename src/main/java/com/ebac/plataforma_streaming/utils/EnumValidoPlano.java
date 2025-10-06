package com.ebac.plataforma_streaming.utils;

import com.ebac.plataforma_streaming.annotations.EnumPlano;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidoPlano implements ConstraintValidator<EnumPlano, String> {

    private String[] valoresPermitidos;

    @Override
    public void initialize(EnumPlano constraintAnnotation) {
        valoresPermitidos = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        for (String v : valoresPermitidos) {
            if (v.equalsIgnoreCase(value)) return true;
        }
        return false;
    }
}
