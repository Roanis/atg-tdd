package com.roanis.tdd.junit4.rules;

import org.junit.rules.TestRule;


/**
 * An {@link org.junit.rules.ExternalResource} {@link TestRule}, which loads the specified Site, before a test
 * and sets it as SiteContextManager.currentSite. After the test runs, SiteContextManager.currentSite is set back to null.
 * 
 * @author rory
 *
 */
public class SiteData extends ExternalNucleusData {
	String mSiteId;
	
	public SiteData(String siteId){
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
