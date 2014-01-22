package com.roanis.tdd.base.multisite;

import atg.multisite.Site;
import atg.multisite.SiteContextException;
import atg.multisite.SiteContextImpl;
import atg.multisite.SiteContextManager;
import atg.multisite.SiteManager;
import atg.multisite.SiteTools;

public class SiteTestHelper {
	private SiteManager mSiteManager;
	private SiteTools mSiteTools;
	private SiteContextManager mSiteContextManager;
	private String mDefaultSiteId;
		
	public void defaultCurrentSite() throws SiteContextException{
		setAsCurrent(getDefaultSiteId());
	}
	
	public void setAsCurrent(String pId) throws SiteContextException{
		SiteContextManager siteContextManager = getSiteContextManager();
		Site site = getSiteContextManager().getSite(pId);  
		
		// Set site as the current site
        SiteContextImpl siteContext = new SiteContextImpl(siteContextManager, site);
        siteContextManager.pushSiteContext(siteContext); 
	}
	
	public SiteManager getSiteManager() {
		return mSiteManager;
	}

	public void setSiteManager(SiteManager pSiteManager) {
		mSiteManager = pSiteManager;
	}

	public SiteTools getSiteTools() {
		return mSiteTools;
	}

	public void setSiteTools(SiteTools pSiteTools) {
		mSiteTools = pSiteTools;
	}

	public SiteContextManager getSiteContextManager() {
		return mSiteContextManager;
	}

	public void setSiteContextManager(SiteContextManager pSiteContextManager) {
		mSiteContextManager = pSiteContextManager;
	}
	
	public String getDefaultSiteId() {
		return mDefaultSiteId;
	}

	public void setDefaultSiteId(String pDefaultSiteId) {
		mDefaultSiteId = pDefaultSiteId;
	}

}
