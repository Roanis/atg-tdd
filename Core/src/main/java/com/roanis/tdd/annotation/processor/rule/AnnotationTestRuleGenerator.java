package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;
import java.util.List;

import org.junit.rules.TestRule;

public interface AnnotationTestRuleGenerator {
	public List<TestRule> generateRules(Annotation[] annotation);		
}
