package com.roanis.tdd.samples.commerce.catalog;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

import com.roanis.tdd.base.commerce.catalog.BaseCatalogTest;
import com.roanis.tdd.base.commerce.catalog.CatalogTestConstants;

@RunWith(JUnit4.class)
public class CatalogToolsTest extends BaseCatalogTest {	
	
	@Test
	public void rootCategoryExists() throws RepositoryException{
		assertNotNull(getCatalogTools().findCategory(CatalogTestConstants.ROOT_CATEGORY_ID));
	}
	
	@Test
	public void productExists() throws RepositoryException{
		assertNotNull(getCatalogTools().findProduct(CatalogTestConstants.MENS_BELT_PRODUCT_ID));
	}
	
	@Test
	public void productHasChildSkus() throws RepositoryException{
		RepositoryItem jacket = getCatalogTools().findProduct(CatalogTestConstants.MENS_JACKET_PRODUCT_ID);
		List<RepositoryItem> childSkus = (List<RepositoryItem>) jacket.getPropertyValue("childSKUs");
		assertEquals(3, childSkus.size());
	}

	@Test
	public void skuExists() throws RepositoryException {
		assertNotNull(getCatalogTools().findSKU(CatalogTestConstants.CASUAL_BELT_BLACK_SMALL_SKU_ID));
	}		
		
}
