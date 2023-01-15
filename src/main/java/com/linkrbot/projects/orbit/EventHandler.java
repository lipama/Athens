package com.linkrbot.projects.orbit;

import java.lang.annotation.*;

/**
 * Used to mark listeners.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
    int priority() default EventPriority.MEDIUM;
}
