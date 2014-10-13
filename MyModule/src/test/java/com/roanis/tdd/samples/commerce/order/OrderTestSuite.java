package com.roanis.tdd.samples.commerce.order;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ OrderManagerTest.class, CurrentOrderTest.class  })
public class OrderTestSuite {

}
