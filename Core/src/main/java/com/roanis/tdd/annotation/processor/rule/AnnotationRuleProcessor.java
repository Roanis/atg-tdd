package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

public interface AnnotationRuleProcessor  {
	public TestRule createRule(Annotation annotation);		

}
