package com.roanis.tdd.samples.inventory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.commerce.inventory.InventoryException;
import atg.commerce.inventory.InventoryManager;

import com.roanis.tdd.base.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.base.commerce.inventory.BaseInventoryTest;

@RunWith(JUnit4.class)
public class InventoryManagerTest extends BaseInventoryTest {
	
	@Test
	public void stockAvailable() throws InventoryException {
		assertEquals(InventoryManager.AVAILABILITY_STATUS_IN_STOCK, getInventoryManager().queryAvailabilityStatus(CatalogTestConstants.BOOTS_SIZE_5_SKU_ID));				
	}

}
