package com.roanis.tdd.samples.commerce.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import atg.commerce.catalog.CatalogTools;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.annotation.NucleusRequired;
import com.roanis.tdd.annotation.NucleusWithCatalog;
import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusRequired(modules={"TDD.MyModule"})
@NucleusWithCatalog()
@RunWith(NucleusAwareJunit4ClassRunner.class)

public class CatalogToolsTest {	
	
	@NucleusComponent("/atg/commerce/catalog/CatalogTools")
	private CatalogTools mCatalogTools;
	
	@Test
	public void rootCategoryExists() throws RepositoryException{
		assertNotNull(mCatalogTools.findCategory(CatalogTestConstants.ROOT_CATEGORY_ID));
	}
	
	@Test
	public void productExists() throws RepositoryException{
		assertNotNull(mCatalogTools.findProduct(CatalogTestConstants.MENS_BELT_PRODUCT_ID));
	}
	
	@Test
	public void productHasChildSkus() throws RepositoryException{
		RepositoryItem jacket = mCatalogTools.findProduct(CatalogTestConstants.MENS_JACKET_PRODUCT_ID);
		
		@SuppressWarnings("unchecked")
		List<RepositoryItem> childSkus = (List<RepositoryItem>) jacket.getPropertyValue("childSKUs");
		assertEquals(3, childSkus.size());
	}

	@Test
	public void skuExists() throws RepositoryException {
		assertNotNull(mCatalogTools.findSKU(CatalogTestConstants.CASUAL_BELT_BLACK_SMALL_SKU_ID));
	}			
		
}
