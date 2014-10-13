package com.roanis.tdd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field as a Nucleus component, which gets resolved
 * using the string value, as the path to the component. For example
 * {@code @NucleusComponent("/atg/userprofiling/Profile")} would attempt
 * to resolve the current ATG Profile component.
 * 
 * @author rory
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NucleusComponent {
	// The path to the component
	String value();
}
