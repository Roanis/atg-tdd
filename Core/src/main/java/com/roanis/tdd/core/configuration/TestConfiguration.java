package com.roanis.tdd.core.configuration;

import com.roanis.tdd.core.commerce.catalog.CatalogTestHelper;
import com.roanis.tdd.core.commerce.claimable.ClaimableTestHelper;
import com.roanis.tdd.core.commerce.gifts.GiftListTestHelper;
import com.roanis.tdd.core.commerce.inventory.InventoryTestHelper;
import com.roanis.tdd.core.commerce.order.OrderTestHelper;
import com.roanis.tdd.core.commerce.pricing.priceLists.PriceListTestHelper;
import com.roanis.tdd.core.commerce.promotion.PromotionTestHelper;
import com.roanis.tdd.core.multisite.SiteTestHelper;
import com.roanis.tdd.core.userprofiling.ProfileTestHelper;

import atg.nucleus.ServiceMap;

public class TestConfiguration {	
	
	private ServiceMap mTestHelpers;

	public ServiceMap getTestHelpers() {
		return mTestHelpers;
	}

	public void setTestHelpers(ServiceMap pTestHelpers) {
		mTestHelpers = pTestHelpers;
	}	
	
	public CatalogTestHelper getCatalogTestHelper(){
		return (CatalogTestHelper) getTestHelpers().get(CatalogTestHelper.class.getSimpleName());
	}
	
	public ClaimableTestHelper getClaimableTestHelper(){
		return (ClaimableTestHelper) getTestHelpers().get(ClaimableTestHelper.class.getSimpleName());
	}
	
	public GiftListTestHelper getGiftListTestHelper(){
		return (GiftListTestHelper) getTestHelpers().get(GiftListTestHelper.class.getSimpleName());				
	}
	
	public InventoryTestHelper getInventoryTestHelper(){
		return (InventoryTestHelper) getTestHelpers().get(InventoryTestHelper.class.getSimpleName());
	}
	
	public OrderTestHelper getOrderTestHelper(){
		return (OrderTestHelper) getTestHelpers().get(OrderTestHelper.class.getSimpleName());				
	}
	
	public PriceListTestHelper getPriceListTestHelper(){
		return (PriceListTestHelper) getTestHelpers().get(PriceListTestHelper.class.getSimpleName());
	}
	
	public PromotionTestHelper getPromotionTestHelper(){
		return (PromotionTestHelper) getTestHelpers().get(PromotionTestHelper.class.getSimpleName());
	}
	
	public ProfileTestHelper getProfileTestHelper(){
		return (ProfileTestHelper) getTestHelpers().get(ProfileTestHelper.class.getSimpleName());
	}

	public SiteTestHelper getSiteTestHelper() {
		return (SiteTestHelper) getTestHelpers().get(SiteTestHelper.class.getSimpleName());
	}

}
