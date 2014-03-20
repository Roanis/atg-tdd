package com.roanis.tdd.samples.all;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import com.roanis.tdd.annotation.RunNucleus;
import com.roanis.tdd.junit4.runner.NucleusAwareSuite;
import com.roanis.tdd.samples.commerce.catalog.CatalogTestSuite;
import com.roanis.tdd.samples.commerce.inventory.InventoryTestSuite;
import com.roanis.tdd.samples.commerce.order.OrderTestSuite;
import com.roanis.tdd.samples.commerce.pricing.priceLists.PriceListTestSuite;
import com.roanis.tdd.samples.multisite.SiteTestSuite;
import com.roanis.tdd.samples.userprofiling.ProfileTestSuite;

@RunNucleus(modules={"Roanis.TDD.Samples"})
@RunWith(NucleusAwareSuite.class)
@SuiteClasses({ProfileTestSuite.class, SiteTestSuite.class, CatalogTestSuite.class, PriceListTestSuite.class, InventoryTestSuite.class, OrderTestSuite.class})
public class AllTests {

}
