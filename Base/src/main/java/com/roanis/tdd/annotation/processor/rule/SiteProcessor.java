package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.WithSite;
import com.roanis.tdd.junit4.rules.WithTestSite;


public class SiteProcessor implements AnnotationRuleProcessor {

	@Override
	public TestRule createRule(Annotation annotation) {
		WithSite withSite = (WithSite) annotation;		
		return new WithTestSite(withSite.value());	
	}

}
