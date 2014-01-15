package com.roanis.tdd.base;

import atg.commerce.multisite.CommerceSitePropertiesManager;
import atg.commerce.pricing.priceLists.PriceListManager;
import atg.commerce.profile.CommercePropertyManager;
import atg.multisite.SiteContextManager;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryItem;
import atg.servlet.ServletUtil;

public class BasePriceTest extends BaseCommerceTest{
	
	private PriceListManager mPriceListManager;	
	private RepositoryItem mBaseListPrices;
	private RepositoryItem mBaseSalePrices;
	private CommerceSitePropertiesManager mCommerceSitePropertiesManager;
	private CommercePropertyManager mCommercePropertyManager;

	
			
	@Override
	public void setUp() throws Exception {	
		super.setUp();
		mPriceListManager = getCommerceTestConfiguration().getPriceListManager();
		mCommerceSitePropertiesManager = mPriceListManager.getCommerceSitePropertiesManager();
		mCommercePropertyManager = getCommerceTestConfiguration().getCommercePropertyManager();
		
		setupDefaultPriceLists();
		setupProfilePriceLists();
	}	

	protected void setupDefaultPriceLists() {
		mBaseListPrices = (RepositoryItem) SiteContextManager.getCurrentSite().getPropertyValue(getCommerceSitePropertiesManager().getDefaultListPriceListPropertyName());
		mBaseSalePrices = (RepositoryItem) SiteContextManager.getCurrentSite().getPropertyValue(getCommerceSitePropertiesManager().getDefaultSalePriceListPropertyName());
	}
	
	private void setupProfilePriceLists() {
		MutableRepositoryItem user = (MutableRepositoryItem) ServletUtil.getCurrentUserProfile();
		user.setPropertyValue(getCommercePropertyManager().getPriceListPropertyName(), mBaseListPrices);
		user.setPropertyValue(getCommercePropertyManager().getSalePriceListPropertyName(), mBaseSalePrices);
	}
	
	@Override
	public void tearDown() throws Exception {
		mPriceListManager = null;
		mCommerceSitePropertiesManager = null;
		mCommercePropertyManager = null;
		
		super.tearDown();
	}
	
	public PriceListManager getPriceListManager() {
		return mPriceListManager;
	}

	public void setPriceListManager(PriceListManager pPriceListManager) {
		mPriceListManager = pPriceListManager;
	}
	
	public RepositoryItem getBaseListPrices() {
		return mBaseListPrices;
	}

	public void setBaseListPrices(RepositoryItem pBaseListPrices) {
		mBaseListPrices = pBaseListPrices;
	}
	
	public RepositoryItem getBaseSalePrices() {
		return mBaseSalePrices;
	}

	public void setBaseSalePrices(RepositoryItem pBaseSalePrices) {
		mBaseSalePrices = pBaseSalePrices;
	}
	
	public CommerceSitePropertiesManager getCommerceSitePropertiesManager() {
		return mCommerceSitePropertiesManager;
	}

	public void setCommerceSitePropertiesManager(CommerceSitePropertiesManager pCommerceSitePropertiesManager) {
		mCommerceSitePropertiesManager = pCommerceSitePropertiesManager;
	}
	
	public CommercePropertyManager getCommercePropertyManager() {
		return mCommercePropertyManager;
	}

	public void setCommercePropertyManager(CommercePropertyManager pCommercePropertyManager) {
		mCommercePropertyManager = pCommercePropertyManager;
	}

}
