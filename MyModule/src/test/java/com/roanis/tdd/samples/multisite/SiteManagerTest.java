package com.roanis.tdd.samples.multisite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import atg.multisite.SiteManager;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;

import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.annotation.NucleusRequired;
import com.roanis.tdd.annotation.NucleusWithSite;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusRequired(modules={"TDD.MyModule"})
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
		RepositoryItem[] disabledSites = mSiteManager.getDisabledSites();
		assertTrue(disabledSites==null || disabledSites.length==0);
	}
		
}
