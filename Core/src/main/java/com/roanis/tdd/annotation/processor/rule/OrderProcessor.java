package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.NucleusWithOrder;
import com.roanis.tdd.junit4.rules.OrderData;

public class OrderProcessor implements AnnotationRuleProcessor {

	@Override
	public TestRule createRule(Annotation annotation) {
		NucleusWithOrder withOrder = (NucleusWithOrder) annotation;
		return new OrderData(withOrder.value());
	}

}
