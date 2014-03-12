package com.roanis.tdd.base.configuration;

import com.roanis.tdd.base.commerce.catalog.CatalogTestHelper;
import com.roanis.tdd.base.commerce.claimable.ClaimableTestHelper;
import com.roanis.tdd.base.commerce.gifts.GiftListTestHelper;
import com.roanis.tdd.base.commerce.inventory.InventoryTestHelper;
import com.roanis.tdd.base.commerce.order.OrderTestHelper;
import com.roanis.tdd.base.commerce.pricing.priceLists.PriceListTestHelper;
import com.roanis.tdd.base.commerce.promotion.PromotionTestHelper;
import com.roanis.tdd.base.multisite.SiteTestHelper;
import com.roanis.tdd.base.userprofiling.ProfileTestHelper;

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
		return (CatalogTestHelper) getTestHelpers().get(CatalogTestHelper.class.getName());
	}
	
	public ClaimableTestHelper getClaimableTestHelper(){
		return (ClaimableTestHelper) getTestHelpers().get(ClaimableTestHelper.class.getName());
	}
	
	public GiftListTestHelper getGiftListTestHelper(){
		return (GiftListTestHelper) getTestHelpers().get(GiftListTestHelper.class.getName());				
	}
	
	public InventoryTestHelper getInventoryTestHelper(){
		return (InventoryTestHelper) getTestHelpers().get(InventoryTestHelper.class.getName());
	}
	
	public OrderTestHelper getOrderTestHelper(){
		return (OrderTestHelper) getTestHelpers().get(OrderTestHelper.class.getName());				
	}
	
	public PriceListTestHelper getPriceListTestHelper(){
		return (PriceListTestHelper) getTestHelpers().get(PriceListTestHelper.class.getName());
	}
	
	public PromotionTestHelper getPromotionTestHelper(){
		return (PromotionTestHelper) getTestHelpers().get(PromotionTestHelper.class.getName());
	}
	
	public ProfileTestHelper getProfileTestHelper(){
		return (ProfileTestHelper) getTestHelpers().get(ProfileTestHelper.class.getName());
	}

	public SiteTestHelper getSiteTestHelper() {
		return (SiteTestHelper) getTestHelpers().get(SiteTestHelper.class.getSimpleName());
	}

}
