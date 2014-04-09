package com.roanis.tdd.junit4.rules;

import java.util.Arrays;

import org.junit.rules.ExternalResource;

import com.roanis.tdd.nucleus.TddNucleusTestUtils;

import atg.nucleus.Nucleus;

public class NucleusWithModules extends ExternalResource {
	private String[] mModules;
	private Nucleus mNucleus = null;
	private Class<?> mTestClass;

	public NucleusWithModules(String[] modules, Class<?> testClass){
		mModules = modules;
		mTestClass = testClass;
	}
	
	@Override
	protected void before() throws Throwable {	
		mNucleus = TddNucleusTestUtils.startNucleus(Arrays.asList(mModules), mTestClass);
	}
	
	@Override
	protected void after() {
		TddNucleusTestUtils.shutdownNucleus(mNucleus);
	}

}
