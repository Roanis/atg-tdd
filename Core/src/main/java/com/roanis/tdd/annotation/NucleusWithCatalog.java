package com.roanis.tdd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;

@NucleusTestData
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NucleusWithCatalog {
	String value() default CatalogTestConstants.BASE_CATALOG_ID;
}
