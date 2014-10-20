package com.roanis.tdd.junit4.rules;

import java.util.Arrays;
import java.util.List;

import org.junit.rules.ExternalResource;

import com.roanis.tdd.nucleus.NucleusContext;
import com.roanis.tdd.nucleus.TddNucleusTestUtils;

import atg.nucleus.Nucleus;

/**
 * An {@link org.junit.rules.ExternalResource} {@link TestRule}, which starts Nucleus in the
 * before method.
 * 
 * @author rory
 *
 */
public class NucleusWithModules extends ExternalResource {
	private String[] mModules;
	private Nucleus mNucleus = null;
	private Class<?> mTestClass;
	private boolean mUseTestConfigLayer;

	public NucleusWithModules(String[] modules, boolean useTestConfigLayer, Class<?> testClass){
		mModules = modules;
		mTestClass = testClass;
		mUseTestConfigLayer = useTestConfigLayer;
	}
	
	@Override
	protected void before() throws Throwable {
		if(! NucleusContext.isNucleusRunning()){
			mNucleus = TddNucleusTestUtils.startNucleus(Arrays.asList(mModules), mUseTestConfigLayer, mTestClass);
			updateNucleusContext();
		} else {
			validateModules();	
			TddNucleusTestUtils.enhanceManifestFiles(Arrays.asList(mModules));
		}
	}

	protected void updateNucleusContext() {
		NucleusContext.getCurrent().setGlobalNucleus(mNucleus);
		NucleusContext.getCurrent().setRunningModules(Arrays.asList(mModules));
		NucleusContext.getCurrent().setIsUsingTestConfig(mUseTestConfigLayer);
	}
	
	protected void validateModules() {
		List<String> runningModules = NucleusContext.getCurrent().getRunningModules();
		if(! runningModules.equals(Arrays.asList(mModules))){
			throw new RuntimeException("Nuclues is already running with a different set of modules than those requested. Running=["+runningModules+"]. Requested=["+mModules+"]");
		}		
	}	
	
	@Override
	protected void after() {
		// No longer shutdown Nucleus from here, as it could have been invoked from either a class or a suite.
		// Don't want to restart after every individual test class. However, make sure the manifest is reset,
		// so that it's always correct after every test class.
		TddNucleusTestUtils.restoreAllManifestFiles(Arrays.asList(mModules));
	}

}
