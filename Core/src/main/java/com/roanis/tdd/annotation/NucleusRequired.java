package com.roanis.tdd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that Nucleus should be started, with a set of modules. Note, the modules
 * must be provided, as there are no defaults e.g {@code @NucleusRequired(modules="MyModule")}<br/>
 * 
 * <p>If Nucleus is already running, then this annotation has no effect i.e. only the first
 * occurrence of it, in all the test classes, will be used to start Nucleus. </p>
 * 
 *<p>If {@code isUseTestConfigLayer} is true (the default) then the Manifest file of each module
 * being started will be updated with {@code ATG-testConfig-Path: testconfig} during Nucleus startup. 
 * This enables each module to contain it's own unit tests and configuration. The Manifest file
 * is restored after each test run.</p>
 * 
 * @author rory
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface NucleusRequired {
	public abstract String[] modules();
	public boolean isTransactional() default true;
	public boolean isUseTestConfigLayer() default true;
}
