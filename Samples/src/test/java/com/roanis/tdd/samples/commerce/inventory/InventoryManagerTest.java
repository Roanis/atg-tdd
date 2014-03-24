package com.roanis.tdd.samples.commerce.inventory;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import atg.commerce.inventory.InventoryException;
import atg.commerce.inventory.InventoryManager;

import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;
import com.roanis.tdd.util.TestUtils;

@RunWith(NucleusAwareJunit4ClassRunner.class)
public class InventoryManagerTest {
	private InventoryManager mInventoryManager;
	
	@Before
	public void setUp() throws Exception {	
		mInventoryManager = TestUtils.getTestConfiguration().getInventoryTestHelper().getInventoryManager();
	}
	
	@After
	public void tearDown(){
		mInventoryManager = null;
	}
	
	@Test
	public void stockAvailable() throws InventoryException {
		assertEquals(InventoryManager.AVAILABILITY_STATUS_IN_STOCK, mInventoryManager.queryAvailabilityStatus(CatalogTestConstants.BOOTS_SIZE_5_SKU_ID));				
	}

}
