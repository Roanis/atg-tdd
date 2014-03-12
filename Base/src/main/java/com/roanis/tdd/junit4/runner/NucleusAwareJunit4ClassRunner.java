package com.roanis.tdd.junit4.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.RuleChain;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.roanis.tdd.annotation.Nucleus;
import com.roanis.tdd.annotation.processor.rule.AnnotationRuleGenerator;
import com.roanis.tdd.annotation.processor.rule.AnnotationRuleGeneratorImpl;
import com.roanis.tdd.junit4.rules.NucleusWithModules;

public class NucleusAwareJunit4ClassRunner extends BlockJUnit4ClassRunner {	

	public NucleusAwareJunit4ClassRunner(Class<?> klass) throws InitializationError {
		super(klass);	
	}
	
	@Override
	protected Statement classBlock(RunNotifier notifier) {		
		Statement statement = super.classBlock(notifier);
		
		if (hasNucleusAnnotation()){
			statement = withNucleusAndClassData(statement);
		} else {
			statement = withClassData(statement);
		}		
		
		return statement;
	}

	protected boolean hasNucleusAnnotation() {
		return null != getTestClass().getJavaClass().getAnnotation(Nucleus.class);
	}
	
	protected Statement withNucleusAndClassData(Statement statement) {	
		Nucleus testNucleus = getTestClass().getJavaClass().getAnnotation(Nucleus.class);
		if (null == testNucleus){
			return statement;
		}
		
		// Nucleus needs to be started before test data can be loaded. Do this via a RuleChain to
		// guarantee ordering.
		return chainNucleusWithData(statement, testNucleus);		
	}
	
	protected Statement chainNucleusWithData(Statement statement, Nucleus testNucleus) {
		List<TestRule> chainedRules = new ArrayList<TestRule>(1);		
		RuleChain chain = RuleChain.outerRule(new NucleusWithModules(testNucleus.modules(), getTestClass().getJavaClass()));
		
		for (TestRule dataRule : classDataRules()) {
			chain = chain.around(dataRule);
		}
                    													
		chainedRules.add(chain);		
		return new RunRules(statement, chainedRules, getDescription());
	}

	protected Statement withClassData(Statement statement) {
		List<TestRule> classDataRules = classDataRules();
		return classDataRules.isEmpty() ? statement :
            new RunRules(statement, classDataRules, getDescription());
	}
	
	protected List<TestRule> classDataRules(){
		AnnotationRuleGenerator ruleGenerator = new AnnotationRuleGeneratorImpl();
		return ruleGenerator.generateRules(getTestClass().getAnnotations());
	}
	

}
