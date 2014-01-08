package com.roanis.tdd.samples.profile;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.roanis.tdd.base.BaseProfileTest;
import com.roanis.tdd.base.RoanisTestCase;

@RunWith(JUnit4.class)
public class ProfileToolsTest extends BaseProfileTest {
	
	@BeforeClass
    public static void startup () throws Exception {
        List<String> modules = new ArrayList<String>();
        modules.add("Roanis.TDD.Samples");
        RoanisTestCase.startNucleus(modules);
    }

    @AfterClass
    public static void shutdown () {
    	RoanisTestCase.shutdownNucleus();
    }
    
    String baseUserEmailAddress;
    
    @Override
    public void setUp() throws Exception {    	
    	super.setUp();
    	
    	baseUserEmailAddress = (String) getBaseProfile().getPropertyValue(mProfileTools.getPropertyManager().getEmailAddressPropertyName());
    }
	
    @Override
    public void tearDown() throws Exception {
    	baseUserEmailAddress = null;
    	super.tearDown();
    }
	
	@Test
	public void userExists(){
		assertNotNull(mProfileTools.getItemFromEmail(baseUserEmailAddress));
	}
	
	@Test
	public void noSuchUser(){
		assertNull(mProfileTools.getItemFromEmail("noSuchEmail@nosuchdomain.com"));
	}
		
}
