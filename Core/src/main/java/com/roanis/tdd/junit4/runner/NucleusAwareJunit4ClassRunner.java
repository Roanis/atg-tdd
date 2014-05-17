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

/**
 * An extension of {@link BlockJUnit4ClassRunner} which looks for any {@link com.roanis.tdd.annotation} on the class
 * and generates a TestRule from the annotation.
 * 
 * @author rory
 *
 */
public class NucleusAwareJunit4ClassRunner extends BlockJUnit4ClassRunner {	

	public NucleusAwareJunit4ClassRunner(Class<?> klass) throws InitializationError {
		super(klass);	
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.BlockJUnit4ClassRunner#methodBlock(org.junit.runners.model.FrameworkMethod)
	 * 
	 * Wrap each method in a transaction and rollback afterwards.
	 */
	@Override
	protected Statement methodBlock(FrameworkMethod method) {		
		Statement statement = super.methodBlock(method);
		statement = withTransaction(method, statement);
		return statement;
	}
	
	/**
	 * Create a {@link NucleusWithTransaction} {@link TestRule} and add it to the
	 * list of rules for the test class.
	 * 
	 * @param method
	 * @param statement
	 * @return
	 */
	protected Statement withTransaction(FrameworkMethod method, Statement statement) {
		List<TestRule> transactionRules = new ArrayList<TestRule>(1);
		transactionRules.add(new NucleusWithTransaction());
		return new RunRules(statement, transactionRules, describeChild(method));
	}

	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#classBlock(org.junit.runner.notification.RunNotifier)
	 * 
	 * Look for any annotations at Class level and generate a TestRule for them. If @RunNucleus is found
	 * on the class (i.e. when running/debugging an individual class) then chain the rules so that
	 * Nucleus is started first and then the test data is set up.
	 */
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
	
	/**
	 * Check for the {@link RunNucleus annotation}. If present, make sure Nuclues is the first
	 * rule in the chain, otherwise just exit with the existing set of rules.
	 * 
	 * @param statement
	 * @return
	 */
	protected Statement withNucleusAndClassData(Statement statement) {	
		RunNucleus testNucleus = getTestClass().getJavaClass().getAnnotation(RunNucleus.class);
		if (null == testNucleus){
			return statement;
		}
		
		// Nucleus needs to be started before test data can be loaded. Do this via a RuleChain to
		// guarantee ordering.
		return chainNucleusWithData(statement, testNucleus);		
	}
	
	/**
	 * Creates a {@link RuleChain} with {@link NucleusWithModules} as the first rule, so 
	 * that it gets started before any attempt to load test data. Subsequent @NucleusWithXXX annotations,
	 * used for setting up test data, are added after @NucleusWithModules.
	 * 
	 * @param statement
	 * @param testNucleus
	 * @return
	 */
	protected Statement chainNucleusWithData(Statement statement, RunNucleus testNucleus) {
		List<TestRule> chainedRules = new ArrayList<TestRule>(1);		
		RuleChain chain = RuleChain.outerRule(new NucleusWithModules(testNucleus.modules(), getTestClass().getJavaClass()));
		
		for (TestRule dataRule : classDataRules()) {
			chain = chain.around(dataRule);
		}
                    													
		chainedRules.add(chain);		
		return new RunRules(statement, chainedRules, getDescription());
	}

	/**
	 * Add all Rules, created by @NucleusWithXXX annotations, to the list of test rules
	 * for this class.
	 * 
	 * @param statement
	 * @return
	 */
	protected Statement withClassData(Statement statement) {
		List<TestRule> classDataRules = classDataRules();
		return classDataRules.isEmpty() ? statement :
            new RunRules(statement, classDataRules, getDescription());
	}
	
	/**
	 * Pass all the annotations on the class to an {@link AnnotationTestRuleGenerator}, so that all
	 * the @NucleusWithXXX annotations are handled as test rules.
	 * 
	 * @return
	 */
	protected List<TestRule> classDataRules(){
		AnnotationTestRuleGenerator ruleGenerator = new AnnotationTestRuleGeneratorImpl();
		return ruleGenerator.generateRules(getTestClass().getAnnotations());
	}
	

}
