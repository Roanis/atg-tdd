package com.roanis.tdd.samples.commerce.inventory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import atg.commerce.inventory.InventoryException;
import atg.commerce.inventory.InventoryManager;

import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.annotation.NucleusRequired;
import com.roanis.tdd.annotation.NucleusWithCommerce;
import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusRequired(modules={"TDD.MyModule"})
@NucleusWithCommerce()
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class InventoryManagerTest {
	
	@NucleusComponent("/atg/commerce/inventory/InventoryManager")
	private InventoryManager mInventoryManager;
	
	@Test
	public void stockAvailable() throws InventoryException {
		assertEquals(InventoryManager.AVAILABILITY_STATUS_IN_STOCK, mInventoryManager.queryAvailabilityStatus(CatalogTestConstants.BOOTS_SIZE_5_SKU_ID));				
	}

}
