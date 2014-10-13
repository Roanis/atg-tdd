package com.roanis.tdd.junit4.runner;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.Statement;

import com.roanis.tdd.annotation.RunNucleus;
import com.roanis.tdd.junit4.rules.NucleusWithModules;

/**
 * A custom JUnit4 {@link Suite} runner, which looks for the {@link RunNucleus} annotation. If found,
 * a new {@link NucleusWithModules} rule is added to the suite, so that Nucleus is started/stopped
 * before and after the test suite.
 * 
 * @author rory
 *
 */
public class NucleusAwareSuite extends Suite {
			
	public NucleusAwareSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
		super(klass, builder);
	}
	
	public NucleusAwareSuite(RunnerBuilder builder, Class<?>[] classes) throws InitializationError {
		super(builder, classes);
    }
		
	protected NucleusAwareSuite(Class<?> klass, Class<?>[] suiteClasses)
			throws InitializationError {
		super(klass, suiteClasses);		
	}
	
	protected NucleusAwareSuite(RunnerBuilder builder, Class<?> klass, Class<?>[] suiteClasses) throws InitializationError {
        super(builder, klass, suiteClasses);
    }

   
    protected NucleusAwareSuite(Class<?> klass, List<Runner> runners) throws InitializationError {
        super(klass, runners);        
    }

	@Override
	protected void collectInitializationErrors(List<Throwable> errors) {		
		super.collectInitializationErrors(errors);
		validateAnnotations(errors);
	}
	
	/**
	 * Ensure that a {@link RunNucleus} annotation has been specified for this Suite runner.
	 * 
	 * @param errors
	 */
	private void validateAnnotations(List<Throwable> errors){				
		if (null == getTestClass().getJavaClass().getAnnotation(RunNucleus.class)){
			errors.add(new Exception("No Nucleus modules were specified, via a @RunNucleus annotation, e.g. @RunNucleus(modules=\"DCS\")"));
			return;
		}				
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runners.ParentRunner#classBlock(org.junit.runner.notification.RunNotifier)
	 * 
	 * Add pre/post processing to start/stop Nucleus.
	 */
	@Override
	protected Statement classBlock(RunNotifier notifier) {		
		Statement statement = super.classBlock(notifier);
		statement = withNucleus(statement);
		return statement;
	}
	
	/**
	 * Create a {@link NucleusWithModules} test rule and add it to the list of rules for this suite.
	 * 
	 * @param statement
	 * @return
	 */
	protected Statement withNucleus(Statement statement) {		
		List<TestRule> nucleusRules = new ArrayList<TestRule>(1);
		
		// Add a class rule to start/stop Nucleus
		RunNucleus withNucleus = getTestClass().getJavaClass().getAnnotation(RunNucleus.class);
		nucleusRules.add(new NucleusWithModules(withNucleus.modules(), withNucleus.isUseTestConfigLayer(), getTestClass().getJavaClass()));
		
		return new RunRules(statement, nucleusRules, getDescription());
	}

}
