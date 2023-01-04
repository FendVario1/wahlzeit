package org.wahlzeit.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE_USE, ElementType.METHOD})
@Repeatable(PatternInstances.class)
public @interface PatternInstance {
    String patternName();
    String[] participants();
}
