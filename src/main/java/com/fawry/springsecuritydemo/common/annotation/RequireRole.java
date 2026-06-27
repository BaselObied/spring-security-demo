package com.fawry.springsecuritydemo.common.annotation;

import com.fawry.springsecuritydemo.model.enumeration.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireRole {
    Role value();
}
