package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.NucleusWithCatalog;
import com.roanis.tdd.junit4.rules.CatalogData;

public class CatalogProcessor implements AnnotationRuleProcessor {

	@Override
	public TestRule createRule(Annotation annotation) {
		NucleusWithCatalog withCatalog = (NucleusWithCatalog) annotation;
		return new CatalogData(withCatalog.value());
	}

}
