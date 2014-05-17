package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.NucleusWithCatalog;
import com.roanis.tdd.annotation.NucleusWithCommerce;
import com.roanis.tdd.annotation.NucleusWithOrder;
import com.roanis.tdd.annotation.NucleusWithProfile;
import com.roanis.tdd.annotation.NucleusWithSite;


/**
 * An implementation of the {@link AnnotationTestRuleGenerator} interface. This class
 * maintains a collection of supported annotation types and uses them to generate
 * the test rules.
 * 
 * @author rory
 *
 */
public class AnnotationTestRuleGeneratorImpl implements AnnotationTestRuleGenerator {
	private static final Map<Class<? extends Annotation>, AnnotationRuleProcessor> supportedAnnotationTypes;
	static {
		supportedAnnotationTypes = new HashMap<Class<? extends Annotation>, AnnotationRuleProcessor>();
		supportedAnnotationTypes.put(NucleusWithSite.class, new SiteProcessor());
		supportedAnnotationTypes.put(NucleusWithProfile.class, new ProfileProcessor());
		supportedAnnotationTypes.put(NucleusWithCatalog.class, new CatalogProcessor());		
		supportedAnnotationTypes.put(NucleusWithOrder.class, new OrderProcessor());	
		supportedAnnotationTypes.put(NucleusWithCommerce.class, new CommerceProcessor());	
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
