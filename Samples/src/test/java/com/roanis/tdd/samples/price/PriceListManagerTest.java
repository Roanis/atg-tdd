package com.roanis.tdd.samples.price;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.commerce.pricing.priceLists.PriceListException;
import atg.repository.RepositoryItem;

import com.roanis.tdd.base.BasePriceListTest;
import com.roanis.tdd.base.commerce.catalog.CatalogTestConstants;

@RunWith(JUnit4.class)
public class PriceListManagerTest extends BasePriceListTest {
		
	@Test
	public void priceListExists(){
		assertNotNull(getListPrices());
	}
	
	@Test
	public void salePriceListExists(){
		assertNotNull(getSalePrices());
	}
	
	@Test
	public void skuListPriceExists() throws PriceListException {
		RepositoryItem price = getPriceListManager().getPrice(getListPrices().getRepositoryId(), CatalogTestConstants.MENS_BELT_PRODUCT_ID, CatalogTestConstants.CASUAL_BELT_BROWN_SMALL_SKU_ID);
		assertNotNull(price);
		assertEquals(19.99D, price.getPropertyValue(getPriceListManager().getListPricePropertyName()));
	}
	
	@Test
	public void skuSalePriceExists() throws PriceListException{
		RepositoryItem price = getPriceListManager().getPrice(getSalePrices().getRepositoryId(), CatalogTestConstants.PHONE_PRODUCT_ID, CatalogTestConstants.PHONE_SKU_ID);
		assertNotNull(price);
		assertEquals(269.00D, price.getPropertyValue(getPriceListManager().getListPricePropertyName()));
	}
	

}
