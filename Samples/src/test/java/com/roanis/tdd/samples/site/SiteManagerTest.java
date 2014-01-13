package com.roanis.tdd.samples.site;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import atg.multisite.SiteManager;
import atg.repository.RepositoryException;

import com.roanis.tdd.base.BaseSiteTest;

@RunWith(JUnit4.class)
public class SiteManagerTest extends BaseSiteTest {
	
	private SiteManager mSiteManager;
		
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mSiteManager = getSiteTestConfiguration().getSiteManager();
	}
	
	@Override
	public void tearDown() throws Exception {
		mSiteManager = null;
		super.tearDown();
	}
		
	// Some tests that run queries against the Site repository
	@Test
	public void singleSiteExists() throws RepositoryException{
		assertEquals(1, getSiteManager().getSiteCount());		
	}
	
	@Test
	public void zeroDisabledSites() throws RepositoryException{
		assertTrue(ArrayUtils.isEmpty(getSiteManager().getDisabledSites()));
	}
	
	public SiteManager getSiteManager() {
		return mSiteManager;
	}

	public void setSiteManager(SiteManager pSiteManager) {
		mSiteManager = pSiteManager;
	}

}
