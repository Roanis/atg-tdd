package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.NucleusWithCommerce;
import com.roanis.tdd.junit4.rules.CommerceData;

public class CommerceProcessor implements AnnotationRuleProcessor {

	@Override
	public TestRule createRule(Annotation annotation) {
		NucleusWithCommerce withCommerce = (NucleusWithCommerce) annotation;
		return 	new CommerceData.Builder().site(withCommerce.site()).profile(withCommerce.profile())
					.catalog(withCommerce.catalog()).priceList(withCommerce.priceList()).salePriceList(withCommerce.salePriceList())
					.order(withCommerce.order()).build();
	}

	

}
