package com.roanis.tdd.junit4.rules;

import org.junit.rules.ExternalResource;

import com.roanis.tdd.core.configuration.TestConfiguration;
import com.roanis.tdd.util.TestUtils;

/**
 * An {@link org.junit.rules.ExternalResource} {@link TestRule} which resolves
 * the {@link TestConfiguration} Nucleus component. Once this component is 
 * resolved, subclasses have access to all the {@link com.roanis.tdd.core.TestHelper} objects.
 * 
 * @author rory
 *
 */
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
