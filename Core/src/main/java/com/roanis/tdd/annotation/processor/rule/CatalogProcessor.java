package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.NucleusWithCatalog;
import com.roanis.tdd.junit4.rules.CatalogData;

/**
 * An {@link AnnotationRuleProcessor} implementation, which handles {@link NucleusWithCatalog} annotations.
 * A {@link CatalogData} TestRule is created from the information provided in the annotation.
 * 
 * @author rory
 *
 */
public class CatalogProcessor implements AnnotationRuleProcessor {
	
	@Override
	public TestRule createRule(Annotation annotation) {
		NucleusWithCatalog withCatalog = (NucleusWithCatalog) annotation;
		return new CatalogData(withCatalog.value());
	}

}
