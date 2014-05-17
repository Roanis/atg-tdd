package com.roanis.tdd.annotation.processor.rule;

import java.lang.annotation.Annotation;

import org.junit.rules.TestRule;

import com.roanis.tdd.annotation.NucleusWithProfile;
import com.roanis.tdd.junit4.rules.ProfileData;

/**
 * An {@link AnnotationRuleProcessor} implementation, which handles {@link NucleusWithProfile} annotations.
 * A {@link ProfileData} TestRule is created from the information provided in the annotation.
 * 
 * @author rory
 *
 */
public class ProfileProcessor implements AnnotationRuleProcessor {
	
	@Override
	public TestRule createRule(Annotation annotation) {		
		NucleusWithProfile withProfile = (NucleusWithProfile) annotation;
		return new ProfileData(withProfile.value());
	}

}
