package com.roanis.tdd.junit4.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.RuleChain;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.roanis.tdd.annotation.RunNucleus;
import com.roanis.tdd.annotation.processor.rule.AnnotationTestRuleGenerator;
import com.roanis.tdd.annotation.processor.rule.AnnotationTestRuleGeneratorImpl;
import com.roanis.tdd.junit4.rules.NucleusWithModules;
import com.roanis.tdd.junit4.rules.NucleusWithTransaction;

public class NucleusAwareJunit4ClassRunner extends BlockJUnit4ClassRunner {	

	public NucleusAwareJunit4ClassRunner(Class<?> klass) throws InitializationError {
		super(klass);	
	}
	
	@Override
	protected Statement methodBlock(FrameworkMethod method) {		
		Statement statement = super.methodBlock(method);
		statement = withTransaction(method, statement);
		return statement;
	}
	
	protected Statement withTransaction(FrameworkMethod method, Statement statement) {
		List<TestRule> transactionRules = new ArrayList<TestRule>(1);
		transactionRules.add(new NucleusWithTransaction());
		return new RunRules(statement, transactionRules, describeChild(method));
	}

	@Override
	protected Statement classBlock(RunNotifier notifier) {		
		Statement statement = super.classBlock(notifier);
		
		if (hasRunNucleusAnnotation()){
			statement = withNucleusAndClassData(statement);
		} else {
			statement = withClassData(statement);
		}		
		
		return statement;
	}

	protected boolean hasRunNucleusAnnotation() {
		return null != getTestClass().getJavaClass().getAnnotation(RunNucleus.class);
	}
	
	protected Statement withNucleusAndClassData(Statement statement) {	
		RunNucleus testNucleus = getTestClass().getJavaClass().getAnnotation(RunNucleus.class);
		if (null == testNucleus){
			return statement;
		}
		
		// Nucleus needs to be started before test data can be loaded. Do this via a RuleChain to
		// guarantee ordering.
		return chainNucleusWithData(statement, testNucleus);		
	}
	
	protected Statement chainNucleusWithData(Statement statement, RunNucleus testNucleus) {
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
		AnnotationTestRuleGenerator ruleGenerator = new AnnotationTestRuleGeneratorImpl();
		return ruleGenerator.generateRules(getTestClass().getAnnotations());
	}
	

}
