package com.roanis.tdd.junit4.rules;

import org.junit.rules.ExternalResource;

import com.roanis.tdd.core.configuration.TestConfiguration;
import com.roanis.tdd.util.TestUtils;

public class ExternalNucleusData extends ExternalResource {
	private TestConfiguration mTestConfiguration;

	public TestConfiguration getTestConfiguration() {
		return mTestConfiguration;
	}

	public void setTestConfiguration(TestConfiguration pTestConfiguration) {
		mTestConfiguration = pTestConfiguration;
	}

	@Override
	protected void before() throws Throwable {		
		mTestConfiguration = TestUtils.getTestConfiguration();
	}
	
	@Override
	protected void after() {
		mTestConfiguration = null;
	}
}
