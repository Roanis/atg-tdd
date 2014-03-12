package com.roanis.tdd.junit4.rules;


public class WithTestSite extends ExternalData {
	String mSiteId;
	
	public WithTestSite(String siteId){
		mSiteId = siteId;
	}
	
	@Override
	protected void before() throws Throwable {		
		super.before();
		getTestConfiguration().getSiteTestHelper().setAsCurrent(mSiteId);
	}
	
	@Override
	protected void after() {
		getTestConfiguration().getSiteTestHelper().reset();
		super.after();
	}
}	
