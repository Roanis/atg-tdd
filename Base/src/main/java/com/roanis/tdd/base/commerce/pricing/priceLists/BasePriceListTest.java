package com.roanis.tdd.base.commerce.pricing.priceLists;

import com.roanis.tdd.base.BaseCommerceTest;

import atg.commerce.pricing.priceLists.PriceListException;
import atg.commerce.pricing.priceLists.PriceListManager;
import atg.repository.RepositoryItem;

public class BasePriceListTest extends BaseCommerceTest{
	
	private PriceListManager mPriceListManager;	
	private RepositoryItem mListPrices;
	private RepositoryItem mSalePrices;	
	
	@Override
	public void setUp() throws Exception {	
		super.setUp();
		mPriceListManager = getPriceListTestHelper().getPriceListManager();		
		
		setupDefaultPriceLists();		
	}	

	protected void setupDefaultPriceLists() throws PriceListException {
		mListPrices = getPriceListManager().getPriceList(getPriceListTestHelper().getDefaultPriceListId());
		mSalePrices = getPriceListManager().getPriceList(getPriceListTestHelper().getDefaultSalePriceListId());
	}
		
	@Override
	public void tearDown() throws Exception {
		mPriceListManager = null;
		mListPrices = null;
		mSalePrices = null;
		
		super.tearDown();
	}
	
	public PriceListManager getPriceListManager() {
		return mPriceListManager;
	}

	public void setPriceListManager(PriceListManager pPriceListManager) {
		mPriceListManager = pPriceListManager;
	}
	
	public RepositoryItem getSalePrices() {
		return mSalePrices;
	}

	public void setSalePrices(RepositoryItem pSalePrices) {
		mSalePrices = pSalePrices;
	}

	public RepositoryItem getListPrices() {
		return mListPrices;
	}

	public void setListPrices(RepositoryItem pListPrices) {
		mListPrices = pListPrices;
	}
		
}
