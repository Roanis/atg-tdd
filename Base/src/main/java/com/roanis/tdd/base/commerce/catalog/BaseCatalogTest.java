package com.roanis.tdd.base.commerce.catalog;

import atg.commerce.catalog.custom.CustomCatalogTools;

import com.roanis.tdd.base.BaseCommerceTest;

public class BaseCatalogTest extends BaseCommerceTest {
	
	private CustomCatalogTools mCatalogTools;
	
	@Override
	protected void setupTestConfiguration() {		
		super.setupTestConfiguration();
		mCatalogTools = (CustomCatalogTools) getCommerceTestConfiguration().getCatalogTools();
	}
	
	@Override
	public void tearDown() throws Exception {
		mCatalogTools = null;
	}	
	
	public CustomCatalogTools getCatalogTools() {
		return mCatalogTools;
	}

	public void setCatalogTools(CustomCatalogTools pCatalogTools) {
		mCatalogTools = pCatalogTools;
	}

}
