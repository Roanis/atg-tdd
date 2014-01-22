package com.roanis.tdd.base.commerce.inventory;

import atg.commerce.inventory.InventoryManager;

import com.roanis.tdd.base.BaseCommerceTest;

public class BaseInventoryTest extends BaseCommerceTest {
	
	public InventoryManager getInventoryManager() {
		return getInventoryTestHelper().getInventoryManager();
	}	

}
