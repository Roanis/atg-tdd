package com.roanis.tdd.util;

import atg.nucleus.Nucleus;

import com.roanis.tdd.core.configuration.TestConfiguration;

/**
 * Utility methods for performing unit tests.
 * 
 * @author rory
 *
 */
public class TestUtils {
	public static TestConfiguration getTestConfiguration(){
		return (TestConfiguration) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/configuration/TestConfiguration");
	}
}
