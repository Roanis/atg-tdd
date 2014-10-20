package com.roanis.tdd.samples.userprofiling;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import atg.servlet.ServletUtil;
import atg.userprofiling.ProfileTools;

import com.roanis.tdd.annotation.NucleusComponent;
import com.roanis.tdd.annotation.NucleusRequired;
import com.roanis.tdd.annotation.NucleusWithProfile;
import com.roanis.tdd.junit4.runner.NucleusAwareJunit4ClassRunner;

@NucleusRequired(modules={"TDD.MyModule"})
@NucleusWithProfile()
@RunWith(NucleusAwareJunit4ClassRunner.class)
public class ProfileToolsTest {
    
	@NucleusComponent("/atg/userprofiling/ProfileTools")
	private ProfileTools mProfileTools;
	
    String userEmailAddress;
    
    @Before
    public void setUp() throws Exception {    	
    	userEmailAddress = (String) ServletUtil.getCurrentUserProfile().getPropertyValue(mProfileTools.getPropertyManager().getEmailAddressPropertyName());
    }
	
    @After
    public void tearDown() throws Exception {
    	userEmailAddress = null;    	
    }
	
	@Test
	public void userExists(){
		assertNotNull(mProfileTools.getItemFromEmail(userEmailAddress));
	}
	
	@Test
	public void noSuchUser(){
		assertNull(mProfileTools.getItemFromEmail("noSuchEmail@nosuchdomain.com"));
	}			
}
