package com.hotstrip.jbnode.common.annotations;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CalcExecTime {
    boolean value() default true;
}
