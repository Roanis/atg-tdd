package com.roanis.tdd.samples.multisite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import atg.multisite.SiteManager;
import atg.repository.RepositoryException;

import com.roanis.tdd.annotation.WithSite;
import com.roanis.tdd.base.multisite.SiteTestConstants;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;
import com.roanis.tdd.util.TestUtils;

@WithSite(SiteTestConstants.BASE_SITE_ID)
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class SiteManagerTest  {
	
	private SiteManager mSiteManager;
		
	@Before
	public void setUp() throws Exception {		
		mSiteManager = TestUtils.getTestConfiguration().getSiteTestHelper().getSiteManager();
	}
	
	@After
	public void tearDown() throws Exception {
		mSiteManager = null;		
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
