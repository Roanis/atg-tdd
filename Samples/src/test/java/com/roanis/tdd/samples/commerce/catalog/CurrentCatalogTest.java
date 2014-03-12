package com.roanis.tdd.samples.commerce.catalog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.commerce.dp.CatalogContext;

import com.roanis.tdd.core.commerce.catalog.BaseCatalogTest;
import com.roanis.tdd.core.commerce.catalog.CatalogTestConstants;

@RunWith(JUnit4.class)
public class CurrentCatalogTest extends BaseCatalogTest {

	@Test
	public void currentCatalogExists() {
		assertNotNull(CatalogContext.getCurrentCatalog());
	}
	
	@Test
	public void baseCatalogIsCurrent(){
		assertEquals(CatalogTestConstants.BASE_CATALOG_ID, CatalogContext.getCurrentCatalog().getRepositoryId());
	}

}
