package com.oss.pii.demo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Mask {
    public int keepLastDigits() default 0;
    public int keepFirstDigits() default 0;
    public char maskChar() default '#';
}