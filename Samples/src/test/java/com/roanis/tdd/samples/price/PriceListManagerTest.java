package com.roanis.tdd.samples.price;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.commerce.pricing.priceLists.PriceListManager;
import atg.repository.RepositoryItem;

import com.roanis.tdd.base.BaseCommerceTest;

@RunWith(JUnit4.class)
public class PriceListManagerTest extends BasePriceTest {
	
	
	@Test
	public void skuPriceExists() {
		RepositoryItem price = getPriceListManager().getPrice(pPriceListId, pProductId, pSkuId);
	}
	
	

}
