package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.NucleusWithSite;
import com.roanis.tdd.junit4.rules.SiteData;


public class SiteProcessor implements AnnotationRuleProcessor {

	@Override
	public TestRule createRule(Annotation annotation) {
		NucleusWithSite withSite = (NucleusWithSite) annotation;		
		return new SiteData(withSite.value());	
	}

}
