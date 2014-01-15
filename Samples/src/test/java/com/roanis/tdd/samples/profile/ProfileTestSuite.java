package com.roanis.tdd.samples.profile;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CurrentProfileTest.class, ProfileToolsTest.class})
public class ProfileTestSuite {
}
