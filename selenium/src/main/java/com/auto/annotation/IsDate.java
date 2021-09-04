package com.auto.annotation;


import com.auto.validator.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author little 校验时间是否为合法
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface IsDate {

    String message() default "时间格式不正确";

    String formatter() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}