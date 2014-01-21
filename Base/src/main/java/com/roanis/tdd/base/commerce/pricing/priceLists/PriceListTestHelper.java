package com.roanis.tdd.base.commerce.pricing.priceLists;

import atg.commerce.pricing.priceLists.PriceListException;
import atg.commerce.pricing.priceLists.PriceListManager;
import atg.commerce.profile.CommercePropertyManager;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryItem;
import atg.servlet.ServletUtil;

public class PriceListTestHelper {
	
	private String mDefaultSalePriceListId;
	private String mDefaultPriceListId;
	private PriceListManager mPriceListManager;
	private CommercePropertyManager mCommercePropertyManager;
	
	public void defaultCurrentProfilePriceLists() throws PriceListException{
		defaultProfliePriceLists(ServletUtil.getCurrentUserProfile());
	}
	
	public void defaultProfliePriceLists(RepositoryItem pProfile) throws PriceListException{
		setPriceLists(pProfile, getDefaultPriceListId(), getDefaultSalePriceListId());
	}
	
	public void setPriceLists(RepositoryItem pProfile, String pPriceListId, String pSalePriceListId) throws PriceListException{
		setPriceList(pProfile, pPriceListId);
		setSalePriceList(pProfile, pSalePriceListId);
	}
	
	public void setPriceList(RepositoryItem pProfile, String pPriceListId) throws PriceListException{
		RepositoryItem priceList = getPriceListManager().getPriceList(pPriceListId);
		setPriceList(pProfile, priceList);
	}
	
	public void setSalePriceList(RepositoryItem pProfile, String pSalePriceListId) throws PriceListException{
		RepositoryItem salePriceList = getPriceListManager().getPriceList(pSalePriceListId);
		setSalePriceList(pProfile, salePriceList);
	}
	
	public void setPriceList(RepositoryItem pProfile, RepositoryItem pPriceList){
		((MutableRepositoryItem) pProfile).setPropertyValue(getCommercePropertyManager().getPriceListPropertyName(), pPriceList);
	}
	
	public void setSalePriceList(RepositoryItem pProfile, RepositoryItem pSalePriceList){
		((MutableRepositoryItem) pProfile).setPropertyValue(getCommercePropertyManager().getSalePriceListPropertyName(), pSalePriceList);
	}
			
	public String getDefaultSalePriceListId() {
		return mDefaultSalePriceListId;
	}

	public void setDefaultSalePriceListId(String pDefaultSalePriceListId) {
		mDefaultSalePriceListId = pDefaultSalePriceListId;
	}
		
	public String getDefaultPriceListId() {
		return mDefaultPriceListId;
	}

	public void setDefaultPriceListId(String pDefaultPriceListId) {
		mDefaultPriceListId = pDefaultPriceListId;
	}
		
	public PriceListManager getPriceListManager() {
		return mPriceListManager;
	}

	public void setPriceListManager(PriceListManager pPriceListManager) {
		mPriceListManager = pPriceListManager;
	}					

	public CommercePropertyManager getCommercePropertyManager() {
		return mCommercePropertyManager;
	}

	public void setCommercePropertyManager(CommercePropertyManager pCommercePropertyManager) {
		mCommercePropertyManager = pCommercePropertyManager;
	}
	

}
