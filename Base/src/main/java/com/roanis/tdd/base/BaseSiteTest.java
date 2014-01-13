package com.roanis.tdd.base;

import atg.multisite.Site;
import atg.multisite.SiteContextImpl;
import atg.multisite.SiteContextManager;
import atg.nucleus.Nucleus;

import com.roanis.tdd.base.site.SiteTestConfiguration;
import com.roanis.tdd.base.site.SiteTestConstants;

public class BaseSiteTest extends RoanisTestCase {
	private String mSiteId = SiteTestConstants.BASE_SITE_ID;
	private Site mBaseSite;
	
	private SiteTestConfiguration mSiteTestConfiguration;	
	
	@Override
	protected void setupTestConfiguration() {		
		super.setupTestConfiguration();
		mSiteTestConfiguration = (SiteTestConfiguration) Nucleus.getGlobalNucleus().resolveName("/roanis/tdd/base/site/SiteTestConfiguration");
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();				
		setupSite();
	}
	
	protected void setupSite() throws Exception {	
		SiteContextManager siteContextManager = getSiteTestConfiguration().getSiteContextManager();
		mBaseSite = siteContextManager.getSite(mSiteId);  
		
		// Set site as the current site
        SiteContextImpl siteContext = new SiteContextImpl(siteContextManager, mBaseSite);
        siteContextManager.pushSiteContext(siteContext); 
	}
	
	@Override
	public void tearDown() throws Exception {
		mSiteTestConfiguration = null;
		mBaseSite = null;
		mSiteId = null;
		super.tearDown();
	}
	
	public Site getBaseSite() {
		return mBaseSite;
	}

	public void setBaseSite(Site pBaseSite) {
		mBaseSite = pBaseSite;
	}	
	
	public String getSiteId() {
		return mSiteId;
	}

	public void setSiteId(String pSiteId) {
		mSiteId = pSiteId;
	}
	
	public SiteTestConfiguration getSiteTestConfiguration() {
		return mSiteTestConfiguration;
	}

	public void setSiteTestConfiguration(SiteTestConfiguration pSiteTestConfiguration) {
		mSiteTestConfiguration = pSiteTestConfiguration;
	}
	
}
