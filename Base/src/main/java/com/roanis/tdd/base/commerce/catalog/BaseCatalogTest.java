package com.roanis.tdd.base.commerce.catalog;

import atg.commerce.catalog.CatalogTools;

import com.roanis.tdd.base.BaseCommerceTest;

public class BaseCatalogTest extends BaseCommerceTest {
	
	@Override
	public void setUp() throws Exception {		
		super.setUp();
		setupCatalogs();
	}
	
	public CatalogTools getCatalogTools(){
		return getCatalogTestHelper().getCatalogTools();
	}

}
