package com.roanis.tdd.base.commerce.gifts;

import com.roanis.tdd.base.TestHelper;

import atg.commerce.gifts.GiftlistManager;

public class GiftListTestHelper implements TestHelper {
	
	private GiftlistManager mGiftlistManager;

	public GiftlistManager getGiftlistManager() {
		return mGiftlistManager;
	}

	public void setGiftlistManager(GiftlistManager pGiftlistManager) {
		mGiftlistManager = pGiftlistManager;
	}
	
	@Override
	public String getName() {
		return getClass().getCanonicalName();
	}

}
