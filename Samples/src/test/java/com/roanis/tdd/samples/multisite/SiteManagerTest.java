package com.roanis.tdd.samples.multisite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import atg.multisite.SiteManager;
import atg.repository.RepositoryException;

import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.annotation.NucleusWithSite;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusWithSite()
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class SiteManagerTest  {
	
	@NucleusComponent("/atg/multisite/SiteManager")
	private SiteManager mSiteManager;
		
	// Some tests that run queries against the Site repository
	@Test
	public void singleSiteExists() throws RepositoryException{
		assertEquals(1, mSiteManager.getSiteCount());		
	}
	
	@Test
	public void zeroDisabledSites() throws RepositoryException{
		assertTrue(ArrayUtils.isEmpty(mSiteManager.getDisabledSites()));
	}
		
}
