package com.roanis.tdd.util;

import atg.nucleus.Nucleus;

import com.roanis.tdd.core.configuration.TestConfiguration;

public class TestUtils {
	public static TestConfiguration getTestConfiguration(){
		return (TestConfiguration) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/configuration/TestConfiguration");
	}
}
