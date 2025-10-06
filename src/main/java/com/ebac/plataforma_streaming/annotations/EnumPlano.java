package com.ebac.plataforma_streaming.annotations;

import com.ebac.plataforma_streaming.utils.EnumValidoPlano;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidoPlano.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumPlano {
    String message() default "Valor de plano inválido. Deve ser um dos valores: BASIC, STANDARD ou PREMIUM";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass();
}
