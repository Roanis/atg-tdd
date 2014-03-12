package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.WithSite;


public class AnnotationRuleGeneratorImpl implements AnnotationRuleGenerator {
	private static final Map<Class<? extends Annotation>, AnnotationRuleProcessor> supportedAnnotationTypes;
	static {
		supportedAnnotationTypes = new HashMap<Class<? extends Annotation>, AnnotationRuleProcessor>();
		supportedAnnotationTypes.put(WithSite.class, new SiteProcessor());
	}

	@Override
	public List<TestRule> generateRules(Annotation[] annotations) {
		List<TestRule> result = new ArrayList<TestRule>();
		for (Annotation annotation : annotations) {
			AnnotationRuleProcessor ruleProcessor = supportedAnnotationTypes.get(annotation.annotationType());
			if (null != ruleProcessor){
				result.add(ruleProcessor.createRule(annotation));
			}
		}
		return result;
	}

}
