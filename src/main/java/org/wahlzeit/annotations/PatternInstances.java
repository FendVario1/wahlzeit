package org.wahlzeit.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE_USE, ElementType.METHOD})
public @interface PatternInstances {
    PatternInstance[] value();
}
