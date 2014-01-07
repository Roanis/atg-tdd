package com.roanis.tdd.samples.profile;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;  

import atg.userprofiling.ProfileTools;

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
	
	protected ProfileTools mProfileTools = null;
	protected String mEmailPropertyName = null;
	
	@Override
	public void setUpComponents() throws Exception {
		super.setUpComponents();
		
		mProfileTools = getProfileTestTools().getProfileTools();
		mEmailPropertyName = mProfileTools.getPropertyManager().getEmailAddressPropertyName();
	}
	
	@Test
	public void emailAddressExists(){
		assertNotNull(mProfileTools.getItemFromEmail((String) getBaseProfile().getPropertyValue(mEmailPropertyName)));
	}
	
	@Test
	public void noEmailAddressFound(){
		assertNull(mProfileTools.getItemFromEmail("noSuchEmail@nosuchdomain.com"));
	}
	
	@Override
	public void tearDownComponents() {
		mProfileTools = null;
		mEmailPropertyName = null;
		super.tearDownComponents();
	}

}
