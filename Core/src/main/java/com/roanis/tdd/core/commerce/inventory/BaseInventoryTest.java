package com.roanis.tdd.core.commerce.inventory;

import atg.commerce.inventory.InventoryManager;

import com.roanis.tdd.core.BaseCommerceTest;

public class BaseInventoryTest extends BaseCommerceTest {
	
	public InventoryManager getInventoryManager() {
		return getInventoryTestHelper().getInventoryManager();
	}	

}
