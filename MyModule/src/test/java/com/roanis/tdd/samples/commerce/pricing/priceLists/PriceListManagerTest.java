package com.roanis.tdd.samples.commerce.pricing.priceLists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import atg.commerce.pricing.priceLists.PriceListException;
import atg.commerce.pricing.priceLists.PriceListManager;
import atg.repository.RepositoryItem;

import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.core.commerce.pricing.priceLists.PriceListTestHelper;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@RunWith(NucleusAwareJunit4ClassRunner.class)
public class PriceListManagerTest {
	
	@NucleusComponent("/atg/commerce/pricing/priceLists/PriceListManager")
	private PriceListManager mPriceListManager;	
	
	@NucleusComponent("/roanis/tdd/base/commerce/pricing/priceLists/PriceListTestHelper")
	private PriceListTestHelper mPriceListTestHelper;
	
	private RepositoryItem mListPrices;
	private RepositoryItem mSalePrices;	
	
	@Before
	public void setUp() throws Exception {	
		setupDefaultPriceLists();		
	}	
	
	@After
	public void tearDown() throws Exception {
		mListPrices = null;
		mSalePrices = null;				
	}

	protected void setupDefaultPriceLists() throws PriceListException {
		mListPrices = mPriceListManager.getPriceList(mPriceListTestHelper.getDefaultPriceListId());
		mSalePrices = mPriceListManager.getPriceList(mPriceListTestHelper.getDefaultSalePriceListId());
	}
		
	@Test
	public void priceListExists(){
		assertNotNull(mListPrices);
	}
	
	@Test
	public void salePriceListExists(){
		assertNotNull(mSalePrices);
	}
	
	@Test
	public void skuListPriceExists() throws PriceListException {
		RepositoryItem price = mPriceListManager.getPrice(mListPrices.getRepositoryId(), CatalogTestConstants.MENS_BELT_PRODUCT_ID, CatalogTestConstants.CASUAL_BELT_BROWN_SMALL_SKU_ID);
		assertNotNull(price);
		assertEquals(19.99D, price.getPropertyValue(mPriceListManager.getListPricePropertyName()));
	}
	
	@Test
	public void skuSalePriceExists() throws PriceListException{
		RepositoryItem price = mPriceListManager.getPrice(mSalePrices.getRepositoryId(), CatalogTestConstants.PHONE_PRODUCT_ID, CatalogTestConstants.PHONE_SKU_ID);
		assertNotNull(price);
		assertEquals(269.00D, price.getPropertyValue(mPriceListManager.getListPricePropertyName()));
	}
	

}
