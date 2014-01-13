package com.roanis.tdd.samples.all;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.roanis.tdd.base.RoanisTestCase;
import com.roanis.tdd.samples.profile.ProfileTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ProfileTestSuite.class})
public class AllTests {
	
	@BeforeClass
    public static void startup () throws Exception {
        List<String> modules = new ArrayList<String>();
        modules.add("Roanis.TDD.Samples");
        RoanisTestCase.startNucleus(modules);
    }

    @AfterClass
    public static void shutdown () {
    	RoanisTestCase.shutdownNucleus();
    }

}
