package com.roanis.tdd.samples.all;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.roanis.tdd.base.nucleus.RoanisNucleusTestUtils;
import com.roanis.tdd.samples.commerce.catalog.CatalogTestSuite;
import com.roanis.tdd.samples.commerce.inventory.InventoryTestSuite;
import com.roanis.tdd.samples.commerce.pricing.priceLists.PriceListTestSuite;
import com.roanis.tdd.samples.multisite.SiteTestSuite;
import com.roanis.tdd.samples.userprofiling.ProfileTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ProfileTestSuite.class, SiteTestSuite.class, CatalogTestSuite.class, PriceListTestSuite.class, InventoryTestSuite.class})
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
