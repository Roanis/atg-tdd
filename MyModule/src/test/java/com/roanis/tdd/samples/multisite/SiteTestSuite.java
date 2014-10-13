package com.roanis.tdd.samples.multisite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CurrentSiteTest.class, SiteManagerTest.class })
public class SiteTestSuite {

}
