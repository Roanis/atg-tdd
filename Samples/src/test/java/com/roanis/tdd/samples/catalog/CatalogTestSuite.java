package com.roanis.tdd.samples.catalog;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CurrentCatalogTest.class, CatalogToolsTest.class })
public class CatalogTestSuite {

}
