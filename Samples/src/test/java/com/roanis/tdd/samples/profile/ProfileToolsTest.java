package com.roanis.tdd.samples.profile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.roanis.tdd.base.BaseProfileTest;

@RunWith(JUnit4.class)
public class ProfileToolsTest extends BaseProfileTest {
    
    String baseUserEmailAddress;
    
    @Override
    public void setUp() throws Exception {    	
    	super.setUp();
    	
    	baseUserEmailAddress = (String) getBaseProfile().getPropertyValue(getProfileTestConfiguration().getPropertyManager().getEmailAddressPropertyName());
    }
	
    @Override
    public void tearDown() throws Exception {
    	baseUserEmailAddress = null;
    	super.tearDown();
    }
	
	@Test
	public void userExists(){
		assertNotNull(getProfileTestConfiguration().getProfileTools().getItemFromEmail(baseUserEmailAddress));
	}
	
	@Test
	public void noSuchUser(){
		assertNull(getProfileTestConfiguration().getProfileTools().getItemFromEmail("noSuchEmail@nosuchdomain.com"));
	}
		
}
