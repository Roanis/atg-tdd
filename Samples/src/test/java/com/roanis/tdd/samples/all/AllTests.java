package com.roanis.tdd.samples.all;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.roanis.tdd.base.util.RoanisNucleusTestUtils;
import com.roanis.tdd.samples.catalog.CatalogTestSuite;
import com.roanis.tdd.samples.price.PriceListTestSuite;
import com.roanis.tdd.samples.profile.ProfileTestSuite;
import com.roanis.tdd.samples.site.SiteTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ProfileTestSuite.class, SiteTestSuite.class, CatalogTestSuite.class, PriceListTestSuite.class})
public class AllTests {
	
	@BeforeClass
    public static void startup () throws Exception {
        List<String> modules = new ArrayList<String>();
        modules.add("Roanis.TDD.Samples");
        RoanisNucleusTestUtils.startNucleus(modules);
    }

    @AfterClass
    public static void shutdown () {
    	RoanisNucleusTestUtils.shutdownNucleus();
    }

}
