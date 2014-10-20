package com.roanis.tdd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.roanis.tdd.core.multisite.SiteTestConstants;

/**
 * Indicates that a site should be loaded from the Site repository
 * and set as the current site i.e. SiteContextManager.getCurrentSite
 * will return the specified site.
 * 
 * <p>The site used can be changed by specifying a different id e.g. @NucleusWithSite("mySiteID").</p>
 * 
 * @author rory
 *
 */
@NucleusTestSetup
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NucleusWithSite {
	
	String value() default SiteTestConstants.BASE_SITE_ID;

}
