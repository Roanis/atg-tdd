package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.NucleusWithSite;
import com.roanis.tdd.junit4.rules.SiteData;


/**
 * An {@link AnnotationRuleProcessor} implementation, which handles {@link NucleusWithSite} annotations.
 * A {@link SiteData} TestRule is created from the information provided in the annotation.
 * 
 * @author rory
 *
 */
public class SiteProcessor implements AnnotationRuleProcessor {

	@Override
	public TestRule createRule(Annotation annotation) {
		NucleusWithSite withSite = (NucleusWithSite) annotation;		
		return new SiteData(withSite.value());	
	}

}
