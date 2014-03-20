package com.roanis.tdd.junit4.rules;

/**
 * This class in an {@link org.junit.rules.ExternalResource}, which loads the specified Catalog, before a test
 *  and sets it as the current catalog. After the test runs, the current catalog is set back to null.
 * 
 * @author rory curtis
 *
 */
public class CatalogData extends ExternalNucleusData {
	String mCatalogId;
	
	public CatalogData(String catalogId){
		mCatalogId = catalogId;
	}
	
	@Override
	protected void before() throws Throwable {		
		super.before();
		getTestConfiguration().getCatalogTestHelper().setAsCurrent(mCatalogId);
	}
	
	@Override
	protected void after() {	
		getTestConfiguration().getCatalogTestHelper().reset();
		super.after();
	}
}
