package com.roanis.tdd.base.commerce.promotion;

import com.roanis.tdd.base.TestHelper;

import atg.commerce.promotion.PromotionTools;

public class PromotionTestHelper implements TestHelper {
	
	private PromotionTools mPromotionTools;

	public PromotionTools getPromotionTools() {
		return mPromotionTools;
	}

	public void setPromotionTools(PromotionTools pPromotionTools) {
		mPromotionTools = pPromotionTools;
	}
	
	@Override
	public String getName() {
		return getClass().getCanonicalName();
	}

}
