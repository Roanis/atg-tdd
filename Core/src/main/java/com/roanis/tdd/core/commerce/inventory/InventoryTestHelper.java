package com.roanis.tdd.core.commerce.inventory;

import com.roanis.tdd.core.TestHelper;

import atg.commerce.inventory.InventoryManager;

public class InventoryTestHelper implements TestHelper {
	private InventoryManager mInventoryManager;

	public InventoryManager getInventoryManager() {
		return mInventoryManager;
	}

	public void setInventoryManager(InventoryManager pInventoryManager) {
		mInventoryManager = pInventoryManager;
	}
	
	@Override
	public String getName() {
		return getClass().getCanonicalName();
	}

}
