package com.roanis.tdd.samples.multisite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import atg.multisite.SiteContextManager;

import com.roanis.tdd.annotation.WithSite;
import com.roanis.tdd.base.multisite.SiteTestConstants;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@WithSite(SiteTestConstants.BASE_SITE_ID)
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class CurrentSiteTest {		
	
	@Test
	public void currentSiteExists() {		
		assertNotNull(SiteContextManager.getCurrentSite());
	}
	
	@Test
	public void baseSiteIsCurrent(){		
		assertEquals(SiteTestConstants.BASE_SITE_ID, SiteContextManager.getCurrentSiteId());
	}
	
	@Test
	public void isEnabled(){
		assertTrue((Boolean) SiteContextManager.getCurrentSite().getPropertyValue(SiteTestConstants.ENABLED_PROPERTY_NAME));
	}

}
