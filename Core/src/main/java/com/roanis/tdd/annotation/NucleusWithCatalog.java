package com.roanis.tdd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;

/**
 * Indicates that a catalog should be loaded from the Catalog repository and set as the
 * current catalog i.e. CatalogContext.getCurrentCatalog will return the specified catalog.
 * 
 * <p>The catalog used can be changed by specifying a different id e.g. @NucleusWithCatalog("myCatalogID").</p>
 * 
 * @author rory
 *
 */
@NucleusTestSetup
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NucleusWithCatalog {
	String value() default CatalogTestConstants.BASE_CATALOG_ID;
}
