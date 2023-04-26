package com.example.transaction2.aop;

import com.example.transaction2.entity.enums.PermissionEnum;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface CheckAuth {
    PermissionEnum[] permission() default {};
}
