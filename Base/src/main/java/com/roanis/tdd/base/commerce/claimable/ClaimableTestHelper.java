package com.roanis.tdd.base.commerce.claimable;

import com.roanis.tdd.base.TestHelper;

import atg.commerce.claimable.ClaimableManager;

public class ClaimableTestHelper implements TestHelper{
	
	private ClaimableManager mClaimableManager;

	public ClaimableManager getClaimableManager() {
		return mClaimableManager;
	}

	public void setClaimableManager(ClaimableManager pClaimableManager) {
		mClaimableManager = pClaimableManager;
	}
	
	@Override
	public String getName() {
		return getClass().getCanonicalName();
	}

}
