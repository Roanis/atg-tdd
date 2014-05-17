package com.roanis.tdd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that Nucleus should be started, with a set of modules. Note, the modules
 * must be provided, as there are no defaults e.g @RunNucleus(modules="MyModule")<br/>
 * 
 * <p>This will typically be used in a top level {@link TestSuite}, so that Nucleus is only
 * started/stopped once per test run.</p>
 * 
 * @author rory
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface RunNucleus {
	public abstract String[] modules();
	public boolean isTransactional() default true;
}
