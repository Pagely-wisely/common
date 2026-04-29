package com.pagely.common.auth.annotation;

import com.pagely.common.auth.Role;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {
    Role[] role() default {};
}
