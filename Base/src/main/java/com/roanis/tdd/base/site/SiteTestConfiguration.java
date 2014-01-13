package com.roanis.tdd.base.site;

import atg.multisite.SiteContextManager;
import atg.multisite.SiteManager;
import atg.multisite.SiteTools;

public class SiteTestConfiguration {
	private SiteManager mSiteManager;

	public SiteManager getSiteManager() {
		return mSiteManager;
	}

	public void setSiteManager(SiteManager pSiteManager) {
		mSiteManager = pSiteManager;
	}
	
	private SiteTools mSiteTools;

	public SiteTools getSiteTools() {
		return mSiteTools;
	}

	public void setSiteTools(SiteTools pSiteTools) {
		mSiteTools = pSiteTools;
	}
	
	private SiteContextManager mSiteContextManager;

	public SiteContextManager getSiteContextManager() {
		return mSiteContextManager;
	}

	public void setSiteContextManager(SiteContextManager pSiteContextManager) {
		mSiteContextManager = pSiteContextManager;
	}

}
