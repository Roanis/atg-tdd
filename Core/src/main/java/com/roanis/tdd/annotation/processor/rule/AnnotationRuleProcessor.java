package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

/**
 * An interface for converting Annotations into JUnit test rules.
 * 
 * @author rory
 *
 */
public interface AnnotationRuleProcessor  {
	
	/**
	 * Create a TestRule for the given annotation.
	 * 
	 * @param annotation - one of the TDD annotations e.g. {@link NucleusWithCatalog}, {@link NucleusWithSite}, etc.
	 * @return - a JUnit {@link TestRule}, which represents what action to perform based on the annotation.
	 */
	public TestRule createRule(Annotation annotation);		

}
