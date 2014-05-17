package com.roanis.tdd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.roanis.tdd.core.userprofiling.ProfileTestConstants;

/**
 * Indicates that a user should be loaded from the Profile repository 
 * and set as the current user i.e. ServletUtil.getCurrentUserProfile()
 * will return the specified user.
 * 
 * <p>The profile used can be changed by specifying a different id e.g. @NucleusWithProfile("myProfileID").</p>
 * 
 * @author rory
 *
 */
@NucleusTestData
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NucleusWithProfile {
	String value() default ProfileTestConstants.BASE_USER_ID;
	
}
