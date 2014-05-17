package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.rules.TestRule;

/**
 * An interface for generating a collection of test rules, based on a collection
 * of annotations.
 * 
 * @author rory
 *
 */
public interface AnnotationTestRuleGenerator {
	
	
	/**
	 * Iterate each annotation and create a JUnit {@link TestRule} for it.
	 * 
	 * @param annotation - one of the TDD annotations e.g. {@link com.roanis.tdd.annotation.NucleusWithCatalog}, 
	 * {@link com.roanis.tdd.annotation.NucleusWithSite}, etc.
	 * @return - a collection of JUnit {@link TestRule} objects i.e. one for each annotation.
	 */
	public List<TestRule> generateRules(Annotation[] annotation);		
}
