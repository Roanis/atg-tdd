package com.roanis.tdd.junit4.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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

import com.google.common.collect.Lists;
import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.annotation.RunNucleus;
import com.roanis.tdd.annotation.processor.rule.AnnotationTestRuleGenerator;
import com.roanis.tdd.annotation.processor.rule.AnnotationTestRuleGeneratorImpl;
import com.roanis.tdd.core.TestContext;
import com.roanis.tdd.junit4.rules.NucleusComponentRule;
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
	private TestContext mTestContext;
	

	public NucleusAwareJunit4ClassRunner(Class<?> klass) throws InitializationError {
		super(klass);	
		mTestContext = new TestContext(klass);
	}
	
	@Override
	protected Object createTest() throws Exception {
		Object instance = super.createTest();
		prepareTest(instance);
		return instance;
	}
	
	protected void prepareTest(Object instance) {
		mTestContext.setTestInstance(instance);
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
			statement = withRuleChain(statement);
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
	protected Statement withRuleChain(Statement statement) {	
		RunNucleus annotation = getTestClass().getJavaClass().getAnnotation(RunNucleus.class);
		if (null == annotation){
			return statement;
		}
				
		return createRuleChain(statement, annotation);		
	}
	
	/**
	 * Creates a {@link RuleChain} with {@link NucleusWithModules} as the first rule, so 
	 * that it gets started before any attempt to load test data, or resolve components. 
	 * Subsequent @NucleusWithXXX annotations, used for setting up test data, are added after 
	 * {@link NucleusWithModules}, as are {@link NucleusComponent} rules.
	 * 
	 * @param statement
	 * @param testNucleus
	 * @return
	 */
	protected Statement createRuleChain(Statement statement, RunNucleus testNucleus) {
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
	
	/* (non-Javadoc)
	 * @see org.junit.runners.BlockJUnit4ClassRunner#withBefores(org.junit.runners.model.FrameworkMethod, java.lang.Object, org.junit.runners.model.Statement)
	 * 
	 * Make sure any fields annotated with @NucleusComponent are injected before the test
	 * method runs. A RuleChain is created to make sure that component injection runs first.
	 */
	@Override
	protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
		Statement junitBefores = super.withBefores(method, target, statement);
		List<TestRule> chainedRules = new ArrayList<TestRule>(1);
		RuleChain chain = RuleChain.emptyRuleChain();
		
		for (TestRule nucleusComponentInjectionRule : nucleusComponenInjectionRules()) {
			chain = chain.around(nucleusComponentInjectionRule);
		}
		
		chainedRules.add(chain);		
		return new RunRules(junitBefores, chainedRules, getDescription());
	}
	
	protected Statement withNucleusComponentRules(Statement statement) {
		List<TestRule> nucleusComponentRules = nucleusComponenInjectionRules();
		return nucleusComponentRules.isEmpty() ? statement :
            new RunRules(statement, nucleusComponentRules, getDescription());
	}

	/**
	 * Search for all fields in the Test Class, plus all super classes,
	 * that are annotated with {@code NucleusComponent}. For each field,
	 * create a {@link TestRule} to inject the component into the field.
	 * 
	 * @return a list of TestRules, one for every field that needs a
	 * component injected. An empty list if no fields are found.
	 */
	protected List<TestRule> nucleusComponenInjectionRules() {
		List<TestRule> nucleusComponentRules = Lists.newArrayList();
		Class<?> clazz = getTestContext().getTestInstance().getClass();
		while(clazz != Object.class){
			List<Field> fieldsToInject = scan(clazz, NucleusComponent.class);
			addInjectionRules(fieldsToInject, nucleusComponentRules);
			clazz = clazz.getSuperclass();
		}
		
		return nucleusComponentRules;
	}
	
	/**
	 * Scan the given class for fields annotated with the given annotation.
	 * 
	 * @param clazz the class to scan
	 * @param ann the annotation to look for
	 * @return the list of Fields which contain the annotation.
	 */
	protected List<Field> scan(Class<?> clazz, Class<? extends Annotation> ann){
		List<Field> result = Lists.newArrayList();
		for(Field field  : clazz.getDeclaredFields()) {
		    if (field.isAnnotationPresent(ann)) {
		    	result.add(field);
		    }
		}
		return result;
	}
	
	/**
	 * Create a {@link NucleusComponentRule} for each field, detailing which component
	 * should be injected into that field. This uses the current test instance from
	 * {@link TestContext} i.e. the current instantiated test class.
	 * 
	 * @param fieldsToInject a list of Field objects that need a component injected.
	 * @param nucleusComponentRules a list of test rules, which gets updated with a new
	 * rule for each Field.
	 */
	protected void addInjectionRules(List<Field> fieldsToInject, List<TestRule> nucleusComponentRules) {		
		if(!fieldsToInject.isEmpty()){
			for(Field field  : fieldsToInject) {
				NucleusComponent annotation = (NucleusComponent) field.getAnnotation(NucleusComponent.class);
				nucleusComponentRules.add(new NucleusComponentRule(field, getTestContext().getTestInstance(), annotation.value())	);
			}
		}		
	}	
	
	public TestContext getTestContext() {
		return mTestContext;
	}

	public void setTestContext(TestContext pTestContext) {
		mTestContext = pTestContext;
	}
	
}
